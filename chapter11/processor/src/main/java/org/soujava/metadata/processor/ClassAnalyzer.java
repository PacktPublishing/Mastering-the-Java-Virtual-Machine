package org.soujava.metadata.processor;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.MapperException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClassAnalyzer implements Supplier<String> {

    private static Logger LOGGER = Logger.getLogger(ClassAnalyzer.class.getName());
    private static final String NEW_INSTANCE = "entitymetadata.mustache";

    private final Element entity;

    private final ProcessingEnvironment processingEnv;

    private final Mustache template;

    ClassAnalyzer(Element entity, ProcessingEnvironment processingEnv) {
        this.entity = entity;
        this.processingEnv = processingEnv;
        MustacheFactory factory = new DefaultMustacheFactory();
        this.template = factory.compile(NEW_INSTANCE);
    }

    @Override
    public String get() {
        if (ProcessorUtil.isTypeElement(entity)) {
            TypeElement typeElement = (TypeElement) entity;
            LOGGER.info("Processing the class: " + typeElement);
            boolean hasValidConstructor = processingEnv.getElementUtils().getAllMembers(typeElement)
                    .stream()
                    .filter(EntityProcessor.IS_CONSTRUCTOR.and(EntityProcessor.HAS_ACCESS))
                    .anyMatch(EntityProcessor.IS_CONSTRUCTOR.and(EntityProcessor.HAS_ACCESS));
            if (hasValidConstructor) {
                try {
                    return analyze(typeElement);
                } catch (IOException exception) {
                    error(exception);
                }
            } else {
                throw new MapperException("The class " + ProcessorUtil.getSimpleNameAsString(entity) + " must have at least an either public or default constructor");
            }
        }

        return "";
    }

    private String analyze(TypeElement typeElement) throws IOException {

        final List<String> fields = processingEnv.getElementUtils()
                .getAllMembers(typeElement).stream()
                .filter(EntityProcessor.IS_FIELD.and(EntityProcessor.HAS_ANNOTATION))
                .map(f -> new FieldAnalyzer(f, processingEnv, typeElement))
                .map(FieldAnalyzer::get)
                .collect(Collectors.toList());

        EntityModel metadata = getMetadata(typeElement, fields);
        createClass(entity, metadata);
        LOGGER.info("Found the fields: " + fields);
        return metadata.getQualified();
    }

    private void createClass(Element entity, EntityModel metadata) throws IOException {
        Filer filer = processingEnv.getFiler();
        JavaFileObject fileObject = filer.createSourceFile(metadata.getQualified(), entity);
        try (Writer writer = fileObject.openWriter()) {
            template.execute(writer, metadata);
        }
    }

    private EntityModel getMetadata(TypeElement element, List<String> fields) {
        final Entity annotation = element.getAnnotation(Entity.class);
        String packageName = ProcessorUtil.getPackageName(element);
        String sourceClassName = ProcessorUtil.getSimpleNameAsString(element);
        String entityName = annotation.value().isBlank() ? sourceClassName : annotation.value();
        return new EntityModel(packageName, sourceClassName, entityName, fields);
    }

    private void error(IOException exception) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "failed to write extension file: "
                + exception.getMessage());
    }
}

package org.soujava.metadata.processor;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Id;
import org.soujava.medatadata.api.MapperException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.soujava.metadata.processor.ProcessorUtil.capitalize;
import static org.soujava.metadata.processor.ProcessorUtil.getPackageName;
import static org.soujava.metadata.processor.ProcessorUtil.getSimpleNameAsString;

public class FieldAnalyzer implements Supplier<String> {

    private static final String TEMPLATE = "fieldmetadata.mustache";
    private static final Predicate<Element> IS_METHOD = el -> el.getKind() == ElementKind.METHOD;
    public static final Function<Element, String> ELEMENT_TO_STRING = el -> el.getSimpleName().toString();
    private final Element field;
    private final Mustache template;
    private final ProcessingEnvironment processingEnv;
    private final TypeElement entity;

    FieldAnalyzer(Element field, ProcessingEnvironment processingEnv,
                  TypeElement entity) {
        this.field = field;
        this.processingEnv = processingEnv;
        this.entity = entity;
        this.template = createTemplate();
    }

    @Override
    public String get() {
        FieldModel metadata = getMetaData();
        Filer filer = processingEnv.getFiler();
        JavaFileObject fileObject = getFileObject(metadata, filer);
        try (Writer writer = fileObject.openWriter()) {
            template.execute(writer, metadata);
        } catch (IOException exception) {
            throw new MapperException("An error to compile the class: " +
                    metadata.getQualified(), exception);
        }
        return metadata.getQualified();
    }

    private JavaFileObject getFileObject(FieldModel metadata, Filer filer) {
        try {
            return filer.createSourceFile(metadata.getQualified(), entity);
        } catch (IOException exception) {
            throw new MapperException("An error to create the class: " +
                    metadata.getQualified(), exception);
        }

    }

    private FieldModel getMetaData() {
        final String fieldName = field.getSimpleName().toString();
        final Predicate<Element> validName = el -> el.getSimpleName().toString()
                .contains(capitalize(fieldName));
        final Predicate<String> hasGetterName = el -> el.equals("get" + capitalize(fieldName));
        final Predicate<String> hasSetterName = el -> el.equals("set" + capitalize(fieldName));
        final Predicate<String> hasIsName = el -> el.equals("is" + capitalize(fieldName));

        final List<Element> accessors = processingEnv.getElementUtils()
                .getAllMembers(entity).stream()
                .filter(validName.and(IS_METHOD).and(EntityProcessor.HAS_ACCESS))
                .collect(Collectors.toList());

        final TypeMirror typeMirror = field.asType();
        String className;
        final List<String> arguments;
        if (typeMirror instanceof DeclaredType) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            arguments = declaredType.getTypeArguments().stream()
                    .map(TypeMirror::toString)
                    .collect(Collectors.toList());
            className = declaredType.asElement().toString();

        } else {
            className = typeMirror.toString();
            arguments = Collections.emptyList();
        }

        Column column = field.getAnnotation(Column.class);
        Id id = field.getAnnotation(Id.class);
        final boolean isId = id != null;
        final String packageName = getPackageName(entity);
        final String entityName = getSimpleNameAsString(this.entity);
        final String name = getName(fieldName, column, id);

        final String getMethod = accessors.stream()
                .map(ELEMENT_TO_STRING)
                .filter(hasGetterName)
                .findFirst().orElseThrow(generateGetterError(fieldName, packageName, entityName,
                        "There is not valid getter method to the field: "));
        final String setMethod = accessors.stream()
                .map(ELEMENT_TO_STRING)
                .filter(hasSetterName.or(hasIsName))
                .findFirst().orElseThrow(generateGetterError(fieldName, packageName, entityName,
                        "There is not valid setter method to the field: "));

        return FieldModel.builder()
                .withPackageName(packageName)
                .withName(name)
                .withType(className)
                .withEntity(entityName)
                .withReader(getMethod)
                .withWriter(setMethod)
                .withFieldName(fieldName)
                .withId(isId)
                .withArguments(arguments)
                .build();
    }

    private String getName(String fieldName, Column column, Id id) {
        if (id == null) {
            return column.value().isBlank() ? fieldName : column.value();
        } else {
            return id.value().isBlank() ? fieldName : id.value();
        }
    }

    private Supplier<MapperException> generateGetterError(String fieldName, String packageName, String entity, String s) {
        return () -> new MapperException(s + fieldName + " in the class: " + packageName + "." + entity);
    }

    private Mustache createTemplate() {
        MustacheFactory factory = new DefaultMustacheFactory();
        return factory.compile(TEMPLATE);
    }

}

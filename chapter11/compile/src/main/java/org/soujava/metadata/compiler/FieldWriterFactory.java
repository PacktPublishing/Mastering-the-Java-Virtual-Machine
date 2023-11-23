package org.soujava.metadata.compiler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

class FieldWriterFactory {

    private static final Logger LOGGER = Logger.getLogger(FieldWriterFactory.class.getName());

    private static final String TEMPLATE_FILE = "FieldWriter.template";

    private static final String TEMPLATE = TemplateReader.INSTANCE.apply(TEMPLATE_FILE);

    private final JavaCompilerFacade compilerFacade;

    FieldWriterFactory(JavaCompilerFacade compilerFacade) {
        this.compilerFacade = compilerFacade;
    }


    public FieldWriter apply(Field field) {

        Class<?> declaringClass = field.getDeclaringClass();
        Optional<String> methodName = getMethodName(declaringClass, field);
        return methodName.map(compile(declaringClass, field.getType()))
                .orElseThrow(() -> new RuntimeException("there is an issue to compile a FieldReader"));
    }

    private Function<String, FieldWriter> compile(Class<?> declaringClass, Class<?> type) {
        return method -> {
            String packageName = declaringClass.getPackage().getName();
            String simpleName = declaringClass.getSimpleName() + "$" + method;
            String newInstance = declaringClass.getName();
            String name = declaringClass.getName() + "$" + method;
            String typeCast = type.getName();
            String javaSource = StringFormatter.INSTANCE.format(TEMPLATE, packageName, simpleName,
                    newInstance, method, typeCast);

            FieldWriterJavaSource source = new FieldWriterJavaSource(name, simpleName, javaSource);
            Optional<Class<? extends FieldWriter>> reader = compilerFacade.apply(source);
            return reader.map(this::newInstance).orElse(null);
        };
    }

    private FieldWriter newInstance(Class<? extends FieldWriter> writerClass) {
        try {
            return (FieldWriter) writerClass.getConstructors()[0].newInstance();
        } catch (Exception e) {
            throw new RuntimeException("An issue to create a new instance class", e);
        }
    }

    private Optional<String> getMethodName(Class<?> declaringClass, Field field) {
        try {
            Method writeMethod = new PropertyDescriptor(field.getName(), declaringClass).getWriteMethod();
            if (Modifier.isPublic(writeMethod.getModifiers())) {
                return Optional.of(writeMethod.getName());
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "A setter method does not exist to the field: "
                    + field.getName() + " within class " + declaringClass.getName() + " using the fallback with reflection", e);

        }
        return Optional.empty();
    }

    private static final class FieldWriterJavaSource implements JavaSource<FieldWriter> {

        private final String name;

        private final String simpleName;

        private final String javaSource;


        FieldWriterJavaSource(String name, String simpleName, String javaSource) {
            this.name = name;
            this.simpleName = simpleName;
            this.javaSource = javaSource;
        }

        @Override
        public String getSimpleName() {
            return simpleName;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getJavaSource() {
            return javaSource;
        }

        @Override
        public Class<FieldWriter> getType() {
            return FieldWriter.class;
        }
    }
}

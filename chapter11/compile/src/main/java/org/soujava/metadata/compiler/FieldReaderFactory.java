package org.soujava.metadata.compiler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


class FieldReaderFactory {

    private static final Logger LOGGER = Logger.getLogger(FieldReaderFactory.class.getName());

    private static final String TEMPLATE_FILE = "FieldReader.tempalte";

    private static final String TEMPLATE = TemplateReader.INSTANCE.apply(TEMPLATE_FILE);

    private final JavaCompilerFacade compilerFacade;

    FieldReaderFactory(JavaCompilerFacade compilerFacade) {
        this.compilerFacade = compilerFacade;
    }


    public FieldReader apply(Field field) {

        Class<?> declaringClass = field.getDeclaringClass();
        Optional<String> methodName = getMethodName(declaringClass, field);

        return methodName.map(compile(declaringClass))
                .orElseThrow(() -> new RuntimeException("there is an issue to compile a FieldReader"));

    }

    private Function<String, FieldReader> compile(Class<?> declaringClass) {
        return method -> {
            String packageName = declaringClass.getPackage().getName();

            String simpleName = declaringClass.getSimpleName() + "$" + method;
            String newInstance = declaringClass.getName();
            String name = declaringClass.getName() + "$" + method;
            String javaSource = StringFormatter.INSTANCE.format(TEMPLATE, packageName, simpleName, newInstance, method);
            FieldReaderJavaSource source = new FieldReaderJavaSource(name, simpleName, javaSource);
            Optional<Class<? extends FieldReader>> reader = compilerFacade.apply(source);
            return reader.map(this::newInstance).orElse(null);
        };
    }

    private FieldReader newInstance(Class<? extends FieldReader> readerClass) {
        try {
            return (FieldReader) readerClass.getConstructors()[0].newInstance();
        } catch (Exception e) {
            throw new RuntimeException("An issue to create a new instance class", e);
        }
    }

    private Optional<String> getMethodName(Class<?> declaringClass, Field field) {
        try {
            Method readMethod = new PropertyDescriptor(field.getName(), declaringClass).getReadMethod();
            if (Modifier.isPublic(readMethod.getModifiers())) {
                return Optional.of(readMethod.getName());
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "A getter method does not exist to the field: "
                    + field.getName() + " within class " + declaringClass.getName() + " using the fallback with reflection", e);

        }
        return Optional.empty();
    }

    private static final class FieldReaderJavaSource implements JavaSource<FieldReader> {

        private final String name;

        private final String simpleName;

        private final String javaSource;


        FieldReaderJavaSource(String name, String simpleName, String javaSource) {
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
        public Class<FieldReader> getType() {
            return FieldReader.class;
        }
    }
}

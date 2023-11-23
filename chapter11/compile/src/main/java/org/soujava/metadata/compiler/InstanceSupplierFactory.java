package org.soujava.metadata.compiler;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.logging.Logger;

public class InstanceSupplierFactory {

    private static final String TEMPLATE_FILE = "InstanceSupplier.template";

    private static final String TEMPLATE = TemplateReader.INSTANCE.apply(TEMPLATE_FILE);

    private final JavaCompilerFacade compilerFacade;


    InstanceSupplierFactory(JavaCompilerFacade compilerFacade) {
        this.compilerFacade = compilerFacade;
    }

    public InstanceSupplier apply(Constructor<?> constructor) {
        Class<?> declaringClass = constructor.getDeclaringClass();
        if (Modifier.isPublic(constructor.getModifiers())) {
            String packageName = declaringClass.getPackage().getName();
            String simpleName = declaringClass.getSimpleName() + "$InstanceSupplier";
            String newInstance = declaringClass.getName();
            String name = declaringClass.getName() + "$InstanceSupplier";
            String javaSource = StringFormatter.INSTANCE.format(TEMPLATE, packageName, simpleName, newInstance);
            InstanceJavaSource source = new InstanceJavaSource(name, simpleName, javaSource);
            Optional<Class<? extends InstanceSupplier>> supplier = compilerFacade.apply(source);
            Optional<InstanceSupplier> instanceSupplier = supplier.map(this::newInstance);
            return instanceSupplier.orElseThrow(() -> new RuntimeException("There is an issue to load the class"));

        }
        throw new RuntimeException(String.format("The constructor to the class %s is not public, using fallback with Reflectioin",
                declaringClass.getName()));
    }

    private InstanceSupplier newInstance(Class<? extends InstanceSupplier> type) {
        try {
            return (InstanceSupplier) type.getConstructors()[0].newInstance();
        } catch (Exception e) {
            throw new RuntimeException("An issue to create a new instance class", e);
        }
    }

    private static final class InstanceJavaSource implements JavaSource<InstanceSupplier> {

        private final String name;

        private final String simpleName;

        private final String javaSource;


        InstanceJavaSource(String name, String simpleName, String javaSource) {
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
        public Class<InstanceSupplier> getType() {
            return InstanceSupplier.class;
        }
    }
}

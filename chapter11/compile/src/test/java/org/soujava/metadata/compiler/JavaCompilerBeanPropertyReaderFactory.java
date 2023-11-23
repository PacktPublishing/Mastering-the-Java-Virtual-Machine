package org.soujava.metadata.compiler;


class JavaCompilerBeanPropertyReaderFactory {

    private final JavaCompilerFacade compilerFacade = new JavaCompilerFacade(
            JavaCompilerBeanPropertyReaderFactory.class.getClassLoader());

    public ReadFromGetterMethod generate(Class<?> beanClass, String propertyName) {

        String getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        String packageName = JavaCompilerBeanPropertyReaderFactory.class.getPackage().getName()
                + ".generated." + beanClass.getPackage().getName();
        String simpleClassName = beanClass.getSimpleName() + "$" + propertyName;
        String fullClassName = packageName + "." + simpleClassName;
        final String source = "package " + packageName + ";\n"
                + "public class " + simpleClassName + " implements " + ReadFromGetterMethod.class.getName() + " {\n"
                + "    public Object apply(Object bean) {\n"
                + "        return ((" + beanClass.getName() + ") bean)." + getterName + "();\n"
                + "    }\n"
                + "}";

        JavaSource<ReadFromGetterMethod> javaSource = new JavaSource() {
            @Override
            public String getSimpleName() {
                return fullClassName;
            }

            @Override
            public String getName() {
                return fullClassName;
            }

            @Override
            public String getJavaSource() {
                return source;
            }

            @Override
            public Class<ReadFromGetterMethod> getType() {
                return ReadFromGetterMethod.class;
            }
        };
        Class<? extends ReadFromGetterMethod> compiledClass = compilerFacade.apply(javaSource).get();
        try {
            return compiledClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("The generated class (" + fullClassName + ") failed to instantiate.", e);
        }
    }

}
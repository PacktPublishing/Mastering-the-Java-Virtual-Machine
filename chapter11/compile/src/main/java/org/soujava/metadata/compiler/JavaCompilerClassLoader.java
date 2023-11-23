package org.soujava.metadata.compiler;


import java.util.HashMap;
import java.util.Map;

final class JavaCompilerClassLoader extends ClassLoader {

    private final Map<String, JavaCompiledStream> fileObjectMap = new HashMap<>();

    public JavaCompilerClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String fullClassName) throws ClassNotFoundException {
        JavaCompiledStream fileObject = fileObjectMap.get(fullClassName);
        if (fileObject != null) {
            byte[] classBytes = fileObject.getClassBytes();
            return defineClass(fullClassName, classBytes, 0, classBytes.length);
        }
        return super.findClass(fullClassName);
    }

    public void addJavaFileObject(String qualifiedName, JavaCompiledStream fileObject) {
        fileObjectMap.put(qualifiedName, fileObject);
    }

}
package org.soujava.metadata.compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

final class JavaFileObject extends SimpleJavaFileObject {

    private final String javaSource;

    public JavaFileObject(String fullClassName, String javaSource) {
        super(URI.create("string:///" + fullClassName.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.javaSource = javaSource;
    }

    @Override
    public String getCharContent(boolean ignoreEncodingErrors) {
        return javaSource;
    }

}

package org.soujava.metadata.compiler;

public class CompilerAccessException extends RuntimeException{

    public CompilerAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompilerAccessException(String message) {
        super(message);
    }
}

package org.soujava.medatadata.api;

import java.util.Set;

public interface FieldMetadata {

    boolean isId();

    String getFieldName();

    String getName();

    void write(Object bean, Object value);

    Object read(Object bean);

    Class<?> getType();

    Set<Class<?>> getArguments();
}

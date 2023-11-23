package org.soujava.medatadata.api;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EntityMetadata {

    String getName();

    String getSimpleName();

    String getClassName();

    List<String> getFieldsName();

    Class<?> getClassInstance();

    List<FieldMetadata> getFields();

    <T> T newInstance();

    String getColumnField(String javaField);

    Optional<FieldMetadata> getFieldMapping(String javaField);

    Map<String, FieldMetadata> getFieldsGroupByName();

    Optional<FieldMetadata> getId();
}

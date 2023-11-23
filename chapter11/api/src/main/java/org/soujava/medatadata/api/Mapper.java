package org.soujava.medatadata.api;

import java.util.Map;

public interface Mapper {

    <T>  T toEntity(Map<String, Object> map, Class<T> type);

    <T> Map<String, Object> toMap(T entity);
}

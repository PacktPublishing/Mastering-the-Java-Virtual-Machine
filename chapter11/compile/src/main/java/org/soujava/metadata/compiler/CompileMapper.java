package org.soujava.metadata.compiler;

import org.soujava.medatadata.api.Mapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompileMapper implements Mapper {

    private Map<Class<?>, EntityMetadata> mapper = new HashMap<>();
    private final EntityMetadataFactory factory = new EntityMetadataFactory();

    @Override
    public <T> T toEntity(Map<String, Object> map, Class<T> type) {
        Objects.requireNonNull(map, "Map is required");
        Objects.requireNonNull(type, "type is required");

        final EntityMetadata entityMetadata = getEntityMetadata(type);
        final T instance = entityMetadata.newInstance();
        final Map<String, FieldMetadata> fieldMap = entityMetadata.getFields().stream()
                .collect(Collectors.toMap(FieldMetadata::getName, Function.identity()));

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final FieldMetadata metadata = fieldMap.get(entry.getKey());
            if (metadata != null) {
                metadata.write(instance, entry.getValue());
            }
        }

        return instance;
    }

    @Override
    public <T> Map<String, Object> toMap(T entity) {
        Objects.requireNonNull(entity, "entity is required");
        final EntityMetadata entityMetadata = getEntityMetadata(entity.getClass());
        Map<String, Object> map = new HashMap<>();
        map.put("entity", entityMetadata.getName());
        for (FieldMetadata field : entityMetadata.getFields()) {
            final Object value = field.getValue(entity);
            if (value != null) {
                map.put(field.getName(), value);
            }
        }
        return map;
    }

    private <T> EntityMetadata getEntityMetadata(Class<T> type) {
        final EntityMetadata entityMetadata = mapper.get(type);
        if (entityMetadata != null) {
            return entityMetadata;
        }
        synchronized (type) {
            final EntityMetadata entity = factory.apply(type);
            mapper.put(type, entity);
            return entity;
        }
    }
}

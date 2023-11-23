package expert.os;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class ReflectionMapper implements Mapper {

    public static final String ENTITY_ENTRY = "_entity";

    @Override
    public <T> Map<String, Object> toMap(T entity) {
        Objects.requireNonNull(entity, "entity is required");

        Map<String, Object> map = new HashMap<>();

        Class<?> type = entity.getClass();
        map.put(ENTITY_ENTRY, type.getName());

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);

            Optional<Column> column = Optional.ofNullable(field.getAnnotation(Column.class));
            if (column.isPresent()) {
                String key = column.map(Column::value)
                        .filter(Predicate.not(String::isBlank))
                        .orElse(field.getName());
                Object value = getValue(entity, field);
                map.put(key, value);
            }

        }

        Append[] appends = type.getAnnotationsByType(Append.class);
        for (Append append : appends) {
            map.put(append.key(), append.value());
        }

        return map;
    }

    @Override
    public <T> T toEntity(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        T entity = getEntity(map.get(ENTITY_ENTRY).toString());
        Class<?> type = entity.getClass();
        for (Field field : type.getDeclaredFields()) {
            String key = Optional.ofNullable(field.getAnnotation(Column.class))
                    .map(Column::value)
                    .filter(Predicate.not(String::isBlank))
                    .orElse(field.getName());
            Optional<Object> value = Optional.ofNullable(map.get(key));
            value.ifPresent(v -> setValue(entity, field, v));
        }

        return entity;
    }

    private <T> void setValue(T entity, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T getEntity(String entity) {
        try {
            Class<?> type = Class.forName(entity);
            for (Constructor<?> constructor : type.getConstructors()) {
                if (constructor.getParameterCount() == 0) {
                    return (T) constructor.newInstance();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("it should have a constructor");
    }


    private static <T> Object getValue(T entity, Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

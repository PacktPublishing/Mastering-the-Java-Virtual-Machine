package org.soujava.metadata.reflection;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Id;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

interface FieldWriter {

    <T> void write(Map<String, Object> map, T instance) throws IllegalAccessException;

    class IdFieldWriter implements FieldWriter {

        private final Field field;

        public IdFieldWriter(Field field) {
            this.field = field;
        }

        @Override
        public <T> void write(Map<String, Object> map, T instance) throws IllegalAccessException {
            final Id id = field.getAnnotation(Id.class);
            final String fieldName = field.getName();
            String idName = id.value().isBlank() ? fieldName : id.value();
            field.setAccessible(true);
            final Object value = map.get(idName);
            if (value != null) {
                field.set(instance, value);
            }
        }
    }

    class ColumnFieldWriter implements FieldWriter {

        private final Field field;

        public ColumnFieldWriter(Field field) {
            this.field = field;
        }

        @Override
        public <T> void write(Map<String, Object> map, T instance) throws IllegalAccessException {
            final Column column = field.getAnnotation(Column.class);
            final String fieldName = field.getName();
            String columnName = column.value().isBlank() ? fieldName : column.value();
            field.setAccessible(true);
            final Object value = map.get(columnName);
            if (value != null) {
                field.set(instance, value);
            }
        }
    }

    class NooPFieldWriter implements FieldWriter {
        @Override
        public <T> void write(Map<String, Object> map, T instance) throws IllegalAccessException {

        }
    }

    static FieldWriter of(Field field) {
        if (Objects.nonNull(field.getAnnotation(Id.class))) {
            return new IdFieldWriter(field);
        } else if (Objects.nonNull(field.getAnnotation(Column.class))) {
            return new ColumnFieldWriter(field);
        } else {
            return new NooPFieldWriter();
        }
    }

}

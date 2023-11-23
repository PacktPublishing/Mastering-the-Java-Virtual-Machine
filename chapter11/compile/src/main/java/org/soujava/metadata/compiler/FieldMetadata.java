package org.soujava.metadata.compiler;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Id;

import java.lang.reflect.Field;

class FieldMetadata {

    private final Field field;

    private final FieldReader reader;

    private final FieldWriter writer;

    FieldMetadata(Field field, FieldReader reader, FieldWriter writer) {
        this.field = field;
        this.reader = reader;
        this.writer = writer;
    }


    public String getName() {
        final Id id = field.getAnnotation(Id.class);
        final Column column = field.getAnnotation(Column.class);
        if (id != null) {
            return id.value().isEmpty() ? field.getName() : id.value();
        } else if (column != null) {
            return column.value().isEmpty() ? field.getName() : column.value();
        }
        return field.getName();
    }

    public <T> Object getValue(T entity) {
        return this.reader.read(entity);
    }

    public <T> void write(T entity, Object value) {
        this.writer.write(entity, value);
    }

    @Override
    public String toString() {
        return "FieldMetadata{" +
                "field=" + field +
                ", reader=" + reader +
                ", writer=" + writer +
                '}';
    }

}

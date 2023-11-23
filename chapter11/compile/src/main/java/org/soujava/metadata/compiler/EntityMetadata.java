package org.soujava.metadata.compiler;

import org.soujava.medatadata.api.Entity;

import java.util.List;

class EntityMetadata {

    private final Class<?> type;

    private final InstanceSupplier supplier;

    private final List<FieldMetadata> fields;

    EntityMetadata(Class<?> type, InstanceSupplier supplier, List<FieldMetadata> fields) {
        this.type = type;
        this.supplier = supplier;
        this.fields = fields;
    }


    public Class<?> getType() {
        return type;
    }

    public <T> T newInstance() {
        return (T) this.supplier.get();
    }

    public List<FieldMetadata> getFields() {
        return fields;
    }

    public String getName() {
        final Entity annotation = type.getAnnotation(Entity.class);
        final String value = annotation.value();
        return value.isEmpty() ? type.getSimpleName() : value;
    }


}

package org.soujava.metadata.compiler;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class EntityMetadataFactory implements Function<Class<?>, EntityMetadata> {

    private final JavaCompilerFacade compilerFacade;
    private final FieldReaderFactory readerFactory;
    private final FieldWriterFactory writerFactory;
    private final InstanceSupplierFactory instanceFactory;

    public EntityMetadataFactory() {
        this.compilerFacade = new JavaCompilerFacade(EntityMetadataFactory.class.getClassLoader());
        this.readerFactory = new FieldReaderFactory(compilerFacade);
        this.writerFactory = new FieldWriterFactory(compilerFacade);
        this.instanceFactory = new InstanceSupplierFactory(compilerFacade);
    }

    @Override
    public EntityMetadata apply(Class<?> type) {

        List<FieldMetadata> fields = new ArrayList<>();
        for (Field field : type.getDeclaredFields()) {
            final Id id = field.getAnnotation(Id.class);
            final Column column = field.getAnnotation(Column.class);
            if (id != null || column != null) {
                final FieldReader reader = readerFactory.apply(field);
                final FieldWriter writer = writerFactory.apply(field);
                fields.add(new FieldMetadata(field, reader, writer));
            }
        }
        final InstanceSupplier supplier = instanceFactory.apply(type.getConstructors()[0]);
        return new EntityMetadata(type, supplier, fields);
    }
}

package org.soujava.metadata.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.soujava.medatadata.api.ClassMappings;
import org.soujava.medatadata.api.EntityMetadata;
import org.soujava.medatadata.api.FieldMetadata;

import java.util.Map;

public class CarTest {

    private ClassMappings mappings = new org.soujava.metadata.processor.ProcessorClassMappings();

    @Test
    public void shouldCreate() {
        final EntityMetadata entityMetadata = mappings.get(Car.class);
        Car car = entityMetadata.newInstance();
        Assert.assertNotNull(car);
    }

    @Test
    public void shouldGetter() {
        final EntityMetadata entityMetadata = mappings.get(Car.class);
        final Map<String, FieldMetadata> fieldsGroupByName = entityMetadata.getFieldsGroupByName();
        final FieldMetadata fieldMetadata = fieldsGroupByName.get("model");
        Car car = entityMetadata.newInstance();
        fieldMetadata.write(car, "native");
        Assertions.assertEquals(car.getModel(), fieldMetadata.read(car));
    }

    @Test
    public void shouldSetter() {
        final EntityMetadata entityMetadata = mappings.get(Car.class);
        final Map<String, FieldMetadata> fieldsGroupByName = entityMetadata.getFieldsGroupByName();
        final FieldMetadata fieldMetadata = fieldsGroupByName.get("name");
        Car car = entityMetadata.newInstance();
        fieldMetadata.write(car, "name");
        Assertions.assertEquals(car.getName(), fieldMetadata.read(car));
    }
}

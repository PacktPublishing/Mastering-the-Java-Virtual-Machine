package org.soujava.metadata.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.soujava.medatadata.api.ClassMappings;
import org.soujava.medatadata.api.EntityMetadata;
import org.soujava.medatadata.api.FieldMetadata;

import java.util.Map;

public class PersonTest {

    private ClassMappings mappings = new org.soujava.metadata.processor.ProcessorClassMappings();

    @Test
    public void shouldCreate() {
        final EntityMetadata entityMetadata = mappings.get(Person.class);
        Person person = entityMetadata.newInstance();
        Assert.assertNotNull(person);
    }

    @Test
    public void shouldGetter() {
        final EntityMetadata entityMetadata = mappings.get(Person.class);
        final Map<String, FieldMetadata> fieldsGroupByName = entityMetadata.getFieldsGroupByName();
        final FieldMetadata fieldMetadata = fieldsGroupByName.get("native");
        Person person = entityMetadata.newInstance();
        fieldMetadata.write(person, "native");
        Assertions.assertEquals(person.getUsername(), fieldMetadata.read(person));
    }

    @Test
    public void shouldSetter() {
        Person person = new org.soujava.metadata.example.PersonEntityMetaData().newInstance();
        new org.soujava.metadata.example.PersonEmailFieldMetaData().write(person, "otavio");
        Assertions.assertEquals("otavio", new org.soujava.metadata.example.PersonEmailFieldMetaData().read(person));
    }
}
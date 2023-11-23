package expert.os.metadata.example;

import expert.os.processor.ProcessorClassMappings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import expert.os.api.ClassMappings;
import expert.os.api.EntityMetadata;
import expert.os.api.FieldMetadata;

import java.util.Map;

public class PersonTest {

    private ClassMappings mappings = new ProcessorClassMappings();

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
        Person person = new expert.os.metadata.example.PersonEntityMetaData().newInstance();
        new expert.os.metadata.example.PersonEmailFieldMetaData().write(person, "otavio");
        Assertions.assertEquals("otavio", new expert.os.metadata.example.PersonEmailFieldMetaData().read(person));
    }
}
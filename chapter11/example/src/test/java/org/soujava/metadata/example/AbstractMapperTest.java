package org.soujava.metadata.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.soujava.medatadata.api.Mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractMapperTest {

    protected Mapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = getMapper();
    }

    protected abstract Mapper getMapper();

    @Test
    public void shouldCreateMap() {
        Person person = new Person();
        person.setId(10L);
        person.setEmail("otaviojava@java.net");
        person.setUsername("otaviojava");
        person.setContacts(Arrays.asList("Poliana", "Bruno Souza", "Yanaga", "Elder"));

        final Map<String, Object> map = mapper.toMap(person);
        Assertions.assertEquals(10L, map.get("id"));
        Assertions.assertEquals("Person", map.get("entity"));
        Assertions.assertEquals("otaviojava", map.get("native"));
        Assertions.assertEquals("otaviojava@java.net", map.get("email"));
        Assertions.assertEquals(person.getContacts(), map.get("contacts"));

    }

    @Test
    public void shouldCreateEntity() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 10L);
        map.put("email","otaviojava@java.net");
        map.put("native","otaviojava");
        map.put("contacts",Arrays.asList("Poliana", "Bruno Souza", "Yanaga", "Elder"));

        final Person person = mapper.toEntity(map, Person.class);
        Assertions.assertEquals(10L, person.getId());
        Assertions.assertEquals("otaviojava", person.getUsername());
        Assertions.assertEquals("otaviojava@java.net", person.getEmail());
        Assertions.assertEquals(person.getContacts(), person.getContacts());
    }
}

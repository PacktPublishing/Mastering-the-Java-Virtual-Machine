package org.soujava.metadata.compiler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompilerTest {

    @Test
    public void test() {
        JavaCompilerBeanPropertyReaderFactory factory = new JavaCompilerBeanPropertyReaderFactory();
        final ReadFromGetterMethod name = factory.generate(Animal.class, "name");
        Animal animal = new Animal("id", "lion");
        final Object apply = name.apply(animal);
        Assertions.assertEquals("lion", apply);

    }
}

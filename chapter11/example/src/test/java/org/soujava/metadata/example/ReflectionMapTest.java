package org.soujava.metadata.example;

import org.soujava.medatadata.api.Mapper;
import org.soujava.metadata.reflection.ReflectionMapper;

public class ReflectionMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ReflectionMapper();
    }


}

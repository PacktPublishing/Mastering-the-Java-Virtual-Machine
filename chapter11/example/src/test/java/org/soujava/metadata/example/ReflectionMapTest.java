package org.soujava.metadata.example;

import expert.os.api.Mapper;
import org.soujava.metadata.reflection.ReflectionMapper;

public class ReflectionMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ReflectionMapper();
    }


}

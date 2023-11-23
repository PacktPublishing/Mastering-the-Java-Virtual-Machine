package org.soujava.metadata.example;

import expert.os.api.Mapper;
import expert.os.processor.ProcessorMap;

public class ProcessorMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ProcessorMap();
    }
}

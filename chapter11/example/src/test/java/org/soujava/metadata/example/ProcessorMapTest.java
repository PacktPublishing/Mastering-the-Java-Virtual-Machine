package org.soujava.metadata.example;

import org.soujava.medatadata.api.Mapper;
import expert.os.processor.ProcessorMap;

public class ProcessorMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ProcessorMap();
    }
}

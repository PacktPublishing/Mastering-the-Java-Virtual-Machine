package org.soujava.metadata.example;

import org.soujava.medatadata.api.Mapper;
import org.soujava.metadata.compiler.CompileMapper;
import org.soujava.metadata.processor.ProcessorMap;

public class CompilerMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new CompileMapper();
    }
}

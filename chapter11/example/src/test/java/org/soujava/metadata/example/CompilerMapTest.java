package org.soujava.metadata.example;

import expert.os.api.Mapper;
import org.soujava.metadata.compiler.CompileMapper;

public class CompilerMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new CompileMapper();
    }
}

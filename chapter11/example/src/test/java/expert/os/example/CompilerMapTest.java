package expert.os.example;

import expert.os.api.Mapper;
import expert.os.metadata.compiler.CompileMapper;

public class CompilerMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new CompileMapper();
    }
}

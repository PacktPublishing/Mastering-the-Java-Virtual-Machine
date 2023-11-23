package expert.os.metadata.example;

import expert.os.api.Mapper;
import expert.os.metadata.reflection.ReflectionMapper;

public class ReflectionMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ReflectionMapper();
    }


}

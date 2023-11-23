package expert.os.example;

import expert.os.api.Mapper;
import expert.os.reflection.ReflectionMapper;

public class ReflectionMapTest extends AbstractMapperTest {

    @Override
    protected Mapper getMapper() {
        return new ReflectionMapper();
    }


}

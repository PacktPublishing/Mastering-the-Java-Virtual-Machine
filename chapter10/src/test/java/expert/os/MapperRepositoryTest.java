package expert.os;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapperRepositoryTest {

    MapperRepository repository = MapperRepository.repository();

    @Test
    public void shouldConvertToMap() {
        Pet ada = Pet.of("Ada", 8);
        Map<String, Object> map = repository.map(ada);

        assertThat(map)
                .isNotNull()
                .isNotEmpty()
                .containsKeys("_entity", "name", "age")
                .containsEntry("name", "Ada")
                .containsEntry("age", 8)
                .containsEntry("_entity", Pet.class.getName());

    }

    @Test
    public void shouldConvertEntity() {
        Map<String, Object> map = Map.of("_entity", Pet.class.getName()
                , "name", "Ada", "age", 8);

        Pet pet = repository.entity(map);

        assertThat(pet).isNotNull()
                .isInstanceOf(Pet.class)
                .matches(p -> p.name().equals("Ada"))
                .matches(p -> p.age() == 8);
    }

    @Test
    public void shouldUseAlias() {

        Map<String, Object> map = Map.of("_entity", Pet.class.getName()
                , "name", "Ada", "age", 8);

        Pet pet = repository.incrementAge(map);

        assertThat(pet).isNotNull()
                .isInstanceOf(Pet.class)
                .matches(p -> p.name().equals("Ada"))
                .matches(p -> p.age() == 9);
    }

}
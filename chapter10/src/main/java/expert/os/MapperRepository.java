package expert.os;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public interface MapperRepository {

    <T> T entity(Map<String, Object> map);

    <T> Map<String, Object> map(T entity);

    default <T> T incrementAge(Map<String, Object> map) {
        Objects.requireNonNull(map, "map is required");
        if (map.containsKey("age")) {
            Map<String, Object> entity = new HashMap<>(map);
            entity.computeIfPresent("age", (k, v) -> {
                Integer age = (Integer) v;
                return ++age;
            });
            return this.entity(entity);
        }
        return this.entity(map);
    }

    static MapperRepository repository() {
        return (MapperRepository) Proxy.newProxyInstance(MapperRepository.class.getClassLoader(),
                new Class[]{MapperRepository.class}, new MapperInvocationHandler());
    }
}

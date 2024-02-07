package expert.os;

import java.util.Map;

public interface Mapper {

    /**
     *
     * @param entity
     * @param <T>
     * @return
     * @throws NullPointerException when the entity is null
     */
    <T> Map<String, Object> toMap(T entity);

    <T> T toEntity(Map<String, Object> map);
}

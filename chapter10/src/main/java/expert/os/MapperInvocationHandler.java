package expert.os;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

public class MapperInvocationHandler implements InvocationHandler {

    private Mapper mapper = new ReflectionMapper();
    @Override
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
        String name = method.getName();
        switch (name) {
            case "entity":
                Map<String, Object> map = (Map<String, Object>) params[0];
                Objects.requireNonNull(map, "map is required");
                return mapper.toEntity(map);
            case "map":
                Object entity = params[0];
                Objects.requireNonNull(entity, "entity is required");
                return mapper.toMap(entity);
        }
        if(method.isDefault()) {
            return InvocationHandler.invokeDefault(proxy, method, params);
        }
        throw new UnsupportedOperationException("The proxy is not supported to the method: " + method);
    }
}

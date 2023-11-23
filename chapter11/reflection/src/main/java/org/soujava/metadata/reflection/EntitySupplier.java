package org.soujava.metadata.reflection;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Id;
import org.soujava.medatadata.api.MapperException;
import org.soujava.medatadata.api.Param;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface EntitySupplier<T> {

    T toEntity(Map<String, Object> map, Class<T> type) throws InvocationTargetException,
            InstantiationException, IllegalAccessException;

    class FieldEntitySupplier<T> implements EntitySupplier<T> {

        private final Constructor<T> constructor;

        FieldEntitySupplier(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T toEntity(Map<String, Object> map, Class<T> type) throws InvocationTargetException,
                InstantiationException, IllegalAccessException {
            final T instance = constructor.newInstance();
            for (Field field : type.getDeclaredFields()) {
                FieldWriter writer = FieldWriter.of(field);
                writer.write(map, instance);
            }
            return instance;
        }
    }

    class ConstructorEntitySupplier<T> implements EntitySupplier<T> {

        private final Constructor<T> constructor;

        ConstructorEntitySupplier(Constructor<T> constructor) {
            this.constructor = constructor;
        }

        @Override
        public T toEntity(Map<String, Object> map, Class<T> type) throws InvocationTargetException,
                InstantiationException, IllegalAccessException {

            Map<String, Field> fieldMap = Stream.of(type.getDeclaredFields())
                    .collect(Collectors.toMap(f -> f.getName(), Function.identity()));

            ArrayList<Object> parameters = new ArrayList<>(constructor.getParameters().length);
            for (Parameter parameter : constructor.getParameters()) {
                Param param = parameter.getAnnotation(Param.class);
                String paramName = Optional.ofNullable(param)
                        .map(Param::value)
                        .orElseThrow(() -> new MapperException("The param in the constructor should have the annotation "
                                + Param.class));
                Field field = fieldMap.get(paramName);
                if (Objects.nonNull(field.getAnnotation(Id.class))) {
                    Id id = field.getAnnotation(Id.class);
                    String name = Optional.of(id).map(Id::value)
                            .filter(s -> !s.isBlank())
                            .orElse(field.getName());
                    parameters.add(map.get(name));
                } else if (Objects.nonNull(field.getAnnotation(Column.class))) {
                    Column column = field.getAnnotation(Column.class);
                    String name = Optional.of(column).map(Column::value)
                            .filter(s -> !s.isBlank())
                            .orElse(field.getName());
                    parameters.add(map.get(name));
                }
            }
            return constructor.newInstance(parameters.toArray());
        }
    }

    static <T> EntitySupplier<T> of(Constructor<T> constructor) {
        if (constructor.getParameters().length == 0) {
            return new FieldEntitySupplier<>(constructor);
        }
        return new ConstructorEntitySupplier<>(constructor);
    }
}

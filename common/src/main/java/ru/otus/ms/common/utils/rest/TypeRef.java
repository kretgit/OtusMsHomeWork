package ru.otus.ms.common.utils.rest;

import lombok.experimental.UtilityClass;
import org.springframework.core.ParameterizedTypeReference;
import ru.otus.ms.common.model.user.User;

import java.util.List;

@UtilityClass
public class TypeRef {

    public static final ParameterizedTypeReference<List<User>> USER_LIST = new ParameterizedTypeReference<>() {
    };

    public <T> ParameterizedTypeReference<T> typeReferenceOf(Class<T> clazz) {
        return ParameterizedTypeReference.forType(clazz);
    }

    public <T> ParameterizedTypeReference<T> typeReferenceOf(T instance) {
        return new ParameterizedTypeReference<T>() {
        };
    }
}

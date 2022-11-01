package de.utopiamc.framework.inject.context;

import java.lang.reflect.Type;

public interface Context {

    <T> T get(Class<T> clazz);
    <T> T get(Type type);

    boolean isPresent(Type type);

}

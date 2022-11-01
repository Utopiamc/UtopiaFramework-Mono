package de.utopiamc.framework.inject.beans;

import java.lang.reflect.Type;

public interface BeanRegistry {

    <T> T get(Type type);

    boolean isPresent(Type type);

    boolean isEnabled();
}

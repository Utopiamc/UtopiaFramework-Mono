package de.utopiamc.framework.inject.beans;

import java.lang.reflect.Type;
import java.util.Set;

public interface BeanRegistry {

    Set<BeanDefinition> getBeanDefinitions();

    <T> T get(Type type);

    boolean isPresent(Type type);

    boolean isEnabled();
}

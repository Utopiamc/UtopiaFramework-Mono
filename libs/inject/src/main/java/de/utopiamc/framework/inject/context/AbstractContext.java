package de.utopiamc.framework.inject.context;

import de.utopiamc.framework.commons.validate.Assertions;
import de.utopiamc.framework.inject.beans.BeanRegistry;
import de.utopiamc.framework.inject.exceptions.BeanRegistryNotEnabledException;

import java.lang.reflect.Type;

public abstract class AbstractContext implements Context {

    @Override
    public <T> T get(Type type) {
        Assertions.requireNonNull(type, "Type can't be null.");
        return checkBeanRegistry().get(type);
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return get((Type) clazz);
    }

    @Override
    public boolean isPresent(Type type) {
        Assertions.requireNonNull(type, "Type can't be null.");
        return checkBeanRegistry().isPresent(type);
    }

    private BeanRegistry checkBeanRegistry() {
        BeanRegistry beanRegistry = getBeanRegistry();
        if (beanRegistry == null || !beanRegistry.isEnabled()) {
            throw new BeanRegistryNotEnabledException();
        }
        return beanRegistry;
    }

    protected abstract BeanRegistry getBeanRegistry();

}

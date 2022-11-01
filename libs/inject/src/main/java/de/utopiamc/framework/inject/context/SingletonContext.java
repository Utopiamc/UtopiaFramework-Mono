package de.utopiamc.framework.inject.context;

import de.utopiamc.framework.inject.beans.BeanRegistry;

public class SingletonContext extends AbstractContext {

    private final BeanRegistry registry;

    public SingletonContext(BeanRegistry beanRegistry) {
        this.registry = beanRegistry;
    }

    @Override
    protected BeanRegistry getBeanRegistry() {
        return registry;
    }
}

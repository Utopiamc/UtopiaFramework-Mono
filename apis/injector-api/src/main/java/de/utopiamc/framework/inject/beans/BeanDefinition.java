package de.utopiamc.framework.inject.beans;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

public interface BeanDefinition {

    @NotNull
    Set<Annotation> getAnnotations();

    Type getType();

    @Nullable
    BeanDefinition getParent();

    @NotNull
    Set<BeanDefinition> getProvidingBeanDefinitions();

}

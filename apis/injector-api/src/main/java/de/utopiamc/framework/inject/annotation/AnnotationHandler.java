package de.utopiamc.framework.inject.annotation;

import de.utopiamc.framework.inject.beans.BeanDefinition;

import java.lang.annotation.Annotation;

@FunctionalInterface
public interface AnnotationHandler {

    HandlerResult handle(Annotation annotation, BeanDefinition beanDefinition);

}

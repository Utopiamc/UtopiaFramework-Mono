package de.utopiamc.framework.inject.annotation;

import de.utopiamc.framework.inject.beans.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface AnnotationHandlerService {

    default void addAnnotationHandlers(Set<Class<? extends Annotation>> annotations, AnnotationHandler handler) {
        annotations.forEach(a -> addAnnotationHandler(a, handler));
    }
    void addAnnotationHandler(Class<? extends Annotation> annotation, AnnotationHandler handler);

    Set<HandlerResult> handleAnnotations(BeanDefinition beanDefinition);

}

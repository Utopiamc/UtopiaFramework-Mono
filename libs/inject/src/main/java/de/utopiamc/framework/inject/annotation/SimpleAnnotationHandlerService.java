package de.utopiamc.framework.inject.annotation;

import de.utopiamc.framework.commons.validate.Assertions;
import de.utopiamc.framework.inject.beans.BeanDefinition;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleAnnotationHandlerService implements AnnotationHandlerService {

    private final Map<Class<? extends Annotation>, Set<AnnotationHandler>> annotationHandlers;

    public SimpleAnnotationHandlerService() {
        this.annotationHandlers = new HashMap<>();
    }

    @Override
    public void addAnnotationHandler(Class<? extends Annotation> annotation, AnnotationHandler handler) {
        Assertions.requireNonNull(annotation, "Annotation should not be null!");
        Assertions.requireNonNull(handler, "AnnotationHandler should not be null!");

        annotationHandlers.putIfAbsent(annotation, new HashSet<>());
        annotationHandlers.get(annotation).add(handler);
    }

    @Override
    public Set<HandlerResult> handleAnnotations(BeanDefinition beanDefinition) {
        Set<HandlerResult> results = new HashSet<>();
        for (Annotation annotation : beanDefinition.getAnnotations()) {
            Set<AnnotationHandler> handlers = annotationHandlers.get(annotation.annotationType());
            for (AnnotationHandler handler : handlers) {
                results.add(handler.handle(annotation, beanDefinition));
            }
        }
        return Collections.unmodifiableSet(results);
    }

}

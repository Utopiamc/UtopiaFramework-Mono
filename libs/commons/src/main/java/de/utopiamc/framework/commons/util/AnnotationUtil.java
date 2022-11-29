/*
 * Copyright 2022 UtopiaMc (https://opensource.utopiamc.de)
 * Copyright 2022 Oskar Wiedeweg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.utopiamc.framework.commons.util;

import de.utopiamc.framework.commons.validate.Assertions;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Set;

@UtilityClass
public class AnnotationUtil {

    public boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return getAnnotation(element, annotationType) != null;
    }

    public <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> annotationType) {
        return getAnnotation(element, annotationType, new HashSet<>());
    }

    @SuppressWarnings("unchecked")
    private <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> annotationType, Set<Annotation> scannedAnnotations) {
        Assertions.requireNonNull(element, "Element should not be null!");
        Assertions.requireNonNull(annotationType, "AnnotationType should not be null!");

        if (element.getAnnotation(annotationType) != null) {
            return element.getAnnotation(annotationType);
        }

        for (Annotation elementAnnotation : element.getAnnotations()) {
            if (scannedAnnotations.contains(elementAnnotation) || isInJavaLangAnnotationPackage(elementAnnotation.annotationType())) {
                continue;
            }
            scannedAnnotations.add(elementAnnotation);

            if (elementAnnotation.annotationType().equals(annotationType)) {
                return (T) elementAnnotation;
            }

            T annotation1 = getAnnotation(element, annotationType, scannedAnnotations);
            if (annotation1 != null) {
                return annotation1;
            }
        }

        if (element instanceof Class<?> clazz) {
            Class<?> superClass;
            if ((superClass = clazz.getSuperclass()) != null) {
                T annotation1 = getAnnotation(superClass, annotationType, scannedAnnotations);
                if (annotation1 != null) {
                    return annotation1;
                }
            }

            for (Class<?> anInterface : clazz.getInterfaces()) {
                T annotation1 = getAnnotation(anInterface, annotationType, scannedAnnotations);
                if (annotation1 != null) {
                    return annotation1;
                }
            }
        }
        return null;
    }


    public Set<Annotation> getAnnotations(AnnotatedElement element) {
        return getAnnotations(element, new HashSet<>());
    }

    private Set<Annotation> getAnnotations(AnnotatedElement element, Set<Annotation> scannedAnnotations) {
        for (Annotation annotation : element.getAnnotations()) {
            if (scannedAnnotations.contains(annotation) || isInJavaLangAnnotationPackage(annotation.annotationType())) {
                continue;
            }
            scannedAnnotations.add(annotation);

            getAnnotations(annotation.annotationType(), scannedAnnotations);
        }

        if (element instanceof Class<?> clazz) {
            Class<?> superClass;
            if ((superClass = clazz.getSuperclass()) != null) {
                getAnnotations(superClass, scannedAnnotations);
            }

            for (Class<?> anInterface : clazz.getInterfaces()) {
                getAnnotations(anInterface, scannedAnnotations);
            }
        }

        return scannedAnnotations;
    }

    public Set<Annotation> getAnnotationsWithAnnotation(AnnotatedElement element, Class<? extends Annotation> annotation) {
        return getAnnotationsWithAnnotation(element, annotation, new HashSet<>());
    }

    private Set<Annotation> getAnnotationsWithAnnotation(AnnotatedElement element, Class<? extends Annotation> annotation, Set<Annotation> scannedAnnotations) {
        for (Annotation elementAnnotation : element.getAnnotations()) {
            if (isAnnotationPresent(elementAnnotation.annotationType(), annotation)) {
                scannedAnnotations.add(elementAnnotation);
            }
        }

        if (element instanceof Class<?> clazz) {
            Class<?> superClass;
            if ((superClass = clazz.getSuperclass()) != null) {
                getAnnotationsWithAnnotation(superClass, annotation, scannedAnnotations);
            }

            for (Class<?> anInterface : clazz.getInterfaces()) {
                getAnnotationsWithAnnotation(anInterface, annotation, scannedAnnotations);
            }
        }

        return scannedAnnotations;
    }

    private boolean isInJavaLangAnnotationPackage(Class<? extends Annotation> annotation) {
        return annotation.getPackage().getName().startsWith("java.lang.annotation");
    }

}

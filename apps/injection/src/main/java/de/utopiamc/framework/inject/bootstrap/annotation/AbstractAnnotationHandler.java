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

package de.utopiamc.framework.inject.bootstrap.annotation;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import de.utopiamc.framework.commons.util.AnnotationUtil;
import de.utopiamc.framework.inject.bootstrap.ClassBootstrapContext;

public abstract class AbstractAnnotationHandler implements AnnotationHandler {

	@Override
	public void handleAnnotations(ClassBootstrapContext context) {
		Class<?> cls = context.getType();
		Set<Annotation> annotations = AnnotationUtil.getAnnotations(cls);
		Map<Class<? extends Annotation>, AnnotationProcessor> annotationProcessors = getAnnotationProcessors();

		for (Annotation annotation : annotations) {
			AnnotationProcessor annotationProcessor = annotationProcessors.get(annotation.annotationType());
			if (annotationProcessor != null) {
				annotationProcessor.process(cls, context);
			}
		}
	}

	protected abstract Map<Class<? extends Annotation>, AnnotationProcessor> getAnnotationProcessors();

}

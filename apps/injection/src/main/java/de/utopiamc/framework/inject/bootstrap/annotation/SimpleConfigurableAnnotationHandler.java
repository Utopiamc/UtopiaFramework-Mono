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
import java.util.HashMap;
import java.util.Map;

import de.utopiamc.framework.inject.AssistedFactory;
import de.utopiamc.framework.inject.annotations.Component;
import de.utopiamc.framework.inject.annotations.InjectStatic;
import de.utopiamc.framework.inject.assist.AssistedFactoryAnnotationProcessor;
import de.utopiamc.framework.inject.assist.AssistedFactoryHandler;
import de.utopiamc.framework.inject.bootstrap.annotation.processors.ComponentAnnotationProcessor;
import de.utopiamc.framework.inject.bootstrap.annotation.processors.InjectStaticAnnotationProcessor;

public class SimpleConfigurableAnnotationHandler extends AbstractAnnotationHandler implements ConfigurableAnnotationHandler {

	private final Map<Class<? extends Annotation>, AnnotationProcessor> annotationProcessors;

	public SimpleConfigurableAnnotationHandler() {
		this(new HashMap<>());
	}

	public SimpleConfigurableAnnotationHandler(Map<Class<? extends Annotation>, AnnotationProcessor> annotationProcessors) {
		this.annotationProcessors = annotationProcessors;

		addDefaultAnnotationProcessors();
	}

	protected void addDefaultAnnotationProcessors() {
		annotationProcessors.put(Component.class, new ComponentAnnotationProcessor());
		annotationProcessors.put(InjectStatic.class, new InjectStaticAnnotationProcessor());

		annotationProcessors.put(AssistedFactory.class, new AssistedFactoryAnnotationProcessor(new AssistedFactoryHandler()));
	}

	@Override
	public void addAnnotationHandler(Class<? extends Annotation> annotation, AnnotationProcessor processor) {
		annotationProcessors.put(annotation, processor);
	}

	@Override
	protected Map<Class<? extends Annotation>, AnnotationProcessor> getAnnotationProcessors() {
		return annotationProcessors;
	}
}

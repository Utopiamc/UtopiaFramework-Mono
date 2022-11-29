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

package de.utopiamc.framework.inject.bootstrap.annotation.processors;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Set;

import de.utopiamc.framework.commons.util.AnnotationUtil;
import de.utopiamc.framework.inject.bootstrap.ClassBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.annotation.AnnotationProcessor;

import com.google.inject.Binder;

public class ComponentAnnotationProcessor implements AnnotationProcessor {

	private static final Set<Class<? extends Annotation>> INJECT_ANNOTATIONS = Set.of(javax.inject.Inject.class,
			com.google.inject.Inject.class);
	@Override
	public void process(Class<?> cls, ClassBootstrapContext context) {
		context.getBinder((binder -> bind(binder, cls)));
	}

	private <S> void bind(Binder binder, Class<S> sClass) {
		binder.bind(sClass).toConstructor(findSuitableConstructor(sClass));
	}

	@SuppressWarnings("unchecked")
	private <S> Constructor<S> findSuitableConstructor(Class<S> sClass) {
		Constructor<?>[] constructors = sClass.getConstructors();
		if (constructors.length == 1) {
			return (Constructor<S>) constructors[0];
		}

		Constructor<S> suitableConstructor = null;
		for (Constructor<?> constructor : constructors) {
			for (Class<? extends Annotation> injectAnnotation : INJECT_ANNOTATIONS) {
				if (AnnotationUtil.isAnnotationPresent(constructor, injectAnnotation)) {
					if (suitableConstructor != null && suitableConstructor != constructor) {
						// TODO: Throw to many annotated constructors!
					}
					suitableConstructor = (Constructor<S>) constructor;
				}
			}
		}

		if (suitableConstructor == null) {
			// TODO: Throw no constructor found!
		}
		return suitableConstructor;
	}

}

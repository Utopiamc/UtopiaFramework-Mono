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

package de.utopiamc.framework.inject.bootstrap;

import java.io.File;
import java.util.List;

import de.utopiamc.framework.inject.InjectionContext;
import de.utopiamc.framework.inject.ModuleBootstrapper;
import de.utopiamc.framework.inject.bootstrap.annotation.AnnotationHandler;
import de.utopiamc.framework.inject.bootstrap.context.BootstrapContext;
import de.utopiamc.framework.inject.bootstrap.context.ConfigurableBootstrapContext;

import com.google.inject.Injector;

public abstract class AbstractModuleBootstrapper implements ModuleBootstrapper {

	private final AnnotationHandler annotationHandler;

	public AbstractModuleBootstrapper(AnnotationHandler annotationHandler) {
		this.annotationHandler = annotationHandler;
	}

	@Override
	public BootstrapContext createBootstrapContext(File file) {
		ConfigurableBootstrapContext context = doCreateBootstrapContext(file);

		context.configure();

		configureContext(context);

		return context;
	}

	@Override
	public InjectionContext bootstrapModule(BootstrapContext context, Injector parent) {
		return doBootstrapModule(context, parent);
	}

	protected void handleAnnotations(List<ClassBootstrapContext> contexts) {
		for (ClassBootstrapContext context : contexts) {
			annotationHandler.handleAnnotations(context);
		}
	}

	protected abstract ConfigurableBootstrapContext doCreateBootstrapContext(File file);
	protected abstract void configureContext(ConfigurableBootstrapContext context);
	protected abstract InjectionContext doBootstrapModule(BootstrapContext context, Injector parent);

}

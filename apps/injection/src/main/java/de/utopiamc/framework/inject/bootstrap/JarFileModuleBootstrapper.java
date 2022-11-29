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
import java.util.stream.Collectors;

import de.utopiamc.framework.commons.validate.Assertions;
import de.utopiamc.framework.inject.InjectionContext;
import de.utopiamc.framework.inject.JarBootstrapContext;
import de.utopiamc.framework.inject.JarInjectionContext;
import de.utopiamc.framework.inject.bootstrap.annotation.AnnotationHandler;
import de.utopiamc.framework.inject.bootstrap.context.BootstrapContext;
import de.utopiamc.framework.inject.bootstrap.context.ConfigurableBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.context.support.JarFileBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.injector.InjectorCreator;
import de.utopiamc.framework.inject.bootstrap.injector.SimpleInjectorCreator;
import de.utopiamc.framework.inject.module.ConfigurableModule;

import com.google.inject.Injector;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

public class JarFileModuleBootstrapper extends AbstractModuleBootstrapper {

	private final InjectorCreator creator;

	public JarFileModuleBootstrapper(AnnotationHandler annotationHandler, InjectorCreator creator) {
		super(annotationHandler);
		this.creator = creator;
	}

	public JarFileModuleBootstrapper(AnnotationHandler annotationHandler) {
		super(annotationHandler);
		this.creator = new SimpleInjectorCreator();
	}

	@Override
	protected ConfigurableBootstrapContext doCreateBootstrapContext(File file) {
		return new JarFileBootstrapContext(file);
	}

	@Override
	protected InjectionContext doBootstrapModule(BootstrapContext context, Injector parent) {
		Injector injector = creator.createInjector(parent, new ConfigurableModule(context.produceModule()));
		JarInjectionContext injectionContext = new JarInjectionContext(injector);

		return injectionContext;
	}

	@Override
	protected void configureContext(ConfigurableBootstrapContext rootContext) {
		Assertions.requireTrue(rootContext instanceof JarFileBootstrapContext,
				"Context is no JarFileBootstrapContext!");
		JarFileBootstrapContext context = (JarFileBootstrapContext) rootContext;

		applyClasses(context);
		handleAnnotations(context.getClassBootstrapContexts());
	}

	private void applyClasses(JarFileBootstrapContext context) {
		try (ScanResult scan = new ClassGraph()
				.enableClassInfo()
				.overrideClassLoaders(context.getClassLoader())
				.ignoreParentClassLoaders()
				.scan()) {

			context.applyClassBootstrapContexts(scan.getAllClasses()
					.loadClasses()
					.stream()
					.map((cls) -> makeClassBootstrapContext(cls, context))
					.collect(Collectors.toList()));
		}
	}

	private ClassBootstrapContext makeClassBootstrapContext(Class<?> cls, JarFileBootstrapContext context) {
		ClassBootstrapContext classBootstrapContext = new SimpleClassBootstrapContext(context, cls);

		return classBootstrapContext;
	}

}

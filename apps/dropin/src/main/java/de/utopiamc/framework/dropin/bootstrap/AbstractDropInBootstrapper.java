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

package de.utopiamc.framework.dropin.bootstrap;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import de.utopiamc.framework.dropin.DropIn;
import de.utopiamc.framework.dropin.bootstrap.context.DropInBootstrapContext;
import de.utopiamc.framework.dropin.bootstrap.context.JarFileDropInBootstrapContext;
import de.utopiamc.framework.inject.InjectionContext;
import de.utopiamc.framework.inject.JarBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.JarFileModuleBootstrapper;
import de.utopiamc.framework.inject.bootstrap.annotation.ConfigurableAnnotationHandler;
import de.utopiamc.framework.inject.bootstrap.annotation.SimpleConfigurableAnnotationHandler;
import de.utopiamc.framework.inject.bootstrap.context.BootstrapContext;

public abstract class AbstractDropInBootstrapper implements DropInBootstrapper {

	public static final String JAR_FILE_EXTENSION = ".jar";
	private final ConfigurableAnnotationHandler annotationHandler;
	private final JarFileModuleBootstrapper bootstrapper;

	public AbstractDropInBootstrapper() {
		this(new SimpleConfigurableAnnotationHandler());
	}

	public AbstractDropInBootstrapper(ConfigurableAnnotationHandler annotationHandler) {
		this.annotationHandler = annotationHandler;
		this.bootstrapper = new JarFileModuleBootstrapper(annotationHandler);
	}

	@Override
	public Map<String, DropIn> bootstrapDropIns(Set<File> files) {
		Map<String, DropInBootstrapContext> dropInContexts = new HashMap<>();
		for (File file : files) {
			DropInBootstrapContext context = createContext(file);
			dropInContexts.put(context.getId(), context);
		}

		Map<String, DropIn> runningDropIns = new HashMap<>();
		Set<String> failedDropIns = new HashSet<>();

		for (DropInBootstrapContext value : dropInContexts.values()) {
			runningDropIns.put(value.getId(), runDropIn(value, runningDropIns, dropInContexts, failedDropIns,
					new LinkedList<>()));
		}

		return runningDropIns;
	}

	private DropIn runDropIn(DropInBootstrapContext context, Map<String, DropIn> runningDropIns, Map<String,
			DropInBootstrapContext> availableDropIns, Set<String> failedDropIns, LinkedList<String> stack) {
		String dropInId = context.getId();
		if (stack.contains(dropInId)) {
			// TODO: Handle cyclic dependency
			return null;
		}
		stack.addFirst(dropInId);

		for (String dependency : context.getDependencies()) {
			if (!availableDropIns.containsKey(dependency)) {
				// TODO: Handle unsatisfied dependency
				failedDropIns.add(dropInId);
				return null;
			}
			// TODO: Resolve Dependency
		}

		BootstrapContext bootstrapContext = context.getBootstrapContext();
		InjectionContext injectionContext = bootstrapper.bootstrapModule(bootstrapContext, null);

		return new DropIn(injectionContext);
	}

	protected DropInBootstrapContext createContext(File file) {
		if (!file.getName().endsWith(JAR_FILE_EXTENSION)) {
			return null;
		}

		JarFileDropInBootstrapContext context = new JarFileDropInBootstrapContext(file);
		JarBootstrapContext bootstrapContext = (JarBootstrapContext) bootstrapper.createBootstrapContext(file);

		context.setBoostrapContext(bootstrapContext);
		
		return context;
	}


}

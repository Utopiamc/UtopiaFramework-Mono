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

package de.utopiamc.framework.inject.bootstrap.context;

import java.io.File;

import de.utopiamc.framework.inject.module.ConfigurableModule;
import de.utopiamc.framework.inject.module.ModuleConfigurer;

import com.google.inject.Module;

public abstract class AbstractBootstrapContext implements ConfigurableBootstrapContext {

	private final File file;

	private ModuleConfigurer moduleConfigurer;

	public AbstractBootstrapContext(File file) {
		this.file = file;
		this.moduleConfigurer = (binder) -> {};
	}

	@Override
	public void configure() {
		doConfigure();
	}

	@Override
	public ModuleConfigurer produceModule() {
		return moduleConfigurer;
	}

	protected abstract void doConfigure();

	@Override
	public File getFile() {
		return file;
	}
}

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

package de.utopiamc.framework.inject.bootstrap.context.support;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import de.utopiamc.framework.inject.JarBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.ClassBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.context.AbstractBootstrapContext;

public class JarFileBootstrapContext extends AbstractBootstrapContext implements JarBootstrapContext {

	private ClassLoader loader;
	private List<ClassBootstrapContext> classBootstrapContexts;

	public JarFileBootstrapContext(File file) {
		super(file);
	}

	@Override
	protected void doConfigure() {
		loadJar();
	}

	protected void loadJar() {
		try {
			URI fileUri = getFile().toURI();
			this.loader = new URLClassLoader(new URL[]{fileUri.toURL()}, getClass().getClassLoader());
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public void applyClassBootstrapContexts(List<ClassBootstrapContext> classBootstrapContexts) {
		this.classBootstrapContexts = classBootstrapContexts;
	}

	@Override
	public ClassLoader getClassLoader() {
		return loader;
	}

	public List<ClassBootstrapContext> getClassBootstrapContexts() {
		return classBootstrapContexts;
	}
}

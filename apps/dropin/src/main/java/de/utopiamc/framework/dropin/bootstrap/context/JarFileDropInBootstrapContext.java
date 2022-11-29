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

package de.utopiamc.framework.dropin.bootstrap.context;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import de.utopiamc.framework.dropin.bootstrap.support.DropInIdResolver;
import de.utopiamc.framework.dropin.bootstrap.support.JarIdResolver;
import de.utopiamc.framework.dropin.config.DropInConfiguration;
import de.utopiamc.framework.inject.JarBootstrapContext;
import de.utopiamc.framework.inject.bootstrap.context.BootstrapContext;

import com.google.gson.Gson;

public class JarFileDropInBootstrapContext extends AbstractDropInBootstrapContext {

	private BootstrapContext context;
	private DropInIdResolver idResolver;

	public JarFileDropInBootstrapContext(File file) {
		this.fileName = file.getName();
		this.configuration = findDropInConfiguration(file);
		this.idResolver = new JarIdResolver();
	}

	private DropInConfiguration findDropInConfiguration(File file) {
		try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()})) {
			InputStream resourceAsStream = classLoader.getResourceAsStream("config.json");
			if (resourceAsStream == null) {
				return new DropInConfiguration();
			}

			Gson gson = new Gson();
			DropInConfiguration dropInConfiguration = gson.fromJson(new InputStreamReader(resourceAsStream),
					DropInConfiguration.class);

			return dropInConfiguration;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected String resolveId() {
		return idResolver.resolveName(this);
	}

	public void setIdResolver(DropInIdResolver idResolver) {
		this.idResolver = idResolver;
	}

	public void setBoostrapContext(JarBootstrapContext bootstrapContext) {
		this.context = bootstrapContext;
	}

	@Override
	public BootstrapContext getBootstrapContext() {
		return context;
	}

	@Override
	public Set<String> getDependencies() {
		return Set.of();
	}
}

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

import de.utopiamc.framework.dropin.config.DropInConfiguration;

public abstract class AbstractDropInBootstrapContext implements DropInBootstrapContext {

	private String id;
	protected String fileName;
	protected DropInConfiguration configuration;

	@Override
	public DropInConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getId() {
		if (id == null) {
			this.id = resolveId();
		}
		return id;
	}

	protected abstract String resolveId();
}

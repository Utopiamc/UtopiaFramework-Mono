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

import java.util.function.Consumer;

import de.utopiamc.framework.inject.bootstrap.context.BootstrapContext;

import com.google.inject.Binder;

public class SimpleClassBootstrapContext implements ClassBootstrapContext {

	private final BootstrapContext context;
	private final Class<?> type;

	public SimpleClassBootstrapContext(BootstrapContext context, Class<?> type) {
		this.context = context;
		this.type = type;
	}

	@Override
	public BootstrapContext getBootstrapContext() {
		return context;
	}

	@Override
	public Class<?> getType() {
		return type;
	}

	@Override
	public void getBinder(Consumer<Binder> binder) {

	}
}

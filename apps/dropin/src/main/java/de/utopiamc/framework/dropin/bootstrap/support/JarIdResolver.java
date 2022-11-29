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

package de.utopiamc.framework.dropin.bootstrap.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.utopiamc.framework.dropin.bootstrap.AbstractDropInBootstrapper;
import de.utopiamc.framework.dropin.bootstrap.context.DropInBootstrapContext;

public class JarIdResolver implements DropInIdResolver {

	private final Pattern versionPattern = Pattern.compile("(-)?(0|[1-9]\\d*)(\\.(0|[1-9]\\d*))?(\\.(0|[1-9]\\d*))?(?:-(" +
			"(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\\.(?:0|[1-9]\\d*|\\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\\+([0-9a-zA-Z-]+(?:\\.[0-9a-zA-Z-]+)*))?");

	@Override
	public String resolveName(DropInBootstrapContext context) {
		if (context.getConfiguration().getId() != null) {
			return context.getConfiguration().getId();
		}
		String unCutFileName = context.getFileName();
		String fileName = unCutFileName.substring(0,
				unCutFileName.length() - AbstractDropInBootstrapper.JAR_FILE_EXTENSION.length());
		Matcher matcher = versionPattern.matcher(fileName);
		fileName = matcher.replaceAll("");

		return fileName;
	}
}

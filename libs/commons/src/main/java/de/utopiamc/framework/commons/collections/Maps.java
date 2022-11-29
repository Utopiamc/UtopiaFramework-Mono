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

package de.utopiamc.framework.commons.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Maps {

	public <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<>();
	}

	public <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
		return new LinkedHashMap<>();
	}

	public <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<>();
	}

	public <K extends Comparable<?>, V> TreeMap<K, V> newTreeMap() {
		return new TreeMap<>();
	}

}

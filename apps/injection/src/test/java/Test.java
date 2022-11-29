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

import java.rmi.Naming;
import javax.inject.Named;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Test {

	private final String s;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new AbstractModule() {

			@Provides
			@Named("test")
			public Test getTest() {
				return new Test("test");
			}

			@Provides
			public Test getDefaultTest() {
				return new Test("default");
			}

		});

		System.out.println(injector.getInstance(Key.get(Test.class, Names.named("test"))));
		System.out.println(injector.getInstance(Test.class));
	}

}

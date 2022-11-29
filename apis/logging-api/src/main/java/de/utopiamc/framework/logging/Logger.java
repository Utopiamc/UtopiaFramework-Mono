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

package de.utopiamc.framework.logging;

public interface Logger {

	boolean isTraceEnabled();

	void trace(String message);

	void trace(String format, Object... args);

	void trace(String message, Throwable throwable);


	boolean isDebugEnabled();

	void debug(String message);

	void debug(String format, Object... args);

	void debug(String message, Throwable throwable);


	boolean isInfoEnabled();

	void info(String message);

	void info(String format, Object... args);

	void info(String format, Throwable throwable);


	boolean isWarnEnabled();

	void warn(String message);

	void warn(String format, Object... args);

	void warn(String format, Throwable throwable);


	boolean isErrorEnabled();

	void error(String message);

	void error(String format, Object... args);

	void error(String format, Throwable throwable);

}

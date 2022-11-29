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

public class Log4jLogger implements Logger {

	private final org.apache.log4j.Logger internalLogger;

	public Log4jLogger(org.apache.log4j.Logger internalLogger) {
		this.internalLogger = internalLogger;
	}

	@Override
	public boolean isTraceEnabled() {
		return internalLogger.isTraceEnabled();
	}

	@Override
	public void trace(String message) {
		internalLogger.trace(message);
	}

	@Override
	public void trace(String format, Object... args) {
		internalLogger.trace(String.format(format, args));
	}

	@Override
	public void trace(String message, Throwable throwable) {
		internalLogger.trace(message, throwable);
	}

	@Override
	public boolean isDebugEnabled() {
		return internalLogger.isDebugEnabled();
	}

	@Override
	public void debug(String message) {
		internalLogger.debug(message);
	}

	@Override
	public void debug(String format, Object... args) {
		internalLogger.debug(String.format(format, args));
	}

	@Override
	public void debug(String message, Throwable throwable) {
		internalLogger.debug(message, throwable);
	}

	@Override
	public boolean isInfoEnabled() {
		return internalLogger.isInfoEnabled();
	}

	@Override
	public void info(String message) {
		internalLogger.info(message);
	}

	@Override
	public void info(String format, Object... args) {
		internalLogger.info(String.format(format, args));
	}

	@Override
	public void info(String format, Throwable throwable) {
		internalLogger.info(format, throwable);
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void warn(String message) {
		internalLogger.warn(message);
	}

	@Override
	public void warn(String format, Object... args) {
		internalLogger.warn(String.format(format, args));
	}

	@Override
	public void warn(String format, Throwable throwable) {
		internalLogger.warn(format, throwable);
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public void error(String message) {
		internalLogger.error(message);
	}

	@Override
	public void error(String format, Object... args) {
		internalLogger.error(String.format(format, args));
	}

	@Override
	public void error(String format, Throwable throwable) {
		internalLogger.error(format, throwable);
	}
}

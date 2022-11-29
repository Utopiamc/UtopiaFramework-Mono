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

import java.util.logging.Level;

public class DefaultJavaLogger implements Logger {

	private final java.util.logging.Logger logger;

	public DefaultJavaLogger(java.util.logging.Logger logger) {
		this.logger = logger;
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINER);
	}

	@Override
	public void trace(String message) {
		logger.finer(message);
	}

	@Override
	public void trace(String format, Object... args) {
		logger.finer(String.format(format, args));
	}

	@Override
	public void trace(String message, Throwable throwable) {
		trace(message);
		throwable.printStackTrace();
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isLoggable(Level.FINE);
	}

	@Override
	public void debug(String message) {
		logger.fine(message);
	}

	@Override
	public void debug(String format, Object... args) {
		logger.fine(String.format(format, args));
	}

	@Override
	public void debug(String message, Throwable throwable) {
		debug(message);
		throwable.printStackTrace();
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isLoggable(Level.INFO);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void info(String format, Object... args) {
		logger.info(String.format(format, args));
	}

	@Override
	public void info(String format, Throwable throwable) {
		info(format);
		throwable.printStackTrace();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isLoggable(Level.WARNING);
	}

	@Override
	public void warn(String message) {
		logger.warning(message);
	}

	@Override
	public void warn(String format, Object... args) {
		logger.warning(String.format(format, args));
	}

	@Override
	public void warn(String format, Throwable throwable) {
		warn(format);
		throwable.printStackTrace();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isLoggable(Level.SEVERE);
	}

	@Override
	public void error(String message) {
		logger.severe(message);
	}

	@Override
	public void error(String format, Object... args) {
		logger.severe(String.format(format, args));
	}

	@Override
	public void error(String format, Throwable throwable) {
		error(format);
		throwable.printStackTrace();
	}
}

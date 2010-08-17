/*
 * Copyright [mikiones] [Michal Kinasiewicz]
 * 			 [marcel]   [Marcel Gheorghita] 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.openmu.logger.formatters;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 
 * @author mikiones
 */
public class MuConsoleLogFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		String entry = "";
		entry += "["
				+ DateFormat.getTimeInstance().format(
						new Date(record.getMillis())) + "]"; // [date]
		entry += record.getLevel().getName().substring(0, 3) + ":";
		entry += record.getLoggerName() + "|>";// [who/what]
		entry += record.getMessage() + " \n";
		return entry;
	}

}
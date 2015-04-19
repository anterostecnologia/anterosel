/*******************************************************************************
 * Copyright 2012 Anteros Tecnologia
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package br.com.anteros.el.misc;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import br.com.anteros.core.resource.messages.AnterosBundle;
import br.com.anteros.core.resource.messages.AnterosResourceBundle;
import br.com.anteros.el.resource.messages.AnterosELMessages;

public final class LocalMessages {
	private static final String BUNDLE_NAME = "ANTEROS_EL";
	private static AnterosBundle MESSAGES = AnterosResourceBundle.getBundle(BUNDLE_NAME,AnterosELMessages.class);

	public static String get(String key, Object... args) {
		String template = null;
		try {
			template = MESSAGES.getMessage(key);
		} catch (MissingResourceException e) {
			StringBuilder b = new StringBuilder();
			try {
				b.append(MESSAGES.getMessage("message.unknown"));
				b.append(": ");
			} catch (MissingResourceException e2) {}
			b.append(key);
			if (args != null && args.length > 0) {
				b.append("(");
				b.append(args[0]);
				for (int i = 1; i < args.length; i++) {
					b.append(", ");
					b.append(args[i]);
				}
				b.append(")");
			}
			return b.toString();
		}
		return MessageFormat.format(template, args);
	}
}

/*
 * Java Watchdog Project.
 * Copyright (C) 2013 Frank Cornelis.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version
 * 3.0 as published by the Free Software Foundation.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, see 
 * http://www.gnu.org/licenses/.
 */

package be.e_contract.jwatchdog.notifier.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog.notifier.Notifier;

public class ScriptNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(ScriptNotifier.class);

	private final String mimeType;

	private final String script;

	public ScriptNotifier(String mimeType, String script) {
		this.mimeType = mimeType;
		this.script = script;
	}

	@Override
	public void notify(String message) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager
				.getEngineByMimeType(this.mimeType);
		if (null == scriptEngine) {
			LOG.error("unsupported script language: " + this.mimeType);
			return;
		}
		scriptEngine.put("message", message);
		try {
			scriptEngine.eval(this.script);
		} catch (ScriptException e) {
			LOG.error("script error: " + e.getMessage());
		}
	}
}

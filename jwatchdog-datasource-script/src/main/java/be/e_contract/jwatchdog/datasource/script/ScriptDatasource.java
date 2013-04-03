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

package be.e_contract.jwatchdog.datasource.script;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.datasource.Datasource;

public class ScriptDatasource implements Datasource {

	private static final Log LOG = LogFactory.getLog(ScriptDatasource.class);

	private final String mimeType;

	private final String script;

	public double[] values;

	private int minutes;

	public ScriptDatasource(String mimeType, String script) {
		this.mimeType = mimeType;
		this.script = script;
	}

	@Override
	public double[] getValues(int minutes) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager
				.getEngineByMimeType(this.mimeType);
		if (null == scriptEngine) {
			LOG.error("unsupported script language: " + this.mimeType);
			return new double[] {};
		}
		this.minutes = minutes;
		ScriptObject scriptObject = new ScriptObject(this);
		scriptEngine.put("jwatchdog", scriptObject);
		try {
			scriptEngine.eval(this.script);
		} catch (ScriptException e) {
			LOG.error("script error: " + e.getMessage());
			return new double[] {};
		}
		return this.values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public int getMinutes() {
		return this.minutes;
	}

	@Override
	public void init(Context context) {
		// empty
	}
}

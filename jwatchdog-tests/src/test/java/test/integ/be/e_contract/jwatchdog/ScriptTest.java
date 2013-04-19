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

package test.integ.be.e_contract.jwatchdog;

import java.util.List;

import javax.script.Compilable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class ScriptTest {

	private static final Log LOG = LogFactory.getLog(ScriptTest.class);

	@Test
	public void testScriptEngineManager() throws Exception {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		List<ScriptEngineFactory> scriptEngineFactories = scriptEngineManager
				.getEngineFactories();
		for (ScriptEngineFactory scriptEngineFactory : scriptEngineFactories) {
			LOG.debug("script language: "
					+ scriptEngineFactory.getLanguageName());
			List<String> mimetypes = scriptEngineFactory.getMimeTypes();
			LOG.debug("\tmimetypes: " + mimetypes);
			LOG.debug("\tengine name: " + scriptEngineFactory.getEngineName());
			LOG.debug("\tengine version: "
					+ scriptEngineFactory.getEngineVersion());
			LOG.debug("\tlanguage name: "
					+ scriptEngineFactory.getLanguageName());
			LOG.debug("\tlanguage version: "
					+ scriptEngineFactory.getLanguageVersion());
			ScriptEngine scriptEngine = scriptEngineFactory.getScriptEngine();
			boolean canCompile = scriptEngine instanceof Compilable;
			LOG.debug("\tcan compile: " + canCompile);
		}
	}
}

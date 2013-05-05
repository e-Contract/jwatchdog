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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Locator;

import be.e_contract.jwatchdog.datasource.AbstractDatasourceProvider;
import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.script.jaxb.config.ObjectFactory;
import be.e_contract.jwatchdog.datasource.script.jaxb.config.ScriptType;

public class ScriptDatasourceProvider extends
		AbstractDatasourceProvider<ScriptType> {

	private static final Log LOG = LogFactory
			.getLog(ScriptDatasourceProvider.class);

	public ScriptDatasourceProvider() {
		super("urn:be:e-contract:jwatchdog:datasource:script:1.0",
				ObjectFactory.class, "/jwatchdog-datasource-script-config.xsd");
	}

	@Override
	public Datasource loadDatasource(ScriptType config) throws Exception {
		Locator locator = config.sourceLocation();
		LOG.debug("script:script line number: " + locator.getLineNumber());
		String mimeType = config.getType();
		String script = config.getValue();
		return new ScriptDatasource(mimeType, script);
	}
}

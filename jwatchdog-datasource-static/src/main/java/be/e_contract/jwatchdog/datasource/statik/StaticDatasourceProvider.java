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

package be.e_contract.jwatchdog.datasource.statik;

import be.e_contract.jwatchdog.datasource.AbstractDatasourceProvider;
import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.statik.jaxb.config.ObjectFactory;
import be.e_contract.jwatchdog.datasource.statik.jaxb.config.StaticType;

public class StaticDatasourceProvider extends
		AbstractDatasourceProvider<StaticType> {

	public StaticDatasourceProvider() {
		super("urn:be:e-contract:jwatchdog:datasource:static:1.0",
				ObjectFactory.class, "/jwatchdog-datasource-static-config.xsd");
	}

	@Override
	public Datasource loadDatasource(StaticType config) throws Exception {
		return new StaticDatasource(config);
	}
}

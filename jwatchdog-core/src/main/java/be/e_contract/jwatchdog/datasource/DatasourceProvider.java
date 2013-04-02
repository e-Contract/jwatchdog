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

package be.e_contract.jwatchdog.datasource;

import java.util.Set;

import org.w3c.dom.Element;

/**
 * Interface for a provider of datasources. Register your datasource provider
 * implementation via
 * <code>META-INF/services/be.e_contract.jwatchdog.datasource.DatasourceProvider</code>
 * 
 * @author Frank Cornelis
 * 
 */
public interface DatasourceProvider {

	/**
	 * The XML configuration namespaces that this datasource provider is capable
	 * of handling.
	 * 
	 * @return
	 */
	Set<String> getConfigNamespaces();

	/**
	 * Loads a datasource instance given the DOM configuration element.
	 * 
	 * @param configElement
	 * @return
	 * @throws Exception
	 */
	Datasource loadDatasource(Element configElement) throws Exception;
}

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import be.e_contract.jwatchdog.jaxb.config.DatasourceType;

/**
 * The factory for datasources.
 * 
 * @author Frank Cornelis
 * 
 */
public class DatasourceFactory {

	private static final Log LOG = LogFactory.getLog(DatasourceFactory.class);

	private final List<DatasourceType> datasourcesConfig;

	private static ServiceLoader<DatasourceProvider> datasourceProviderLoader = ServiceLoader
			.load(DatasourceProvider.class);

	public DatasourceFactory(List<DatasourceType> datasourcesConfig) {
		this.datasourcesConfig = datasourcesConfig;
	}

	public Map<String, Datasource> loadDatasources() {
		Map<String, Datasource> datasources = new HashMap<String, Datasource>();
		for (DatasourceType datasourceConfig : this.datasourcesConfig) {
			String name = datasourceConfig.getName();
			LOG.debug("loading datasource: " + name);
			Element configElement = (Element) datasourceConfig.getAny();
			String configNamespace = configElement.getNamespaceURI();
			DatasourceProvider datasourceProvider = findDatasourceProvider(configNamespace);
			if (null == datasourceProvider) {
				LOG.error("unknown datasource config namespace: "
						+ configNamespace);
				continue;
			}
			try {
				Datasource datasource = datasourceProvider
						.loadDatasource(configElement);
				datasources.put(name, datasource);
			} catch (Exception e) {
				LOG.error("error loading data source: " + e.getMessage());
			}
		}
		return datasources;
	}

	private DatasourceProvider findDatasourceProvider(String configNamespace) {
		for (DatasourceProvider datasourceProvider : datasourceProviderLoader) {
			if (datasourceProvider.getConfigNamespaces().contains(
					configNamespace)) {
				return datasourceProvider;
			}
		}
		return null;
	}
}

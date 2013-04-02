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

package be.e_contract.jwatchdog.datasource.rrd;

import java.util.Collections;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.DatasourceProvider;
import be.e_contract.jwatchdog.datasource.rrd.jaxb.config.ObjectFactory;
import be.e_contract.jwatchdog.datasource.rrd.jaxb.config.RrdType;

public class RRDDatasourceProvider implements DatasourceProvider {

	private static final Log LOG = LogFactory
			.getLog(RRDDatasourceProvider.class);

	private final Unmarshaller unmarshaller;

	public RRDDatasourceProvider() {
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ObjectFactory.class);
			this.unmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			throw new RuntimeException("JAXB error: " + e.getMessage());
		}
	}

	@Override
	public Set<String> getConfigNamespaces() {
		return Collections
				.singleton("urn:be:e-contract:jwatchdog:datasource:rrd:1.0");
	}

	@Override
	public Datasource loadDatasource(Element configElement) throws Exception {
		LOG.debug("loading datasource");
		JAXBElement<RrdType> rrdElement = (JAXBElement<RrdType>) this.unmarshaller
				.unmarshal(new DOMSource(configElement));
		RrdType rrdConfig = rrdElement.getValue();
		String rrdFilename = rrdConfig.getFile();
		String datasourceName = rrdConfig.getDataSourceName();
		return new RRDDatasource(rrdFilename, datasourceName);
	}
}

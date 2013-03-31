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

package be.e_contract.jwatchdog;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog._1.JwatchdogConfigType;
import be.e_contract.jwatchdog._1.ObjectFactory;

public class Config {

	private static final Log LOG = LogFactory.getLog(Config.class);

	private final URL configUrl;
	private final Unmarshaller unmarshaller;

	private JwatchdogConfigType jwatchdogConfig;

	public Config(URL configUrl) throws Exception {
		LOG.debug("constructor");
		if (null == configUrl) {
			throw new IllegalArgumentException("missing configUrl parameter");
		}
		this.configUrl = configUrl;

		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		this.unmarshaller = jaxbContext.createUnmarshaller();
	}

	public void reload() throws Exception {
		LOG.debug("reload");
		Object result;
		try {
			result = this.unmarshaller.unmarshal(this.configUrl.openStream());
		} catch (UnmarshalException e) {
			LOG.error("JAXB error: " + e.getMessage());
			return;
		}
		LOG.debug("result type: " + result.getClass().getName());
		JAXBElement<JwatchdogConfigType> jaxbElement = (JAXBElement<JwatchdogConfigType>) result;
		this.jwatchdogConfig = jaxbElement.getValue();
	}

	public JwatchdogConfigType getConfig() {
		return this.jwatchdogConfig;
	}
}

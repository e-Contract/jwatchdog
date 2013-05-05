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

import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import be.e_contract.jwatchdog.Config;
import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.NotifierProvider;
import be.e_contract.jwatchdog.notifier.script.jaxb.config.ObjectFactory;
import be.e_contract.jwatchdog.notifier.script.jaxb.config.ScriptType;

public class ScriptNotifierProvider implements NotifierProvider {

	private static final Log LOG = LogFactory
			.getLog(ScriptNotifierProvider.class);

	private final Unmarshaller unmarshaller;

	public ScriptNotifierProvider() {
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ObjectFactory.class);
			this.unmarshaller = jaxbContext.createUnmarshaller();
			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputStream schemaInputStream = Config.class
					.getResourceAsStream("/jwatchdog-notifier-script-config.xsd");
			Source schemaSource = new StreamSource(schemaInputStream);
			Schema schema = schemaFactory.newSchema(schemaSource);
			this.unmarshaller.setSchema(schema);
		} catch (JAXBException e) {
			LOG.error("JAXB error: " + e.getMessage(), e);
			throw new RuntimeException("JAXB error: " + e.getMessage());
		} catch (SAXException e) {
			LOG.debug("SAX error: " + e.getMessage(), e);
			throw new RuntimeException("SAX error: " + e.getMessage());
		}
	}

	@Override
	public Set<String> getConfigNamespaces() {
		return Collections
				.singleton("urn:be:e-contract:jwatchdog:notifier:script:1.0");
	}

	@Override
	public Notifier loadNotifier(Element configElement) throws Exception {
		JAXBElement<ScriptType> scriptElement = (JAXBElement<ScriptType>) this.unmarshaller
				.unmarshal(new DOMSource(configElement));
		ScriptType scriptConfig = scriptElement.getValue();
		String mimeType = scriptConfig.getType();
		String script = scriptConfig.getValue();
		return new ScriptNotifier(mimeType, script);
	}
}

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

package be.e_contract.jwatchdog.notifier;

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

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import be.e_contract.jwatchdog.Config;

public abstract class AbstractNotifierProvider<T> implements NotifierProvider {

	protected final Unmarshaller unmarshaller;

	private final String configNamespace;

	public AbstractNotifierProvider(String configNamespace,
			Class<?> objectFactoryClass, String xmlSchemaResourceName) {
		this.configNamespace = configNamespace;
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(objectFactoryClass);
			this.unmarshaller = jaxbContext.createUnmarshaller();

			SchemaFactory schemaFactory = SchemaFactory
					.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			InputStream schemaInputStream = Config.class
					.getResourceAsStream(xmlSchemaResourceName);
			Source schemaSource = new StreamSource(schemaInputStream);
			Schema schema = schemaFactory.newSchema(schemaSource);
			this.unmarshaller.setSchema(schema);
		} catch (JAXBException e) {
			throw new RuntimeException("JAXB error: " + e.getMessage());
		} catch (SAXException e) {
			throw new RuntimeException("SAX error: " + e.getMessage());
		}
	}

	@Override
	public Set<String> getConfigNamespaces() {
		return Collections.singleton(this.configNamespace);
	}

	@Override
	public Notifier loadNotifier(Element configElement) throws Exception {
		JAXBElement<T> jaxbElement = (JAXBElement<T>) this.unmarshaller
				.unmarshal(new DOMSource(configElement));
		T config = jaxbElement.getValue();
		return loadNotifier(config);
	}

	protected abstract Notifier loadNotifier(T config) throws Exception;
}

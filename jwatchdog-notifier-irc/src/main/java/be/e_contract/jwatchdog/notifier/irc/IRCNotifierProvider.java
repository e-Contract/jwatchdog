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

package be.e_contract.jwatchdog.notifier.irc;

import java.util.Collections;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Element;

import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.NotifierProvider;
import be.e_contract.jwatchdog.notifier.irc.jaxb.config.IrcType;
import be.e_contract.jwatchdog.notifier.irc.jaxb.config.ObjectFactory;

public class IRCNotifierProvider implements NotifierProvider {

	private final Unmarshaller unmarshaller;

	public IRCNotifierProvider() {
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
				.singleton("urn:be:e-contract:jwatchdog:notifier:irc:1.0");
	}

	@Override
	public Notifier loadNotifier(Element configElement) throws Exception {
		JAXBElement<IrcType> ircConfigElement = (JAXBElement<IrcType>) this.unmarshaller
				.unmarshal(new DOMSource(configElement));
		IrcType ircConfig = ircConfigElement.getValue();
		String server = ircConfig.getServer();
		String name = ircConfig.getName();
		String channel = ircConfig.getChannel();
		return new IRCNotifier(server, name, channel);
	}

}

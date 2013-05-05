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

package be.e_contract.jwatchdog.notifier.spryng;

import be.e_contract.jwatchdog.notifier.AbstractNotifierProvider;
import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.spryng.jaxb.config.ObjectFactory;
import be.e_contract.jwatchdog.notifier.spryng.jaxb.config.SpryngType;

public class SpryngNotifierProvider extends
		AbstractNotifierProvider<SpryngType> {

	public SpryngNotifierProvider() {
		super("urn:be:e-contract:jwatchdog:notifier:spryng:1.0",
				ObjectFactory.class, "/jwatchdog-notifier-spryng-config.xsd");
	}

	@Override
	public Notifier loadNotifier(SpryngType config) throws Exception {
		String username = config.getUsername();
		String password = config.getPassword();
		String destination = config.getDestination();
		String sender = config.getSender();
		SpryngNotifier notifier = new SpryngNotifier(username, password,
				destination, sender);
		return notifier;
	}
}

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

package be.e_contract.jwatchdog.notifier.console;

import be.e_contract.jwatchdog.notifier.AbstractNotifierProvider;
import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.console.jaxb.config.ConsoleType;
import be.e_contract.jwatchdog.notifier.console.jaxb.config.ObjectFactory;

public class ConsoleNotifierProvider extends
		AbstractNotifierProvider<ConsoleType> {

	public ConsoleNotifierProvider() {
		super("urn:be:e-contract:jwatchdog:notifier:console:1.0",
				ObjectFactory.class, "/jwatchdog-notifier-console-config.xsd");
	}

	@Override
	public Notifier loadNotifier(ConsoleType config) throws Exception {
		return new ConsoleNotifier();
	}
}

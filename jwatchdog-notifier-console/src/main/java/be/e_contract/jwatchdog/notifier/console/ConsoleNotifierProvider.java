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

import java.util.Collections;
import java.util.Set;

import org.w3c.dom.Element;

import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.NotifierProvider;

public class ConsoleNotifierProvider implements NotifierProvider {

	@Override
	public Set<String> getConfigNamespaces() {
		return Collections.singleton("be:e-contract:jwatchdog:notifier:console:1.0");
	}

	@Override
	public Notifier loadNotifier(Element configElement) throws Exception {
		return new ConsoleNotifier();
	}
}

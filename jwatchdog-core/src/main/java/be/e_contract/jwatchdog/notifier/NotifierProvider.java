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

import java.util.Set;

import org.w3c.dom.Element;

/**
 * The interface for notifier providers. Register your notifier provider
 * implementation via
 * <code>META-INF/services/be.e_contract.jwatchdog.notifier.NotifierProvider</code>
 * 
 * @author Frank Cornelis
 * 
 */
public interface NotifierProvider {

	/**
	 * Returns the XML configuration namespaces that this notifier provider is
	 * capable of handling.
	 * 
	 * @return
	 */
	Set<String> getConfigNamespaces();

	/**
	 * Loads a notifier instance from the given DOM configuration element.
	 * 
	 * @param configElement
	 * @return
	 * @throws Exception
	 */
	Notifier loadNotifier(Element configElement) throws Exception;
}

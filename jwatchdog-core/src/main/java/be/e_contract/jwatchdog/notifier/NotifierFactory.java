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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.jaxb.config.NotifierGroupElement;
import be.e_contract.jwatchdog.jaxb.config.NotifierGroupType;
import be.e_contract.jwatchdog.jaxb.config.NotifierType;

/**
 * The factory for notifiers.
 * 
 * @author Frank Cornelis
 * 
 */
public class NotifierFactory {

	private static final Log LOG = LogFactory.getLog(NotifierFactory.class);

	private final List<NotifierType> notifierConfigs;

	private final List<NotifierGroupType> notifierGroupConfigs;

	private static ServiceLoader<NotifierProvider> notifierProviderLoader = ServiceLoader
			.load(NotifierProvider.class);

	public NotifierFactory(List<NotifierType> notifierConfigs,
			List<NotifierGroupType> notifierGroupConfigs) {
		this.notifierConfigs = notifierConfigs;
		this.notifierGroupConfigs = notifierGroupConfigs;
	}

	public Map<String, Set<Notifier>> loadNotifiers(Context context) {
		Map<String, Set<Notifier>> notifiers = new HashMap<String, Set<Notifier>>();
		for (NotifierType notifierConfig : this.notifierConfigs) {
			String name = notifierConfig.getName();
			LOG.debug("loading notifier: " + name);
			Element configElement = (Element) notifierConfig.getAny();
			String configNamespace = configElement.getNamespaceURI();
			NotifierProvider notifierProvider = findNotifierProvider(configNamespace);
			if (null == notifierProvider) {
				LOG.error("unsupported notifier config namespace: "
						+ configNamespace);
				continue;
			}
			try {
				Notifier notifier = notifierProvider
						.loadNotifier(configElement);
				notifier.init(context);
				notifiers.put(name, Collections.singleton(notifier));
			} catch (Exception e) {
				LOG.error("error loading notifier: " + e.getMessage());
				Throwable cause = e.getCause();
				if (null != cause) {
					LOG.error("cause: " + cause.getMessage());
				}
			}
		}
		for (NotifierGroupType notifierGroup : this.notifierGroupConfigs) {
			String name = notifierGroup.getName();
			Set<Notifier> group = new HashSet<Notifier>();
			for (NotifierGroupElement notifierGroupElement : notifierGroup
					.getNotifier()) {
				Set<Notifier> notifierElement = notifiers
						.get(notifierGroupElement.getName());
				group.addAll(notifierElement);
			}
			notifiers.put(name, group);
		}
		return notifiers;
	}

	private NotifierProvider findNotifierProvider(String configNamespace) {
		for (NotifierProvider notifierProvider : notifierProviderLoader) {
			if (notifierProvider.getConfigNamespaces()
					.contains(configNamespace)) {
				return notifierProvider;
			}
		}
		return null;
	}
}

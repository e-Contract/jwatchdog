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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.e_contract.jwatchdog.jaxb.config.ProxiesType;
import be.e_contract.jwatchdog.jaxb.config.ProxyType;
import be.e_contract.jwatchdog.jaxb.config.SettingsType;

public class WatchdogContext implements Context {

	private final Map<String, ProxyConfig> proxyConfig;

	public WatchdogContext(SettingsType settingsConfig) {
		this.proxyConfig = new HashMap<String, ProxyConfig>();
		if (null == settingsConfig) {
			return;
		}
		ProxiesType jaxbProxiesConfig = settingsConfig.getProxies();
		List<ProxyType> jaxbProxyConfigList = jaxbProxiesConfig.getProxy();
		for (ProxyType jaxbProxyConfig : jaxbProxyConfigList) {
			String protocol = jaxbProxyConfig.getProtocol();
			String host = jaxbProxyConfig.getHost();
			int port = jaxbProxyConfig.getPort();
			this.proxyConfig.put(protocol,
					new ProxyConfig(protocol, host, port));
		}
	}

	@Override
	public ProxyConfig getProxyConfig(String protocol) {
		return this.proxyConfig.get(protocol);
	}
}

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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {

	private final String version;

	public Version() {
		Properties properties = new Properties();
		InputStream inputStream = Version.class
				.getResourceAsStream("application.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			this.version = "unknown";
			return;
		}
		this.version = properties.getProperty("version");
	}

	public String getVersion() {
		return this.version;
	}
}

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

import java.io.File;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

public class Main {

	private static final Log LOG = LogFactory.getLog(Main.class);

	public static void main(String[] args) throws Exception {
		Version version = new Version();

		if (null == args) {
			printUsage(version);
			return;
		}
		if (args.length < 1) {
			printUsage(version);
			return;
		}

		if (args.length > 1) {
			String logConfigFile = args[1];
			DOMConfigurator.configure(logConfigFile);
		} else {
			URL configUrl = Main.class.getResource("/default-log4j.xml");
			if (null == configUrl) {
				throw new RuntimeException("/default-log4j.xml not found");
			}
			DOMConfigurator.configure(configUrl);
		}

		LOG.debug("jWatchdog " + version.getVersion());
		File configFile = new File(args[0]);
		if (!configFile.exists()) {
			LOG.error("config file not found: " + configFile);
			return;
		}

		Watchdog watchdog = new Watchdog(configFile);
		long sleep = 0;
		do {
			Thread.sleep(sleep);
			sleep = watchdog.guard();
		} while (sleep != -1);
	}

	private static void printUsage(Version version) {
		System.out.println("jWatchdog " + version.getVersion());
		System.out.println("Usage: java -jar jwatchdog-cli-"
				+ version.getVersion()
				+ ".jar jwatchdog-config.xml [log4j.xml]");
	}
}

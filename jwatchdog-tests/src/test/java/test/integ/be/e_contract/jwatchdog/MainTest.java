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

package test.integ.be.e_contract.jwatchdog;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import be.e_contract.jwatchdog.Main;

public class MainTest {

	private static final Log LOG = LogFactory.getLog(MainTest.class);

	@Test
	public void testNoArguments() throws Exception {
		Main.main(null);
	}

	@Test
	public void testWatchdog() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-test.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testWatchdogSkipTrigger() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-skip.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testWatchdogProxy() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-proxy.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testExpression() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-expression.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testUnknownDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-unknown-datasource.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testUnknownNotifier() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-unknown-notifier.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testInvalidConfig() throws Exception {
		URL configUrl = MainTest.class.getResource("/log4j.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testJavascript() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-javascript.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testJavascriptAverage() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-javascript-average.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testNotifierGroup() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-group.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testScriptNotifier() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testScriptNotifierRuby() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-ruby.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testScriptNotifierGroovy() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-groovy.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testScriptNotifierPython() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-python.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testNotifierIRC() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-irc.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testScriptDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-script.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testRRDDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-rrd.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testGraphiteDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-graphite.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testGraphiteDatasourceInvalidTarget() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-datasource-graphite-invalid-target.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testNotifierSpryng() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-spryng.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}

	@Test
	public void testNotifierFile() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-file.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		Main.main(new String[] { path });
		LOG.debug("end");
	}
}

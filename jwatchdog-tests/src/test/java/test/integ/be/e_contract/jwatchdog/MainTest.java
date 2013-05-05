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
		run(configUrl);
	}

	@Test
	public void testHelloWorld() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-helloworld.xml");
		run(configUrl);
	}

	private void run(URL configUrl) throws Exception {
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		LOG.debug("path: " + path);
		URL logUrl = MainTest.class.getResource("/log4j.xml");
		String logPath = logUrl.toURI().getPath();
		Main.main(new String[] { path, logPath });
		LOG.debug("end");
	}

	@Test
	public void testHeartbeat() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-heartbeat.xml");
		run(configUrl);
	}

	@Test
	public void testJavascriptErrorLineNumber() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-javascript-error-line-number.xml");
		run(configUrl);
	}

	@Test
	public void testDatasourceScriptErrorLineNumber() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-datasource-script-error-line-number.xml");
		run(configUrl);
	}

	@Test
	public void testWatchdogSkipTrigger() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-skip.xml");
		run(configUrl);
	}

	@Test
	public void testWatchdogProxy() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-proxy.xml");
		run(configUrl);
	}

	@Test
	public void testExpression() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-expression.xml");
		run(configUrl);
	}

	@Test
	public void testUnknownDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-unknown-datasource.xml");
		run(configUrl);
	}

	@Test
	public void testUnknownNotifier() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-unknown-notifier.xml");
		run(configUrl);
	}

	@Test
	public void testInvalidConfig() throws Exception {
		URL configUrl = MainTest.class.getResource("/log4j.xml");
		run(configUrl);
	}

	@Test
	public void testJavascript() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-javascript.xml");
		run(configUrl);
	}

	@Test
	public void testLogConfiguration() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-javascript.xml");
		LOG.debug("config URL: " + configUrl);
		String path = configUrl.toURI().getPath();
		URL logConfigUrl = MainTest.class.getResource("/test-log4j.xml");
		String logConfigPath = logConfigUrl.toURI().getPath();
		LOG.debug("path: " + path);
		LOG.debug("log config path: " + logConfigPath);
		Main.main(new String[] { path, logConfigPath });
		LOG.debug("end");
	}

	@Test
	public void testJavascriptAverage() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-javascript-average.xml");
		run(configUrl);
	}

	@Test
	public void testNotifierGroup() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-group.xml");
		run(configUrl);
	}

	@Test
	public void testScriptNotifier() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script.xml");
		run(configUrl);
	}

	@Test
	public void testScriptNotifierRuby() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-ruby.xml");
		run(configUrl);
	}

	@Test
	public void testScriptNotifierGroovy() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-groovy.xml");
		run(configUrl);
	}

	@Test
	public void testScriptNotifierPython() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-script-python.xml");
		run(configUrl);
	}

	@Test
	public void testNotifierIRC() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-irc.xml");
		run(configUrl);
	}

	@Test
	public void testScriptDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-script.xml");
		run(configUrl);
	}

	@Test
	public void testSchemaViolation() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-schema-violation.xml");
		run(configUrl);
	}

	@Test
	public void testRRDDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-rrd.xml");
		run(configUrl);
	}

	@Test
	public void testGraphiteDatasource() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-datasource-graphite.xml");
		run(configUrl);
	}

	@Test
	public void testGraphiteDatasourceInvalidTarget() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/test-jwatchdog-config-datasource-graphite-invalid-target.xml");
		run(configUrl);
	}

	@Test
	public void testNotifierSpryng() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-spryng.xml");
		run(configUrl);
	}

	@Test
	public void testNotifierFile() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-file.xml");
		run(configUrl);
	}

	@Test
	public void testNotifierDesktop() throws Exception {
		URL configUrl = MainTest.class
				.getResource("/jwatchdog-config-notifier-desktop.xml");
		run(configUrl);
	}
}

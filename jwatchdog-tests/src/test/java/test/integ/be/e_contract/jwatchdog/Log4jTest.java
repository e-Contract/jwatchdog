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

import static org.junit.Assert.assertNotNull;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;

public class Log4jTest {

	private static final Log LOG = LogFactory.getLog(Log4jTest.class);

	@Test
	public void testRuntimeReconfiguration() throws Exception {
		LOG.debug("default log configuration");
		URL log4jConfig = Log4jTest.class.getResource("/test-log4j.xml");
		assertNotNull(log4jConfig);

		DOMConfigurator.configure(log4jConfig);
		LOG.debug("logging after reconfiguration");
	}
}

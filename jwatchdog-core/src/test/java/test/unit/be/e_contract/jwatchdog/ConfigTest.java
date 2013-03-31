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

package test.unit.be.e_contract.jwatchdog;

import be.e_contract.jwatchdog.Config;
import java.net.URL;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertNotNull;

public class ConfigTest {

	@Test
	public void testMissingParameter() throws Exception {
		// operate & verify
		try {
			new Config(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected
		}
	}

	@Test
	public void testLoadEmptyConfig() throws Exception {
		// setup
		URL testConfig = ConfigTest.class
				.getResource("/jwatchdog-config-test.xml");
		assertNotNull(testConfig);

		// operate
		Config config = new Config(testConfig);

		// verify
	}
}

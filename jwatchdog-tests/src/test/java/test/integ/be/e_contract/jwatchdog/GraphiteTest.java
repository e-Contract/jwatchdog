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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import be.e_contract.jwatchdog.datasource.graphite.GraphiteDatasource;

public class GraphiteTest {

	private static final Log LOG = LogFactory.getLog(GraphiteTest.class);

	@Test
	public void testGraphiteREST() throws Exception {
		GraphiteDatasource graphiteDatasource = new GraphiteDatasource(
				"http://localhost:8080/render", "system.loadavg_1min", null);

		graphiteDatasource.init(new WatchdogTestContext());
		double[] result = graphiteDatasource.getValues(10);
		assertNotNull(result);

		for (double value : result) {
			LOG.debug("value: " + value);
		}
	}

	@Test
	public void testHighLoads() throws Exception {
		int count = 10000;
		while (count > 0) {
			count--;
			GraphiteDatasource graphiteDatasource = new GraphiteDatasource(
					"http://localhost:8080/render", "system.loadavg_1min", null);

			graphiteDatasource.init(new WatchdogTestContext());
			double[] result = graphiteDatasource.getValues(10);
			assertNotNull(result);
		}
	}
}

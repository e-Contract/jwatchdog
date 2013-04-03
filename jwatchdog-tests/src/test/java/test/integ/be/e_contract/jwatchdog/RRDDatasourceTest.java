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

import be.e_contract.jwatchdog.datasource.rrd.RRDDatasource;

public class RRDDatasourceTest {

	private static final Log LOG = LogFactory.getLog(RRDDatasourceTest.class);

	@Test
	public void testGetValues() throws Exception {
		RRDDatasource rrdDatasource = new RRDDatasource(
				"/opt/collectd/var/lib/collectd/rrd/e-contract.be/load/load.rrd",
				"shortterm");
		rrdDatasource.init(new WatchdogTestContext());
		double[] values = rrdDatasource.getValues(5);
		assertNotNull(values);
		for (double value : values) {
			LOG.debug("value: " + value);
		}
	}
}

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

package be.e_contract.jwatchdog.datasource.rrd;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.rrd4j.core.jrrd.ConsolidationFunctionType;
import org.rrd4j.core.jrrd.DataChunk;
import org.rrd4j.core.jrrd.DataSource;
import org.rrd4j.core.jrrd.Header;
import org.rrd4j.core.jrrd.RRDatabase;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.datasource.Datasource;

public class RRDDatasource implements Datasource {

	private static final Log LOG = LogFactory.getLog(RRDDatasource.class);

	private final File rrdFile;

	private final String datasourceName;

	public RRDDatasource(String rrdFilename, String datasourceName)
			throws Exception {
		this.rrdFile = new File(rrdFilename);
		this.datasourceName = datasourceName;
	}

	@Override
	public double[] getValues(int minutes) {
		LOG.debug("RRD file: " + this.rrdFile);
		RRDatabase rrd;
		try {
			rrd = new RRDatabase(this.rrdFile);
		} catch (IOException e) {
			throw new RuntimeException("RRD IO error: " + e.getMessage());
		}
		try {

			DateTime lastUpdate = new DateTime(rrd.getLastUpdate());
			LOG.debug("last update: " + lastUpdate);
			DateTime now = new DateTime();
			if (lastUpdate.isBefore(now.minusMinutes(minutes))) {
				LOG.warn("RRD outdated");
			}

			Header header = rrd.getHeader();
			int primaryDataPointInterval = header.getPDPStep();
			DateTime endDateTime = lastUpdate
					.minusSeconds(primaryDataPointInterval);
			DateTime startDateTime = endDateTime.minusMinutes(minutes);
			DataChunk dataChunk;
			try {
				dataChunk = rrd.getData(ConsolidationFunctionType.AVERAGE,
						startDateTime.toDate(), endDateTime.toDate(),
						primaryDataPointInterval);
			} catch (IOException e) {
				throw new RuntimeException("RRD IO error: " + e.getMessage());
			}
			double[][] data = dataChunk.getData();
			Set<String> dataSourceNames = rrd.getDataSourcesName();
			LOG.debug("RRD datasources: " + dataSourceNames);

			int dsIdx;
			if (null != this.datasourceName) {
				if (!dataSourceNames.contains(this.datasourceName)) {
					LOG.warn("RRD datasource name not found: "
							+ this.datasourceName);
					return new double[] {};
				}
				int size = dataSourceNames.size();
				for (dsIdx = 0; dsIdx < size; dsIdx++) {
					DataSource dataSource = rrd.getDataSource(dsIdx);
					if (dataSource.getName().equals(this.datasourceName)) {
						break;
					}
				}
			} else {
				dsIdx = 0;
			}

			double[] values = new double[data.length];
			for (int idx = 0; idx < data.length; idx++) {
				values[data.length - idx - 1] = data[idx][dsIdx];
			}
			return values;
		} finally {
			try {
				rrd.close();
			} catch (IOException e) {
				LOG.error("error closing RRD: " + e.getMessage());
			}
		}
	}

	@Override
	public void init(Context context) {
		// empty
	}
}

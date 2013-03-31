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

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.junit.Test;
import org.rrd4j.core.jrrd.ConsolidationFunctionType;
import org.rrd4j.core.jrrd.DataChunk;
import org.rrd4j.core.jrrd.DataSource;
import org.rrd4j.core.jrrd.Header;
import org.rrd4j.core.jrrd.RRDatabase;

public class RRDTest {
   
    private static final Log LOG = LogFactory.getLog(RRDTest.class);
    
    @Test
    public void testLoadRRD() throws Exception {
        File rrdFile = new File("/opt/collectd/var/lib/collectd/rrd/e-contract.be/load/load.rrd");
        RRDatabase rrd = new RRDatabase(rrdFile);
        Date lastUpdate = rrd.getLastUpdate();
        LOG.debug("last update: " + lastUpdate);
        LOG.debug("num archives: " + rrd.getNumArchives());
        Header header = rrd.getHeader();
        int primaryDataPointInterval = header.getPDPStep();
        LOG.debug("primary data point interval: " + primaryDataPointInterval + " seconds");
        Set<String> dataSourceNames = rrd.getDataSourcesName();
        for (String dataSourceName : dataSourceNames) {
            LOG.debug("data source: " + dataSourceName);
        }
        Iterator<DataSource> dataSourceIterator = rrd.getDataSources();
        while (dataSourceIterator.hasNext()) {
            DataSource dataSource = dataSourceIterator.next();
            LOG.debug("data source: " + dataSource.getName());
            LOG.debug("type: " + dataSource.getType());
            LOG.debug("value: " + dataSource.getPDPStatusBlock().getValue());
        }
        DateTime endDateTime = new DateTime(lastUpdate).minusSeconds(primaryDataPointInterval);
        DateTime startDateTime = endDateTime.minusMinutes(10);
        DataChunk dataChunk = rrd.getData(ConsolidationFunctionType.AVERAGE, startDateTime.toDate(), endDateTime.toDate(), primaryDataPointInterval);
        double[][] data = dataChunk.getData();
        LOG.debug("data size: " + data.length);
        for (double[] dataEntry : data) {
            LOG.debug("data entry size: " + dataEntry.length);
            for (double dataValue : dataEntry) {
                LOG.debug("value: " + dataValue);
            }
        }
        rrd.close();
    }
}

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

import org.junit.Test;

import com.google.gson.Gson;

public class GSonTest {

	@Test
	public void testGSON() throws Exception {
		Gson gson = new Gson();
		gson.fromJson("1", Integer.class);
		gson.fromJson("{ \"target\" : \"test\"}", DataType.class);
		gson.fromJson("[{ \"target\" : \"test\"}]", DataType[].class);
		gson.fromJson("[{ \"target\" : \"test\", \"datapoints\" : \"test\"}]",
				DataType2[].class);
		gson.fromJson(
				"[{ \"target\" : \"test\", \"datapoints\" : [[1.0, 2.0], [3.0, 4.0]]}]",
				DataType3[].class);
	}

	public static class DataType {
		public String target;
	}

	public static class DataType2 {
		public String target;
		public String datapoints;
	}

	public static class DataType3 {
		public String target;
		public double[][] datapoints;
	}
}

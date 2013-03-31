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

package be.e_contract.jwatchdog.datasource;

/**
 * Interface for a datasource. A datasource provides values over a certain
 * interval in the past.
 * 
 * @author Frank Cornelis
 * 
 */
public interface Datasource {

	/**
	 * Gives back the values. The first entry in the array is the most recent
	 * data point. The last entry in the array is the oldest data point for the
	 * requested interval.
	 * 
	 * @param minutes
	 *            the number of minutes to go back in time.
	 * @return
	 */
	double[] getValues(int minutes);
}

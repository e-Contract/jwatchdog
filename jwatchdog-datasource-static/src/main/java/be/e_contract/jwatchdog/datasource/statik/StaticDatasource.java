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

package be.e_contract.jwatchdog.datasource.statik;

import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.statik.jaxb.config.StaticType;

public class StaticDatasource implements Datasource {

	private final double[] values;

	public StaticDatasource(StaticType staticConfig) {
		int size = staticConfig.getValue().size();
		this.values = new double[size];
		for (int idx = 0; idx < size; idx++) {
			this.values[idx] = staticConfig.getValue().get(idx);
		}
	}

	@Override
	public double[] getValues(int minutes) {
		return this.values;
	}
}

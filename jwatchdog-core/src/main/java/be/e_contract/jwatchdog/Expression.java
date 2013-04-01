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

package be.e_contract.jwatchdog;

public class Expression {

	public double eval(String value) {
		double factor = 1.0;
		if (value.toLowerCase().endsWith("kib")) {
			value = value.substring(0, value.toLowerCase().indexOf("kib"))
					.trim();
			factor = 1024;
		}
		if (value.toLowerCase().endsWith("mib")) {
			value = value.substring(0, value.toLowerCase().indexOf("mib"))
					.trim();
			factor = 1024 * 1024;
		}
		if (value.toLowerCase().endsWith("gib")) {
			value = value.substring(0, value.toLowerCase().indexOf("gib"))
					.trim();
			factor = 1024 * 1024 * 1024;
		}
		return factor * Double.parseDouble(value);
	}
}

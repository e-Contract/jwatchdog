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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import be.e_contract.jwatchdog.Expression;

public class ExpressionTest {

	private Expression testedInstance;

	@Before
	public void setUp() throws Exception {
		this.testedInstance = new Expression();
	}

	@Test
	public void testExpressions() throws Exception {
		assertEquals(10.0d, this.testedInstance.eval("10"), 0);
		assertEquals(5.1 * 1024, this.testedInstance.eval("5.1 KiB"), 0);
		assertEquals(5.2 * 1024, this.testedInstance.eval("5.2kib"), 0);
		assertEquals(6.3 * 1024 * 1024, this.testedInstance.eval("6.3 MiB"), 0);
		assertEquals(7.4 * 1024 * 1024, this.testedInstance.eval("7.4mib"), 0);
		assertEquals(9.6d * 1024 * 1024 * 1024,
				this.testedInstance.eval("9.6 GiB"), 0);
		assertEquals(8.5d * 1024 * 1024 * 1024,
				this.testedInstance.eval("8.5gib"), 0);
	}
}

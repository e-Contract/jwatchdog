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

import be.e_contract.jwatchdog.notifier.mail.MailNotifier;

public class MailNotifierTest {

	@Test
	public void testMail() throws Exception {
		MailNotifier mailNotifier = new MailNotifier("smtp.telenet.be",
				"info@e-contract.be", "info@e-contract.be", 2);

		mailNotifier.notify("hello world");
	}
}

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

import be.e_contract.jwatchdog.notifier.twilio.TwilioNotifier;

public class TwilioNotifierTest {

	@Test
	public void testMessage() throws Exception {
		TwilioNotifier twilioNotifier = new TwilioNotifier("1234", "5678",
				"+32478299492", "+32478299492");

		twilioNotifier.notify("hello world");
	}
}

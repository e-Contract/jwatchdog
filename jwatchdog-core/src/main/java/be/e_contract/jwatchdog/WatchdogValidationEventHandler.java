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

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WatchdogValidationEventHandler implements ValidationEventHandler {

	private static final Log LOG = LogFactory
			.getLog(WatchdogValidationEventHandler.class);

	@Override
	public boolean handleEvent(ValidationEvent event) {
		LOG.warn("severity: " + event.getSeverity());
		LOG.warn("message: " + event.getMessage());
		LOG.warn("linked exception: " + event.getLinkedException());
		ValidationEventLocator locator = event.getLocator();
		LOG.warn("line number: " + locator.getLineNumber());
		LOG.warn("column number: " + locator.getColumnNumber());
		LOG.warn("offset: " + locator.getOffset());
		LOG.warn("node: " + locator.getNode());
		LOG.warn("url: " + locator.getURL());
		return false;
	}
}

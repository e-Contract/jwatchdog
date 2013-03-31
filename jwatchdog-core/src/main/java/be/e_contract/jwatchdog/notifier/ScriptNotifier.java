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

package be.e_contract.jwatchdog.notifier;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScriptNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(ScriptNotifier.class);

	private final Set<Notifier> notifiers;

	private final String notificationPrefix;

	private final boolean allowMultipleNotifications;

	private boolean notified;

	public ScriptNotifier(Set<Notifier> notifiers, String notificationPrefix,
			boolean allowMultipleNotification) {
		this.notifiers = notifiers;
		this.notificationPrefix = notificationPrefix;
		this.allowMultipleNotifications = allowMultipleNotification;
	}

	@Override
	public void notify(String message) {
		if (false == this.allowMultipleNotifications) {
			if (this.notified) {
				LOG.warn("script is firing multiple notifications");
				return;
			}
		}
		if (null != this.notificationPrefix) {
			message = this.notificationPrefix + message;
		}
		this.notified = true;
		for (Notifier notifier : this.notifiers) {
			notifier.notify(message);
		}
	}
}

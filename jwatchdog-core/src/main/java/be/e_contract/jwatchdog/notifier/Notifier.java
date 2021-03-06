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

import be.e_contract.jwatchdog.Context;

/**
 * Interface for notifier.
 * 
 * @author Frank Cornelis
 * 
 */
public interface Notifier {

	/**
	 * Initializes the notifier.
	 * 
	 * @param context
	 *            the jWatchdog context.
	 */
	void init(Context context);

	/**
	 * Sends out a notification message.
	 * 
	 * @param message
	 *            the notification message.
	 */
	void notify(String message);
}

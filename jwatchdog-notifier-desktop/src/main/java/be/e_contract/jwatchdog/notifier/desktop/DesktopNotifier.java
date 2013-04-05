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

package be.e_contract.jwatchdog.notifier.desktop;

import javax.swing.JOptionPane;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.notifier.Notifier;

public class DesktopNotifier implements Notifier {

	private final String title;

	public DesktopNotifier(String title) {
		this.title = title;
	}

	@Override
	public void init(Context context) {
		// empty
	}

	@Override
	public void notify(String message) {
		JOptionPane.showMessageDialog(null, message, this.title,
				JOptionPane.WARNING_MESSAGE);
	}
}

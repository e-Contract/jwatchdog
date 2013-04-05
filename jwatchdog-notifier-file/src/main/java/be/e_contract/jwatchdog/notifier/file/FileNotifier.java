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

package be.e_contract.jwatchdog.notifier.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.notifier.Notifier;

public class FileNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(FileNotifier.class);

	private final File file;

	public FileNotifier(String file) {
		this.file = new File(file);
	}

	@Override
	public void init(Context context) {
		// empty
	}

	@Override
	public void notify(String message) {
		LOG.debug("notification to file: " + this.file);
		LOG.debug("message: " + message);
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(this.file, true);
		} catch (IOException e) {
			LOG.error("IO error: " + e.getMessage());
			return;
		}
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(message);
		printWriter.close();
	}
}

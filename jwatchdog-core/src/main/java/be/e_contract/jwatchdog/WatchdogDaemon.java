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

import java.io.File;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WatchdogDaemon implements Daemon, Runnable {

	private static final Log LOG = LogFactory.getLog(WatchdogDaemon.class);

	private Watchdog watchdog;

	private Thread thread;

	private boolean stop;

	public WatchdogDaemon() {
		super();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(DaemonContext context) throws DaemonInitException,
			Exception {
		String[] arguments = context.getArguments();
		File configFile = new File(arguments[0]);
		this.watchdog = new Watchdog(configFile);
		this.thread = new Thread(this);
	}

	@Override
	public void start() throws Exception {
		this.thread.start();
	}

	@Override
	public void stop() throws Exception {
		this.stop = true;
		synchronized (this.watchdog) {
			this.watchdog.notify();
		}
		this.thread.join();
	}

	@Override
	public void run() {
		do {
			long sleep = -1;
			try {
				sleep = this.watchdog.guard();
			} catch (Exception e) {
				LOG.error("watchdog error: " + e.getMessage());
			}
			if (sleep == -1) {
				return;
			}
			if (this.stop) {
				return;
			}
			synchronized (this.watchdog) {
				try {
					this.watchdog.wait(sleep);
				} catch (InterruptedException e) {
					LOG.debug("interrupted: " + e.getMessage());
				}
			}
		} while (false == this.stop);
	}
}

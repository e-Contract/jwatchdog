/*
 * Java Watchdog Project.
 * Copyright (C) 2013 Frank Cornelis.
 * Copyright (C) 2014 e-Contract.be BVBA.
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

package be.e_contract.jwatchdog.notifier.irc;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.notifier.Notifier;

public class IRCNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(IRCNotifier.class);

	private final String server;

	private final String name;

	private final String channel;

	public IRCNotifier(String server, String name, String channel) {
		this.server = server;
		this.name = name;
		this.channel = channel;
	}

	@Override
	public void init(Context context) {
		// empty
	}

	@Override
	public void notify(String message) {
		LOG.debug("IRC notification: " + message);
		NotifierBot notifierBot = new NotifierBot(message);
		Configuration<PircBotX> configuration = new Configuration.Builder<PircBotX>()
				.setName(this.name).addListener(notifierBot)
				.setServerHostname(this.server)
				.addAutoJoinChannel(this.channel).buildConfiguration();
		PircBotX pircBotX = new PircBotX(configuration);
		LOG.debug("connecting to IRC server: " + this.server);
		try {
			pircBotX.startBot();
		} catch (IOException e) {
			LOG.error("IO error: " + e.getMessage());
			return;
		} catch (IrcException e) {
			LOG.debug("IRC error: " + e.getMessage());
			return;
		}
		LOG.debug("notification done");
	}
}

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.DisconnectEvent;
import org.pircbotx.hooks.events.JoinEvent;

public class NotifierBot extends ListenerAdapter<PircBotX> {

	private static final Log LOG = LogFactory.getLog(NotifierBot.class);

	private final String message;

	public NotifierBot(String message) {
		this.message = message;
	}

	@Override
	public void onConnect(ConnectEvent<PircBotX> event) throws Exception {
		LOG.debug("connected");
	}

	@Override
	public void onDisconnect(DisconnectEvent<PircBotX> event) throws Exception {
		LOG.debug("disconnected");
		synchronized (this) {
			this.notifyAll();
		}
	}

	@Override
	public void onJoin(JoinEvent<PircBotX> event) throws Exception {
		PircBotX pircBotX = event.getBot();
		Channel channel = event.getChannel();
		LOG.debug("joined: " + channel.getName());
		LOG.debug("sending message: " + this.message);
		channel.send().message(this.message);
		pircBotX.sendIRC().quitServer();
	}
}

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

package be.e_contract.jwatchdog.notifier.twilio;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog.notifier.Notifier;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Sms;

public class TwilioNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(TwilioNotifier.class);

	private final String account;

	private final String token;

	private final String to;

	private final String from;

	public TwilioNotifier(String account, String token, String to, String from) {
		this.account = account;
		this.token = token;
		this.to = to;
		this.from = from;
	}

	@Override
	public void notify(String message) {
		LOG.debug("notify: " + message);
		TwilioRestClient twilioRestClient = new TwilioRestClient(this.account,
				this.token);
		Map<String, String> params = new HashMap<String, String>();
		params.put("Body", message);
		params.put("To", this.to);
		params.put("From", this.from);
		SmsFactory messageFactory = twilioRestClient.getAccount()
				.getSmsFactory();
		Sms sms;
		try {
			sms = messageFactory.create(params);
		} catch (TwilioRestException e) {
			LOG.error("SMS error: " + e.getMessage());
			return;
		}
		LOG.debug("SID: " + sms.getSid());
	}
}

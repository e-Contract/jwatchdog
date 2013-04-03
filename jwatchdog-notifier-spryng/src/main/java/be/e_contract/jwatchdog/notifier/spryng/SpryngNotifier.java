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

package be.e_contract.jwatchdog.notifier.spryng;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.ProxyConfig;
import be.e_contract.jwatchdog.notifier.Notifier;

public class SpryngNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(SpryngNotifier.class);

	private final String username;

	private final String password;

	private final String destination;

	private final String sender;

	private Context context;

	public SpryngNotifier(String username, String password, String destination,
			String sender) {
		this.username = username;
		this.password = password;
		this.destination = destination;
		this.sender = sender;
	}

	@Override
	public void init(Context context) {
		this.context = context;
	}

	@Override
	public void notify(String message) {
		HttpClient httpClient = new DefaultHttpClient();
		ProxyConfig proxyConfig = this.context.getProxyConfig("http");
		if (null != proxyConfig) {
			HttpHost proxyHost = new HttpHost(proxyConfig.getHost(),
					proxyConfig.getPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxyHost);
		}

		HttpPost httpPost = new HttpPost("http://www.spryng.be/send.php");

		List<NameValuePair> formParams = new LinkedList<NameValuePair>();
		formParams.add(new BasicNameValuePair("USERNAME", this.username));
		formParams.add(new BasicNameValuePair("PASSWORD", this.password));
		formParams.add(new BasicNameValuePair("DESTINATION", this.destination));
		formParams.add(new BasicNameValuePair("SENDER", this.sender));
		formParams.add(new BasicNameValuePair("BODY", message));
		formParams.add(new BasicNameValuePair("ROUTE", "ECONOMIC"));
		UrlEncodedFormEntity entity;
		try {
			entity = new UrlEncodedFormEntity(formParams, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("unsupported encoding: " + e.getMessage());
			return;
		}

		httpPost.setEntity(entity);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			LOG.error("client protocol error: " + e.getMessage());
			return;
		} catch (IOException e) {
			LOG.error("IO error: " + e.getMessage());
			return;
		}
		StatusLine statusLine = httpResponse.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		LOG.debug("status code: " + statusCode);
	}
}

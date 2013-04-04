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

package be.e_contract.jwatchdog.datasource.graphite;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.ProxyConfig;
import be.e_contract.jwatchdog.datasource.Datasource;

import com.google.gson.Gson;

public class GraphiteDatasource implements Datasource {

	private static final Log LOG = LogFactory.getLog(GraphiteDatasource.class);

	private final String url;

	private final String target;

	private Context context;

	public GraphiteDatasource(String url, String target) {
		this.url = url;
		this.target = target;
	}

	@Override
	public double[] getValues(int minutes) {
		HttpClient httpClient = new DefaultHttpClient();
		URL urlUrl;
		try {
			urlUrl = new URL(this.url);
		} catch (MalformedURLException e) {
			LOG.error("URL error: " + e.getMessage());
			return new double[] {};
		}
		String protocol = urlUrl.getProtocol();
		ProxyConfig proxyConfig = this.context.getProxyConfig(protocol);
		if (null != proxyConfig) {
			HttpHost proxyHost = new HttpHost(proxyConfig.getHost(),
					proxyConfig.getPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxyHost);
		}

		HttpGet httpGet;
		try {
			httpGet = new HttpGet(this.url + "?target="
					+ URLEncoder.encode(this.target, "UTF-8")
					+ "&format=json&from=-" + minutes + "min");
		} catch (UnsupportedEncodingException e) {
			LOG.error("URL encoding error: " + e.getMessage());
			return new double[] {};
		}

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
		} catch (ClientProtocolException e) {
			LOG.error("client protocol error: " + e.getMessage());
			return new double[] {};
		} catch (IOException e) {
			LOG.error("IO error: " + e.getMessage());
			return new double[] {};
		}
		StatusLine statusLine = httpResponse.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode != HttpURLConnection.HTTP_OK) {
			LOG.error("HTTP status code: " + statusCode);
			return new double[] {};
		}
		Header contentTypeHeader = httpResponse.getFirstHeader("Content-Type");
		if (null == contentTypeHeader) {
			LOG.error("no Content-Type header");
			return new double[] {};
		}
		if (false == "application/json".equals(contentTypeHeader.getValue())) {
			LOG.error("invalid content-type: " + contentTypeHeader.getValue());
			return new double[] {};
		}
		GraphiteResult[] results;
		try {
			Gson gson = new Gson();
			results = gson.fromJson(new InputStreamReader(httpResponse
					.getEntity().getContent()), GraphiteResult[].class);
		} catch (Exception e) {
			LOG.error("JSON parsing error: " + e.getMessage());
			return new double[] {};
		}
		GraphiteResult result = results[0];
		LOG.debug("actual target: " + result.target);
		Double[][] datapoints = result.datapoints;
		double[] values = new double[datapoints.length];
		for (int idx = 0; idx < datapoints.length; idx++) {
			Double value = datapoints[datapoints.length - idx - 1][0];
			if (null != value) {
				values[idx] = value;
			} else {
				values[idx] = 0;
			}
		}
		return values;
	}

	@Override
	public void init(Context context) {
		this.context = context;
	}
}

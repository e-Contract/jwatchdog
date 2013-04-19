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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Locator;

import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.DatasourceFactory;
import be.e_contract.jwatchdog.jaxb.config.AboveType;
import be.e_contract.jwatchdog.jaxb.config.BelowType;
import be.e_contract.jwatchdog.jaxb.config.ConditionType;
import be.e_contract.jwatchdog.jaxb.config.JwatchdogConfigType;
import be.e_contract.jwatchdog.jaxb.config.ScriptType;
import be.e_contract.jwatchdog.jaxb.config.TriggerType;
import be.e_contract.jwatchdog.notifier.Notifier;
import be.e_contract.jwatchdog.notifier.NotifierFactory;
import be.e_contract.jwatchdog.notifier.ScriptNotifier;

public class Main {

	private static final Log LOG = LogFactory.getLog(Main.class);

	public static void main(String[] args) throws Exception {
		if (null == args) {
			printUsage();
			return;
		}
		if (args.length < 1) {
			printUsage();
			return;
		}
		LOG.debug("jWatchdog");
		File configFile = new File(args[0]);
		if (!configFile.exists()) {
			return;
		}

		LOG.debug("java version: " + System.getProperty("java.version"));
		LOG.debug("OS name: " + System.getProperty("os.name"));
		LOG.debug("OS arch: " + System.getProperty("os.arch"));
		LOG.debug("OS version: " + System.getProperty("os.version"));
		LOG.debug("supported scripting languages:");
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		List<ScriptEngineFactory> scriptEngineFactories = scriptEngineManager
				.getEngineFactories();
		for (ScriptEngineFactory scriptEngineFactory : scriptEngineFactories) {
			LOG.debug("\tscript language: "
					+ scriptEngineFactory.getLanguageName());
			List<String> mimetypes = scriptEngineFactory.getMimeTypes();
			LOG.debug("\tmimetypes: " + mimetypes);
			LOG.debug("\tscript language version: "
					+ scriptEngineFactory.getLanguageVersion());
			LOG.debug("\tengine: " + scriptEngineFactory.getEngineName());
			LOG.debug("\tengine version: "
					+ scriptEngineFactory.getEngineVersion());
		}

		Config config = new Config(configFile.toURI().toURL());
		boolean loop = true;
		while (loop) {
			config.reload();
			JwatchdogConfigType watchdogConfig = config.getConfig();
			Integer sleepMinutes;
			if (null != watchdogConfig) {
				sleepMinutes = watchdogConfig.getSleep();
				Context context = new WatchdogContext(
						watchdogConfig.getSettings());
				if (false == watchdogConfig.isLoop()) {
					loop = false;
				}
				DatasourceFactory datasourceFactory = new DatasourceFactory(
						watchdogConfig.getDatasource());
				Map<String, Datasource> datasources = datasourceFactory
						.loadDatasources(context);
				NotifierFactory notifierFactory = new NotifierFactory(
						watchdogConfig.getNotifier(),
						watchdogConfig.getNotifierGroup());
				Map<String, Set<Notifier>> notifiers = notifierFactory
						.loadNotifiers(context);
				List<TriggerType> triggerConfigs = watchdogConfig.getTrigger();
				String notificationPrefix = watchdogConfig
						.getNotificationPrefix();
				executeTriggers(sleepMinutes, datasources, notifiers,
						triggerConfigs, notificationPrefix, context);
			} else {
				sleepMinutes = 10;
			}
			if (loop) {
				LOG.debug("sleeping for " + sleepMinutes + " minutes");
				Thread.sleep(sleepMinutes * 60 * 1000);
			}
		}
	}

	private static void executeTriggers(Integer sleepMinutes,
			Map<String, Datasource> datasources,
			Map<String, Set<Notifier>> notifiers,
			List<TriggerType> triggerConfigs, String notificationPrefix,
			Context context) {
		Expression expression = new Expression();
		for (TriggerType triggerConfig : triggerConfigs) {
			LOG.debug("trigger: " + triggerConfig.getDescription());
			if (triggerConfig.isSkip()) {
				LOG.debug("skipping trigger");
				continue;
			}
			LOG.debug("\tdatasource: " + triggerConfig.getDatasource());
			LOG.debug("\tnotifier: " + triggerConfig.getNotifier());
			String datasourceName = triggerConfig.getDatasource();
			Datasource datasource = datasources.get(datasourceName);
			if (null == datasource) {
				LOG.error("unknown datasource: " + datasourceName);
				continue;
			}
			String notifierName = triggerConfig.getNotifier();
			Set<Notifier> notifierSet = notifiers.get(notifierName);
			if (null == notifierSet) {
				LOG.error("unknown notifier: " + notifierName);
				continue;
			}
			double[] values = datasource.getValues(sleepMinutes);
			ConditionType condition = triggerConfig.getCondition();
			AboveType above = condition.getAbove();
			if (null != above) {
				String triggerValue = above.getValue();
				LOG.debug("\tcondition: above " + triggerValue);
				for (double value : values) {
					LOG.debug("\tvalue: " + value);
					double evalTriggerValue = expression.eval(triggerValue);
					if (value > evalTriggerValue) {
						String message;
						if (notificationPrefix != null) {
							message = notificationPrefix
									+ triggerConfig.getDescription();
						} else {
							message = triggerConfig.getDescription();
						}
						for (Notifier notifier : notifierSet) {
							notifier.notify(message);
						}
						return; // one notification should do
					}
				}
			}
			BelowType below = condition.getBelow();
			if (null != below) {
				String triggerValue = below.getValue();
				LOG.debug("\tcondition: below " + triggerValue);
				double evalTriggerValue = expression.eval(triggerValue);
				for (double value : values) {
					LOG.debug("\tvalue: " + value);
					if (value < evalTriggerValue) {
						String message;
						if (notificationPrefix != null) {
							message = notificationPrefix
									+ triggerConfig.getDescription();
						} else {
							message = triggerConfig.getDescription();
						}
						for (Notifier notifier : notifierSet) {
							notifier.notify(message);
						}
						return; // one notification should do
					}
				}
			}
			ScriptType script = condition.getScript();
			if (null != script) {
				LOG.debug("\tcondition: script");
				ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
				String mimeType = script.getType();
				boolean allowMultipleNotifications = script
						.isAllowMultipleNotifications();
				ScriptEngine scriptEngine = scriptEngineManager
						.getEngineByMimeType(mimeType);
				if (null == scriptEngine) {
					LOG.error("unsupported script language: " + mimeType);
					continue;
				}
				String message;
				if (notificationPrefix != null) {
					message = notificationPrefix
							+ triggerConfig.getDescription();
				} else {
					message = triggerConfig.getDescription();
				}
				ScriptObject scriptObject = new ScriptObject(values,
						new ScriptNotifier(notifierSet, notificationPrefix,
								allowMultipleNotifications), message);
				scriptEngine.put("jwatchdog", scriptObject);
				try {
					scriptEngine.eval(script.getValue());
				} catch (ScriptException e) {
					LOG.error("script error: " + e.getMessage());
					Locator locator = script.sourceLocation();
					int scriptXmlLineNumber = locator.getLineNumber();
					int scriptLineNumber = e.getLineNumber();
					int lineNumber = scriptXmlLineNumber + scriptLineNumber - 1;
					LOG.error("script error line number: " + lineNumber);
				}
			}
		}
	}

	private static void printUsage() {
		System.out.println("jWatchdog");
		System.out
				.println("Usage: java -jar jwatchdog-cli-xxx.jar jwatchdog-config.xml");
	}
}

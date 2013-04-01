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
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog._1.AboveType;
import be.e_contract.jwatchdog._1.BelowType;
import be.e_contract.jwatchdog._1.ConditionType;
import be.e_contract.jwatchdog._1.JwatchdogConfigType;
import be.e_contract.jwatchdog._1.ScriptType;
import be.e_contract.jwatchdog._1.TriggerType;
import be.e_contract.jwatchdog.datasource.Datasource;
import be.e_contract.jwatchdog.datasource.DatasourceFactory;
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
		Config config = new Config(configFile.toURI().toURL());
		boolean loop = true;
		while (loop) {
			config.reload();
			JwatchdogConfigType watchdogConfig = config.getConfig();
			Integer sleepMinutes;
			if (null != watchdogConfig) {
				sleepMinutes = watchdogConfig.getSleep();
				if (false == watchdogConfig.isLoop()) {
					loop = false;
				}
				DatasourceFactory datasourceFactory = new DatasourceFactory(
						watchdogConfig.getDatasource());
				Map<String, Datasource> datasources = datasourceFactory
						.loadDatasources();
				NotifierFactory notifierFactory = new NotifierFactory(
						watchdogConfig.getNotifier(),
						watchdogConfig.getNotifierGroup());
				Map<String, Set<Notifier>> notifiers = notifierFactory
						.getNotifiers();
				List<TriggerType> triggerConfigs = watchdogConfig.getTrigger();
				String notificationPrefix = watchdogConfig
						.getNotificationPrefix();
				executeTriggers(sleepMinutes, datasources, notifiers,
						triggerConfigs, notificationPrefix);
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
			List<TriggerType> triggerConfigs, String notificationPrefix) {
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
				Double triggerValue = above.getValue();
				LOG.debug("\tcondition: above " + triggerValue);
				for (double value : values) {
					LOG.debug("\tvalue: " + value);
					if (value > triggerValue) {
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
				Double triggerValue = below.getValue();
				LOG.debug("\tcondition: below " + triggerValue);
				for (double value : values) {
					LOG.debug("\tvalue: " + value);
					if (value < triggerValue) {
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
				scriptEngine.put("values", values);
				scriptEngine.put("notifier", new ScriptNotifier(notifierSet,
						notificationPrefix, allowMultipleNotifications));
				try {
					scriptEngine.eval(script.getValue());
				} catch (ScriptException e) {
					LOG.error("script error: " + e.getMessage());
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

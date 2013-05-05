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

import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import be.e_contract.jwatchdog.jaxb.config.Heartbeat;
import be.e_contract.jwatchdog.notifier.Notifier;

public class HeartBeat {

	private static final Log LOG = LogFactory.getLog(HeartBeat.class);

	private Trigger currentTrigger;

	private String currentCron;

	private String currentMessage;

	private String currentNotifier;

	private JobDataMap jobDataMap;

	private Scheduler scheduler;

	private JobDetail jobDetail;

	public void reconfig(Heartbeat heartbeatConfig,
			Map<String, Set<Notifier>> notifiers) throws SchedulerException,
			ParseException {
		if (null == heartbeatConfig) {
			if (this.scheduler != null) {
				this.scheduler.shutdown();
				this.currentCron = null;
				this.currentTrigger = null;
				this.scheduler = null;
				this.currentMessage = null;
				this.currentNotifier = null;
				this.jobDataMap = null;
				this.jobDetail = null;
			}
			return;
		}
		String cron = heartbeatConfig.getCron();
		LOG.debug("cron: " + cron);
		String message = heartbeatConfig.getMessage();
		String notifier = heartbeatConfig.getNotifier();

		if (null == this.scheduler) {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			this.scheduler = schedulerFactory.getScheduler();

			JobBuilder jobBuilder = JobBuilder.newJob(HeartbeatJob.class);
			this.jobDetail = jobBuilder.storeDurably(true).build();

			this.jobDataMap = this.jobDetail.getJobDataMap();
			this.jobDataMap.put(HeartbeatJob.MESSAGE_JOB_DATA, message);
			Set<Notifier> notifierSet = notifiers.get(notifier);
			this.jobDataMap
					.put(HeartbeatJob.NOTIFIER_SET_JOB_DATA, notifierSet);

			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder
					.newTrigger();
			CronExpression cronExpression = new CronExpression(cron);
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
					.cronSchedule(cronExpression);
			this.currentTrigger = triggerBuilder.withSchedule(
					cronScheduleBuilder).build();
			this.currentCron = cron;
			this.currentMessage = message;
			this.currentNotifier = notifier;

			this.scheduler.scheduleJob(this.jobDetail, this.currentTrigger);

			LOG.debug("starting scheduler for cron: " + cron);
			this.scheduler.start();
		} else {
			if (false == this.currentCron.equals(cron)) {
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder
						.newTrigger();
				CronExpression cronExpression = new CronExpression(cron);
				CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
						.cronSchedule(cronExpression);
				Trigger trigger = triggerBuilder.withSchedule(
						cronScheduleBuilder).build();

				this.scheduler.rescheduleJob(this.currentTrigger.getKey(),
						trigger);
				this.currentTrigger = trigger;
				this.currentCron = cron;
			}
			if (false == this.currentMessage.equals(message)
					|| false == this.currentNotifier.equals(notifier)) {
				this.jobDataMap.put(HeartbeatJob.MESSAGE_JOB_DATA, message);
				Set<Notifier> notifierSet = notifiers.get(notifier);
				this.jobDataMap.put(HeartbeatJob.NOTIFIER_SET_JOB_DATA,
						notifierSet);
				this.scheduler.addJob(this.jobDetail, true);
			}
		}
	}
}

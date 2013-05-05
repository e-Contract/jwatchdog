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

package test.integ.be.e_contract.jwatchdog;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

	private static final Log LOG = LogFactory.getLog(QuartzTest.class);

	@Test
	public void testCronExpression() throws Exception {
		CronExpression cronExpression = new CronExpression("0/15 * * * * ?");
		Date now = new Date();
		Date next = cronExpression.getNextValidTimeAfter(now);
		LOG.debug("now: " + now);
		LOG.debug("next: " + next);
	}

	@Test
	public void testScheduler() throws Exception {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();

		JobBuilder jobBuilder = JobBuilder.newJob(MyJob.class);
		JobDetail jobDetail = jobBuilder.storeDurably(true).build();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		jobDataMap.put(MyJob.MESSAGE_JOB_DATA, "hello world");

		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		CronExpression cronExpression = new CronExpression("0/3 * * * * ?");
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
				.cronSchedule(cronExpression);
		Trigger trigger = triggerBuilder.withSchedule(cronScheduleBuilder)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);

		scheduler.start();

		Thread.sleep(1000 * 10);

		jobDataMap.put(MyJob.MESSAGE_JOB_DATA, "second message");
		scheduler.addJob(jobDetail, true);

		cronExpression = new CronExpression("0/2 * * * * ?");
		cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		triggerBuilder = TriggerBuilder.newTrigger();
		Trigger newTrigger = triggerBuilder.withSchedule(cronScheduleBuilder)
				.build();
		scheduler.rescheduleJob(trigger.getKey(), newTrigger);

		Thread.sleep(1000 * 10);
	}

	public static class MyJob implements Job {

		public static final String MESSAGE_JOB_DATA = "message";

		@Override
		public void execute(JobExecutionContext context)
				throws JobExecutionException {
			LOG.debug("fire time: " + context.getFireTime());
			JobDetail jobDetail = context.getJobDetail();
			JobDataMap jobDataMap = jobDetail.getJobDataMap();
			String message = jobDataMap.getString(MESSAGE_JOB_DATA);
			LOG.debug("message: " + message);
		}
	}
}

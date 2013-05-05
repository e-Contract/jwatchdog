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

import java.util.Date;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import be.e_contract.jwatchdog.notifier.Notifier;

public class HeartbeatJob implements Job {

	private static final Log LOG = LogFactory.getLog(HeartbeatJob.class);

	public static final String MESSAGE_JOB_DATA = "message";

	public static final String NOTIFIER_SET_JOB_DATA = "notifier-set";

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();
		String message = jobDataMap.getString(MESSAGE_JOB_DATA);
		Date fireTime = context.getFireTime();
		LOG.debug("fire time: " + fireTime);
		LOG.debug("heartbeart message: " + message);
		Set<Notifier> notifierSet = (Set<Notifier>) jobDataMap
				.get(NOTIFIER_SET_JOB_DATA);
		for (Notifier notifier : notifierSet) {
			LOG.debug("notification of: " + notifier.getClass().getSimpleName());
			notifier.notify(message);
		}
	}
}

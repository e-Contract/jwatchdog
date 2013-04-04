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

package be.e_contract.jwatchdog.notifier.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import be.e_contract.jwatchdog.Context;
import be.e_contract.jwatchdog.notifier.Notifier;

public class MailNotifier implements Notifier {

	private static final Log LOG = LogFactory.getLog(MailNotifier.class);

	private final String from;

	private final String to;

	private final String smtpServer;

	public MailNotifier(String smtpServer, String from, String to) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.to = to;
	}

	@Override
	public void init(Context context) {
		// empty
	}

	@Override
	public void notify(String message) {
		LOG.debug("sending mail to: " + this.to);
		LOG.debug("message: " + message);

		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.smtpServer);
		properties.put("mail.from", this.from);

		Session session = Session.getInstance(properties, null);
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom();
			mimeMessage.setRecipients(RecipientType.TO, this.to);
			mimeMessage.setSubject(message);
			mimeMessage.setSentDate(new Date());

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setText(message);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			mimeMessage.setContent(multipart);
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			LOG.error("messaging error: " + e.getMessage());
		}
	}
}

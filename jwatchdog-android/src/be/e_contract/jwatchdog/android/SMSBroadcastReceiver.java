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

package be.e_contract.jwatchdog.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if ("android.provider.Telephony.SMS_RECEIVED".equals(action)) {
			Log.d(Constants.TAG, "receiving SMS");
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			String prefix = sharedPreferences.getString("prefix", null);
			if (null == prefix) {
				return;
			}
			if (0 == prefix.length()) {
				return;
			}
			Bundle bundle = intent.getExtras();
			Object[] pdus = (Object[]) bundle.get("pdus");
			for (Object pdu : pdus) {
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
				String body = smsMessage.getDisplayMessageBody();
				if (body.startsWith(prefix)) {
					body = body.substring(prefix.length());
					ContentValues contentValues = new ContentValues();
					contentValues.put(DatabaseHelper.NOTIFICATIONS_MESSAGE_COL,
							body);
					DatabaseHelper databaseHelper = new DatabaseHelper(context);
					SQLiteDatabase database = databaseHelper
							.getWritableDatabase();
					try {
						database.insertOrThrow(
								DatabaseHelper.NOTIFICATIONS_TABLE, null,
								contentValues);
					} finally {
						database.close();
					}
					AlarmManager alarmManager = (AlarmManager) context
							.getSystemService(Context.ALARM_SERVICE);
					Intent alarmIntent = new Intent(context, MainActivity.class);
					PendingIntent pendingIntent = PendingIntent.getActivity(
							context, 0, alarmIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
					alarmManager.set(AlarmManager.RTC_WAKEUP,
							System.currentTimeMillis(), pendingIntent);
				}
			}
		}
	}
}

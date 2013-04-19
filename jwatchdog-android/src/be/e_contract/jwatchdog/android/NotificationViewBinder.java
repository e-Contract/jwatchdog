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

import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class NotificationViewBinder implements ViewBinder {

	private final Context context;

	private final DateFormat timeFormat;

	private final DateFormat dateFormat;

	public NotificationViewBinder(Context context) {
		this.context = context;
		this.timeFormat = android.text.format.DateFormat
				.getTimeFormat(this.context);
		this.dateFormat = android.text.format.DateFormat
				.getDateFormat(this.context);
	}

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		if (columnIndex == cursor
				.getColumnIndex(DatabaseHelper.NOTIFICATIONS_TIMESTAMP_COL)) {
			Date date = new Date(cursor.getLong(columnIndex));
			TextView textView = (TextView) view;
			textView.setText(this.dateFormat.format(date) + " "
					+ this.timeFormat.format(date));
			return true;
		}
		return false;
	}
}

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

	public NotificationViewBinder(Context context) {
		this.context = context;
	}

	@Override
	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		if (columnIndex == cursor
				.getColumnIndex(DatabaseHelper.NOTIFICATIONS_TIMESTAMP_COL)) {
			DateFormat dateFormat = android.text.format.DateFormat
					.getTimeFormat(context);
			long date = cursor.getLong(columnIndex);
			TextView textView = (TextView) view;
			textView.setText(dateFormat.format(new Date(date)));
			return true;
		}
		return false;
	}
}

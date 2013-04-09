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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "jwatchdog.db";
	private static final int DB_VERSION = 1;
	public static final String NOTIFICATIONS_TABLE = "notifications";
	public static final String NOTIFICATIONS_ID_COL = "_id";
	public static final String NOTIFICATIONS_MESSAGE_COL = "msg";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String sql = "CREATE TABLE " + NOTIFICATIONS_TABLE + "("
				+ NOTIFICATIONS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ NOTIFICATIONS_MESSAGE_COL + " TEXT NOT NULL" + ")";
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + NOTIFICATIONS_TABLE);
		onCreate(database);
	}
}

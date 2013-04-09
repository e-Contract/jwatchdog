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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class NotificationContentProvider extends ContentProvider {

	private final static UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);

	private final static String AUTHORITY = NotificationContentProvider.class
			.getName();

	private final static String NOTIFICATIONS_PATH = "notifications";
	private final static int NOTIFICATIONS_CODE = 100;
	public final static Uri NOTIFICATIONS_URI = Uri.parse("content://"
			+ AUTHORITY + "/" + NOTIFICATIONS_PATH);

	private final static String INSERT_PATH = "insert";
	private final static int INSERT_CODE = 200;
	public final static Uri INSERT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + INSERT_PATH);

	private DatabaseHelper databaseHelper;

	static {
		URI_MATCHER.addURI(AUTHORITY, NOTIFICATIONS_PATH, NOTIFICATIONS_CODE);
		URI_MATCHER.addURI(AUTHORITY, INSERT_PATH, INSERT_CODE);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues contentValues) {
		Log.d(Constants.TAG, "insert");
		SQLiteDatabase database = this.databaseHelper.getWritableDatabase();
		try {
			database.insertOrThrow(DatabaseHelper.NOTIFICATIONS_TABLE, null,
					contentValues);
		} finally {
			database.close();
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		this.databaseHelper = new DatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.d(Constants.TAG, "query");
		int uriType = URI_MATCHER.match(uri);
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(DatabaseHelper.NOTIFICATIONS_TABLE);
		switch (uriType) {
		case NOTIFICATIONS_CODE:
			break;
		default:
			throw new IllegalArgumentException("unknown URI: " + uri);
		}
		SQLiteDatabase database = this.databaseHelper.getReadableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues contentValues, String selection,
			String[] selectionArgs) {
		return 0;
	}

}

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
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class NotificationListFragment extends ListFragment implements
		LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter simpleCursorAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(Constants.TAG, "onCreateView");
		View view = super.onCreateView(inflater, container, savedInstanceState);
		Context context = getActivity().getApplicationContext();
		this.simpleCursorAdapter = new SimpleCursorAdapter(context,
				R.layout.row, null, new String[] {
						DatabaseHelper.NOTIFICATIONS_MESSAGE_COL,
						DatabaseHelper.NOTIFICATIONS_TIMESTAMP_COL },
				new int[] { R.id.messageTextView, R.id.timestampTextView });
		this.simpleCursorAdapter.setViewBinder(new NotificationViewBinder(context));
		setListAdapter(this.simpleCursorAdapter);
		getLoaderManager().initLoader(0, null, this);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		CursorLoader cursorLoader = new CursorLoader(getActivity()
				.getApplicationContext(),
				NotificationContentProvider.NOTIFICATIONS_URI, null, null,
				null, null);
		Log.d(Constants.TAG, "onCreateLoader");
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		this.simpleCursorAdapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}
}

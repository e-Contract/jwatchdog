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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	public static final String ALARM_EXTRA = "alarm";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent intent = getIntent();
		boolean alarm = intent.getBooleanExtra(ALARM_EXTRA, false);
		if (false == alarm) {
			return;
		}

		Uri alarmUri = getAlarmUri();
		Log.d(Constants.TAG, "alarm URI: " + alarmUri);
		final MediaPlayer mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(this, alarmUri);
		} catch (Exception e) {
			Log.e(Constants.TAG,
					"media player data source error: " + e.getMessage());
			return;
		}
		AudioManager audioManager = (AudioManager) getApplicationContext()
				.getSystemService(Context.AUDIO_SERVICE);
		int volume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
		if (0 != volume) {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mediaPlayer.setLooping(true);
			try {
				mediaPlayer.prepare();
			} catch (Exception e) {
				Log.e(Constants.TAG,
						"media player prepare error: " + e.getMessage());
				return;
			}
			mediaPlayer.start();
		}

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(R.string.notification_title);
		alertDialogBuilder.setMessage(R.string.notification_message);
		alertDialogBuilder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mediaPlayer.stop();
						mediaPlayer.release();
					}
				});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	private Uri getAlarmUri() {
		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		if (null != uri) {
			return uri;
		}
		return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.action_settings:
			startActivity(new Intent(this, PrefsActivity.class));
			break;
		case R.id.action_remove_all: {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					this);
			alertDialogBuilder.setTitle(R.string.action_remove_all);
			alertDialogBuilder.setMessage(R.string.remove_all_question);
			alertDialogBuilder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							getContentResolver().delete(
									NotificationContentProvider.DELETE_ALL_URI,
									null, null);
						}
					});
			alertDialogBuilder.setNegativeButton(R.string.cancel, null);
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			break;
		}
		}
		return true;
	}
}

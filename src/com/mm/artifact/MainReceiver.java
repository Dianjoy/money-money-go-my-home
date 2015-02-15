package com.mm.artifact;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MainReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		Logger.v(action);
		if (action.equals(Intent.ACTION_SCREEN_OFF)
				|| action.equals(Intent.ACTION_SCREEN_ON)
				|| action.equals("android.intent.action.BOOT_COMPLETED")
				|| action.equals("android.intent.action.USER_PRESENT")
				|| action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
			startService(context);
		} else if (action.equals("com.happy.lock.monitor")) {

		}
	}

	/**
	 * start the custom service
	 */
	public void startService(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, LockService.class);
		context.startService(intent);
	}
}

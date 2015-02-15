package com.mm.artifact;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable r = new Runnable() {
		public void run() {
			mToast.cancel();
		}
	};

	public static void showToast(Activity activity, String content, int duration) {
		mHandler.removeCallbacks(r);
		if (mToast != null) {
			((TextView) (mToast.getView().findViewById(R.id.tv_content)))
					.setText(content);
		} else {
			mToast = new Toast(activity);
			LayoutInflater inflater = activity.getLayoutInflater();
			View v = inflater.inflate(R.layout.toast_style, null);
			((TextView) v.findViewById(R.id.tv_content)).setText(content);
			if(duration == Toast.LENGTH_LONG){
				mToast.setDuration(3000);
			}else if (duration == Toast.LENGTH_SHORT) {
				mToast.setDuration(1000);
			}else {
				mToast.setDuration(duration);
			}
			mToast.setGravity(Gravity.CENTER, 0, 0);
			mToast.setView(v);
			mToast.show();
		}
		if(duration == Toast.LENGTH_LONG){
			mHandler.postDelayed(r, 3000);
		}else if(duration == Toast.LENGTH_SHORT){
			mHandler.postDelayed(r, 1000);
		}else {
			mHandler.postDelayed(r, duration);
		}
		
		mToast.show();
	}
}

package com.mm.artifact;

import android.util.Log;

public class Logger {
	public static final String TAG = "红包神器";
	public static final boolean isDebug = false;
	public static void v(String content){
		if(isDebug == true){
			Log.v(TAG, content);
		}
	}
}

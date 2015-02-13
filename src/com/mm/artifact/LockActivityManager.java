package com.mm.artifact;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;

public class LockActivityManager {

	private static Stack<Activity> lockActivityStack;
	private static LockActivityManager instance;

	private LockActivityManager() {
	}

	public static LockActivityManager getActivityManager() {
		if (instance == null) {
			instance = new LockActivityManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (lockActivityStack == null) {
			lockActivityStack = new Stack<Activity>();
		}
		lockActivityStack.add(activity);
	}

	public Activity topActivity() {
		if(lockActivityStack == null){
			return null;
		}
		if(lockActivityStack.size() == 0){
			return null;
		}
		Activity activity = lockActivityStack.lastElement();
		return activity;
	}

	public void finishActivity() {
		Activity activity = lockActivityStack.lastElement();
		finishActivity(activity);
	}

	public void finishActivity(Activity activity) {
		if (activity != null) {
			lockActivityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	public void finishActivity(Class<?> cls) {
		for (Activity activity : lockActivityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	public void finishAllActivity() {
		for (int i = 0, size = lockActivityStack.size(); i < size; i++) {
			if (null != lockActivityStack.get(i)) {
				lockActivityStack.get(i).finish();
			}
		}
		lockActivityStack.clear();
	}

	public void AppExit(Context context) {
		try {
			finishAllActivity();
			// android.os.Process.killProcess(android.os.Process.myPid());
		} catch (Exception e) {
		}
	}
}
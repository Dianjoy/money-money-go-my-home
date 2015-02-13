package com.mm.artifact;


public class Watcher {
	private static Watcher watcher;

	private Watcher() {

	}

	public static Watcher getInstance() {
		if (watcher == null) {
			watcher = new Watcher();
		}
		return watcher;
	}

	public void createLockMonitor(String userId, int version) {
		if (!createWatcher(userId, version)) {
//			Logger.getLogger("native").e("监视进程创建失败");
		}
	}

	private native boolean createWatcher(String userId, int version);

	private native boolean connectToMonitor();

	private native int sendMsgToMonitor(String msg);

	static {
		System.loadLibrary("monitor");
	}
}

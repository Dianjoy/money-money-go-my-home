package com.mm.artifact;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class LockRemoteService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
			return mBinder;
	}

	private final ILockRemoteService.Stub mBinder = new ILockRemoteService.Stub() {
		@Override
		public void startRemoteP() throws RemoteException {
			// TODO Auto-generated method stub
			Watcher.getInstance().createLockMonitor(
					Utils.getProcessName(LockRemoteService.this),
					android.os.Build.VERSION.SDK_INT);

		}

	};
}

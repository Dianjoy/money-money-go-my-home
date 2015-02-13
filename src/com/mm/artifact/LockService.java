package com.mm.artifact;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

public class LockService extends Service {
	private Intent zdLockIntent = null;
	// private String download_url, version;
	private KeyguardManager mKeyguardManager = null;
	public static KeyguardManager.KeyguardLock mKeyguardLock = null;
	public static PowerManager.WakeLock light = null;
	PowerManager powerManager = null;
	TelephonyManager tm = null;
	private int telStatus = 0;
	private ILockRemoteService lockRemoteService = null;
	public static final String RIGHT_SCORE = "action_right_slide_success";
	public static int redPackCounts;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mKeyguardManager = (KeyguardManager) LockService.this
				.getSystemService(Context.KEYGUARD_SERVICE);
		mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
		mKeyguardLock.disableKeyguard();
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		light = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		redPackCounts = 0;
		zdLockIntent = new Intent(LockService.this, NotifyRedPackActivity.class);
		zdLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_SCREEN_ON);
		intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
		intentFilter.addAction(Intent.ACTION_USER_PRESENT);
		intentFilter.addAction("aaaaaaaaaaaaaaaaaaaa");
		registerReceiver(screenReceiver, intentFilter);
		startRemoteProcress();
		SoundUtil.getInstance(this);
	}

	private void startRemoteProcress() {
		bindService(new Intent("com.mm.artifact.ILockRemoteService"),
				serviceConnection, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			lockRemoteService = ILockRemoteService.Stub.asInterface(service);
			try {
				lockRemoteService.startRemoteP();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	public int OnStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		// startService(new Intent(this, AssistService.class));
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (screenReceiver != null) {
			this.unregisterReceiver(screenReceiver);
		}
		if (lockRemoteService != null) {
			// Log.i("native", "remote进程被主动干掉");
			unbindService(serviceConnection);
			lockRemoteService = null;
		}
		startService(new Intent(this, LockService.class));
	}
	
	private BroadcastReceiver screenReceiver  = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent intent) {
			String action = intent.getAction();
			// Log.i("on", action);
			System.out.println("收到了广播：" + action);
			if (action.equals("aaaaaaaaaaaaaaaaaaaa")) {
//				if (telStatus == 1) {
//					return;
//				}
				System.out.println("接收到了aaaaaaaa广播");
				if(Utils.getQHBState(getApplicationContext()) == false){
					System.out.println("红包开关关闭状态");
					return;
				}
				
				if(Utils.getDontDisturb(getApplicationContext()) == true){
					if(Utils.isDontDisturbTime() == true){
						System.out.println("免打扰状态");
						return;
					}
				}
				redPackCounts++;
				mKeyguardLock.disableKeyguard();
				light.acquire(1000);
				NotifyService.beginTime = System.currentTimeMillis();
				if(Utils.getVibrateState(getApplicationContext()) == true){
					Utils.vibrate(getApplicationContext());
					SoundUtil.getInstance(getApplicationContext()).playSound(SoundUtil.SOUND_TYPE_EARN_MONEY);
				}
				System.out.println("------------准备开启红包提示页面---------");
				startActivity(zdLockIntent);
			}
//			else if (action.equals(Intent.ACTION_SCREEN_ON)) {
//				mKeyguardLock.disableKeyguard();
//				startActivity(zdLockIntent);
//			}
			else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				mKeyguardLock.reenableKeyguard();
			}
		}
	};
}

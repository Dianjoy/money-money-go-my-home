package com.mm.artifact;

import java.util.List;
import java.util.Locale;

import android.R.integer;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class NotifyService extends AccessibilityService implements
		TextToSpeech.OnInitListener {
//	TextToSpeech speech;
//	boolean mTextToSpeechInitialized = false;
	public static long beginTime = 0;
	public static long endTime = 0;
	private KeyguardManager mKeyguardManager = null;
	public static KeyguardManager.KeyguardLock mKeyguardLock = null;
	
//	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		
		
		System.out.println("-------------------走了辅助辅助辅助-----------");
//		if(Utils.getQHBState(getApplicationContext()) == false){
//			return;
//		}
//		
//		if(Utils.getDontDisturb(getApplicationContext()) == true){// 是否开启免打扰
//			// 现在是免打扰时段
//			if(Utils.isDontDisturbTime() == true){// 是否是免打扰时段
//				return;
//			}
//		}
		
		if (event.getPackageName().equals(getPackageName())) {
			return;
		}
		
//		if (!mTextToSpeechInitialized) {
//			return;
//		}
		
//		System.out.println("!!!!!!!!!!!!!包名：" + event.getPackageName().toString());
		if(event.getPackageName().toString().equals("com.tencent.mm") == false){
			return;
		}
		
//		Log.i("tttt", event.getPackageName() + "|" + event.getClassName() + "|"
//				+ event.getAction());
		// processWeiXinHongBaoApplication(event);
		List<CharSequence> text = event.getText();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.size(); i++) {
			builder.append(text.get(i));
		}
//		speech.speak(builder.toString(), TextToSpeech.QUEUE_FLUSH, null);
		// XXX:呵呵
//		System.out.println("!!!!!!!!!!!!!内容："+builder.toString());
		
		if(builder.toString().contains("[微信红包]")){
			
//			mKeyguardManager = (KeyguardManager) this
//					.getSystemService(Context.KEYGUARD_SERVICE);
//			mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");
//			mKeyguardLock.disableKeyguard();
			
//			showDialog(getApplicationContext(),2);
//			Intent intent = new Intent(this,BeginActivity.class);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(intent);
			System.out.println("发送aaaaaaaaaaaa广播");
			sendBroadcast(new Intent("aaaaaaaaaaaaaaaaaaaa"));
		}
	}
	
	private void showDialog(Context context,int index) {
		DialogUtil.createAndShowDialog(getApplicationContext(), index);
	}
	
	@Override
	public void onInterrupt() {
	}

	@Override
	public void onServiceConnected() {
//		speech = new TextToSpeech(getApplicationContext(), this);
		AccessibilityServiceInfo info = new AccessibilityServiceInfo();
		info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
		info.notificationTimeout = 100;
		info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;
		setServiceInfo(info);
		Utils.setOpenAssistState(getApplicationContext());
//		showDialog(getApplicationContext(),1);
		Intent intent=new Intent(this,NoticeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.setCloseAssist(getApplicationContext());
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public void onInit(int status) {
//		if (status == TextToSpeech.SUCCESS) {
//			speech.setLanguage(Locale.US);
//			mTextToSpeechInitialized = true;
//		}
	}
}

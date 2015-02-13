package com.mm.artifact;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import com.mm.artifact.DialogUtil.BaseDialogListener;

public abstract class BaseActivity extends Activity implements OnClickListener{
	/** 初始化View */
	public abstract void initControl();

	/** ViewClick */
	public abstract void controlClick(View v);

	/** 消息处理 */
	public abstract void handleMsg(Message msg);

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			handleMsg(msg);
		};
	};
	public Handler getHandler(){
		return handler;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LockActivityManager.getActivityManager().addActivity(this);
		initControl();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onStop() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onRestart() {
		super.onRestart();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finishActivity(this);
	}

	@Override
	public void onClick(View v) {
		controlClick(v);
	}

	/** 发送消息 */
	public void sendMessage(Object obj, int what) {
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	/** 创建对话框 */
	public void showDialog(Context context,
			BaseDialogListener listener) {
		DialogUtil.createAndShowDialog(context, 1);
	};

	/** 关闭对话框 */
	public void dismissDialog() {
		DialogUtil.dismiss();	
	}
	public void showToast(Activity activity,String message,int time){
		Utils.showToast(activity, message, time);
	}
	/** Toast提示 */
	public void showToast(Activity activity, String message) {
		Utils.showToast(activity, message);
	}
	
	/**退出activity*/
	public void finishActivity(Activity activity){
		LockActivityManager.getActivityManager().finishActivity(activity);
	}
}

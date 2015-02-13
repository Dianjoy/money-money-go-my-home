package com.mm.artifact;

import com.example.weixintext.R;
import com.umeng.analytics.MobclickAgent;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置页面
 * @author dianjoy
 *
 */
public class SettingActivity extends BaseActivity {

	TextView tv_share;
	TextView tv_version;
	TextView tv_back;
	
	RelativeLayout rl_qiang;
	RelativeLayout rl_vibrate;
	RelativeLayout rl_dont_disturb;
	ImageView iv_qiang;
	ImageView iv_vibrate;
	ImageView iv_dont_disturb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initControl() {
		setContentView(R.layout.setting);
		tv_share = (TextView) findViewById(R.id.tv_share);
		tv_share.setOnClickListener(this);
		
		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		tv_version = (TextView) findViewById(R.id.tv_version);
		try {
			tv_version.setText(getVersionName());
		} catch (Exception e) {
			tv_version.setText("");
		}
		iv_qiang = (ImageView) findViewById(R.id.qiang_state);
		rl_qiang = (RelativeLayout) findViewById(R.id.rl_qiang);
		rl_qiang.setOnClickListener(this);
		if (Utils.getQHBState(this)) {
			iv_qiang.setBackgroundResource(R.drawable.switch_open);
		}else {
			iv_qiang.setBackgroundResource(R.drawable.switch_closed);
		}
		
		iv_vibrate = (ImageView) findViewById(R.id.vibrate_state);
		rl_vibrate = (RelativeLayout) findViewById(R.id.rl_vibrate);
		rl_vibrate.setOnClickListener(this);
		if (Utils.getVibrateState(this)) {
			iv_vibrate.setBackgroundResource(R.drawable.switch_open);
		}else {
			iv_vibrate.setBackgroundResource(R.drawable.switch_closed);
		}
		
		iv_dont_disturb = (ImageView) findViewById(R.id.distrub_state);
		rl_dont_disturb = (RelativeLayout) findViewById(R.id.rl_dont_distrub);
		rl_dont_disturb.setOnClickListener(this);
		if (Utils.getDontDisturb(this)) {
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_open);
		}else {
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_closed);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		String version = packInfo.versionName;
		return version;
	}
	@Override
	public void controlClick(View v) {
		switch (v.getId()) {
		case R.id.tv_share:
			Intent intent=new Intent(Intent.ACTION_SEND);   
	        intent.setType("image/*");   
	        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.SHARE_SUBJECT);
	        intent.putExtra(Intent.EXTRA_TEXT, Utils.getShareText(SettingActivity.this));
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
	        startActivity(Intent.createChooser(intent, Constants.SHARE_TITLE));
			break;
		case R.id.rl_dont_distrub:// 免打扰
			setDontDisturbState();
			break;
			
		case R.id.rl_qiang:// 抢红包
			setQHBState();
			break;
			
		case R.id.rl_vibrate:// 振动
			setVibrateState();
			break;
			
		case R.id.tv_back:
			finishActivity(this);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleMsg(Message msg) {
		
	}
	
	/** 设置抢红包开关状态 */
	public void setQHBState(){
		if(Utils.getQHBState(this) == true){// 现在是开着的
			iv_qiang.setBackgroundResource(R.drawable.switch_closed);
			Utils.setQHBState(this, 0);// 关闭
			
			iv_vibrate.setBackgroundResource(R.drawable.switch_closed);
			Utils.setVibrateState(this, 0);
			
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_closed);
			Utils.setDontDisturb(this, 0);
		}else {// 现在是关着的
			iv_qiang.setBackgroundResource(R.drawable.switch_open);
			Utils.setQHBState(this, 1);
		}
	}
	
	/** 设置是否震动状态 */
	public void setVibrateState(){
		if(Utils.getQHBState(this) == false){
			iv_vibrate.setBackgroundResource(R.drawable.switch_closed);
			Utils.setVibrateState(this, 0);
			return;
		}
		if(Utils.getVibrateState(this) == true){// 开着的
			iv_vibrate.setBackgroundResource(R.drawable.switch_closed);
			Utils.setVibrateState(this, 0);
		}else {
			iv_vibrate.setBackgroundResource(R.drawable.switch_open);
			Utils.setVibrateState(this, 1);
		}
	}
	
	/** 设置是否免打扰状态 */
	public void setDontDisturbState(){
		if(Utils.getQHBState(this) == false){
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_closed);
			Utils.setDontDisturb(this, 0);
			return;
		}
		
		if(Utils.getDontDisturb(this) == true){
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_closed);
			Utils.setDontDisturb(this, 0);
		}else {
			iv_dont_disturb.setBackgroundResource(R.drawable.switch_open);
			Utils.setDontDisturb(this, 1);
		}
	}
}

package com.mm.artifact;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.example.weixintext.R;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * 开始界面
 * 
 * @author dianjoy
 * 
 */
public class BeginActivity extends BaseActivity {

	/** 立即开启 */
	TextView tv_begin;
	/** 更多红包 */
	TextView tv_more;
	TextView tv_setting;

	@Override
	public void initControl() {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

		setContentView(R.layout.begin);
		UmengUpdateAgent.update(this);
		tv_begin = (TextView) findViewById(R.id.tv_begin);
		tv_begin.setOnClickListener(this);
		tv_more = (TextView) findViewById(R.id.tv_more);
		tv_more.setOnClickListener(this);
		tv_setting = (TextView) findViewById(R.id.tv_setting);
		tv_setting.setOnClickListener(this);

		startService(new Intent(this, LockService.class));
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		if (Utils.isOpenAsist(this)) {
			Utils.startNewActivity(this, HomeActivity.class);
			finishActivity(this);
		}
		return;
	}

	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}
	@Override
	public void controlClick(View v) {
		switch (v.getId()) {
		case R.id.tv_begin:
			Utils.startNewActivity(BeginActivity.this,
					OpenServiceGuideActivity.class);
			break;
		case R.id.tv_more:
			Utils.startNewActivity(BeginActivity.this,
					HongbaoDesUrlActivity.class);
			break;
		case R.id.tv_setting:
			Utils.startNewActivity(BeginActivity.this, SettingActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void handleMsg(Message msg) {

	}
}

package com.mm.artifact;

import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 设置页面
 * 
 * @author dianjoy
 * 
 */
public class OpenServiceGuideActivity extends BaseActivity {

	RelativeLayout tv_go;
	TextView tv_back;
	ImageView iv_back;
	@Override
	public void initControl() {
		setContentView(R.layout.open_service_guide);
		tv_go = (RelativeLayout) findViewById(R.id.tv_go);
		tv_go.setOnClickListener(this);

		tv_back = (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
		
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	public void controlClick(View v) {
		switch (v.getId()) {
		case R.id.tv_go:
			Intent killIntent = new Intent(
					Settings.ACTION_ACCESSIBILITY_SETTINGS);
			startActivity(killIntent);
			break;
		case R.id.tv_back:
			finishActivity(this);
			break;
		
		case R.id.iv_back:
			finishActivity(this);
			break;
		}
	}

	@Override
	public void handleMsg(Message msg) {

	}
}

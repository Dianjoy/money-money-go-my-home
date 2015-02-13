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
 * 
 * @author dianjoy
 *
 */
public class HomeActivity extends BaseActivity {

	TextView tv_tips;
	TextView tv_setting;
	TextView tv_more;
	TextView tv_nick_name;
	ImageView iv_center_c;
	RelativeLayout rl_bottom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void initControl() {
		setContentView(R.layout.home);
		tv_tips = (TextView) findViewById(R.id.tips);
		tv_setting = (TextView) findViewById(R.id.tv_setting);
		tv_setting.setOnClickListener(this);
		rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		rl_bottom.setOnClickListener(this);
		tv_more = (TextView) findViewById(R.id.tv_more);
		tv_more.setOnClickListener(this);
		tv_nick_name = (TextView) findViewById(R.id.tv_nick_name);
		iv_center_c = (ImageView) findViewById(R.id.iv_center_c);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		setTips();
	}
	@Override
	protected void onPause() {
		MobclickAgent.onPause(this);
		super.onPause();
	}
	private void setTips() {
		String timeString = Utils.getUseTime(this);
		tv_tips.setText("至今通知微信红包" + Utils.getNotifyCounts(this) + "个\n您一共抢了"
				+ Utils.getQiangCounts(this) + "次，平均耗时时间"
				+ timeString + "s");
		Float time = Float.valueOf(timeString);
		if(time == 0){
			tv_nick_name.setText("无称号");
			iv_center_c.setVisibility(View.INVISIBLE);
		}else if(time <= 1){
			iv_center_c.setVisibility(View.VISIBLE);
			iv_center_c.setBackgroundResource(R.drawable.nick_name_flash);
			tv_nick_name.setText("风神");
		}else if(time <= 10){
			iv_center_c.setVisibility(View.VISIBLE);
			iv_center_c.setBackgroundResource(R.drawable.nick_name_flash);
			tv_nick_name.setText("闪电侠");
		}else if(time <= 60){
			iv_center_c.setVisibility(View.VISIBLE);
			iv_center_c.setBackgroundResource(R.drawable.nick_name_flash);
			tv_nick_name.setText("跑男");
		}else {
			iv_center_c.setVisibility(View.VISIBLE);
			iv_center_c.setBackgroundResource(R.drawable.nick_name_flash);
			tv_nick_name.setText("蜗牛");
		}
	}

	@Override
	public void controlClick(View v) {
		switch (v.getId()) {
		case R.id.tv_setting:
			Utils.startNewActivity(HomeActivity.this, SettingActivity.class);
			break;
		case R.id.rl_bottom:
			Intent intent=new Intent(Intent.ACTION_SEND);
	        intent.setType("image/*");
	        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.SHARE_SUBJECT);
	        intent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_TEXT);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        startActivity(Intent.createChooser(intent, Constants.SHARE_TITLE));
			break;
		case R.id.tv_more:
			Utils.startNewActivity(HomeActivity.this,HongbaoDesUrlActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void handleMsg(Message msg) {

	}

}

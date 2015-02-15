package com.mm.artifact;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class NoticeActivity extends BaseActivity {
	private RelativeLayout rl_bottom;

	@Override
	public void initControl() {
		setContentView(R.layout.notice);

		rl_bottom = (RelativeLayout) this.findViewById(R.id.rl_bottom);
		rl_bottom.setOnClickListener(this);
	}

	@Override
	public void controlClick(View v) {
		switch (v.getId()) {
		case R.id.rl_bottom:
			LockActivityManager.getActivityManager().finishActivity(
					OpenServiceGuideActivity.class);
			finishActivity(this);
			break;
		}
	}

	@Override
	public void handleMsg(Message msg) {

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
	public void onBackPressed() {
		super.onBackPressed();
		LockActivityManager.getActivityManager().finishActivity(
				OpenServiceGuideActivity.class);
		finishActivity(this);
	}

}

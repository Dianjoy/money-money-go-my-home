package com.mm.artifact;

import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 开始界面
 * @author dianjoy
 *
 */
public class NotifyRedPackActivity extends BaseActivity {

	Button bt_go;
	RelativeLayout rl_bottom;
	TextView tv_ignor;
	TextView tv_red_pack_count;
	@Override
	public void initControl() {
		setContentView(R.layout.notify_red_pack);
		Utils.addNotifyCounts(this);// 红包提示数量++
		tv_red_pack_count = (TextView) findViewById(R.id.tv_red_pack_count);
		
		
		rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
		rl_bottom.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long endTime = System.currentTimeMillis();
				long intral = endTime - NotifyService.beginTime;
				float thisTime = intral / 1000f;// 本次反应秒数
				// 计算平均速度
				int qiangCounts = Utils.getQiangCounts(NotifyRedPackActivity.this);// 当前抢红包的数
				float currentTime = Float.valueOf(Utils.getUseTime(NotifyRedPackActivity.this));// 当前平均时间
				float currentTotal = qiangCounts * currentTime;// 当前总时间
				float newIntral = (thisTime+currentTotal)/(qiangCounts+1)*1.0f;
				newIntral = (float)(Math.round(newIntral*10))/10;
				Utils.addQiangCounts(NotifyRedPackActivity.this);
				Utils.setUseTime(NotifyRedPackActivity.this, newIntral+"");
				Utils.startAPP(NotifyRedPackActivity.this, "com.tencent.mm");
				finishActivity(NotifyRedPackActivity.this);
			}
		});
		
		tv_ignor = (TextView) findViewById(R.id.tv_ignor);
		tv_ignor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finishActivity(NotifyRedPackActivity.this);
			}
		});
		
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		tv_red_pack_count.setText("有"+LockService.redPackCounts+"个红包，赶快来抢");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	@Override
	public void controlClick(View v) {
	}

	@Override
	protected void onStop() {
		super.onStop();
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LockService.redPackCounts = 0;
//		if(LockService.mKeyguardLock != null){
//			LockService.mKeyguardLock.reenableKeyguard();
//		}
		
	}
	@Override
	public void handleMsg(Message msg) {
		
	}
}

package com.mm.artifact;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.view.View;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent killIntent = new Intent(
				Settings.ACTION_ACCESSIBILITY_SETTINGS);
		startActivity(killIntent);
	}

	@Override
	public void initControl() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controlClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMsg(Message msg) {
		// TODO Auto-generated method stub
		
	}
}

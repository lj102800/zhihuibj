package com.example.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhsh.base.BasePage;

public class SettingPager extends BasePage {

	public SettingPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("设置");
		btnMenu.setVisibility(View.INVISIBLE);
		TextView view=new TextView(mActivity);
		view.setText("设置");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		flContent.addView(view);
	}

}

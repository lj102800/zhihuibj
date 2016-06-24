package com.example.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zhsh.base.BasePage;

public class SmartServicePager extends BasePage {

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		tvTitle.setText("设置");
		TextView view=new TextView(mActivity);
		view.setText("设置");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		flContent.addView(view);
	}

}

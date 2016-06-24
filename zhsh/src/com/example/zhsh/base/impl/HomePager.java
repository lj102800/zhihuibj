package com.example.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhsh.base.BasePage;

public class HomePager extends BasePage {

	public HomePager(Activity activity) {
		super(activity); 
	}

	@Override
	public void initData() {
		tvTitle.setText("智慧上海");
		btnMenu.setVisibility(View.INVISIBLE);
		TextView view=new TextView(mActivity);
		view.setText("首页");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		flContent.addView(view);
	}
}

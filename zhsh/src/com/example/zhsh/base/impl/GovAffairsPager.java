package com.example.zhsh.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.zhsh.base.BasePage;

public class GovAffairsPager extends BasePage {

	public GovAffairsPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		tvTitle.setText("政务");
		TextView view=new TextView(mActivity);
		view.setText("政务");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		flContent.addView(view);
	}

}

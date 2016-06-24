package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
//菜单详情-互动
public class InteractMenuDetailPager extends BaseMenuDetailPager{

	public InteractMenuDetailPager(Activity activity,ArrayList<NewsTabData> children) {
		super(activity);
	
	}

	@Override
	public View initView() {
		TextView view=new TextView(mActivity); 
		view.setText("菜单详情-互动");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		return view;
	}
	
}

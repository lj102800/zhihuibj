package com.example.zhsh.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context) {
		super(context); 
	}

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs); 
	}
	//决定事件是否中断
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		return false;//不拦截事件，让嵌套的子viewpager有机会响应触摸事件
	} 
	 @Override
	public boolean onTouchEvent(MotionEvent arg0) {
	 	return true;
	}
}

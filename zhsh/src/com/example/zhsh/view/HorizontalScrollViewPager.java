package com.example.zhsh.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;

public class HorizontalScrollViewPager extends ViewPager {

	private int startX;
	private int startY;
	private int endX;
	private int endY;
	public HorizontalScrollViewPager(Context context) {
		super(context); 
	}

	public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs); 
	}
	/**
	 * 3种情况需要父控件拦截事件
	 * 1.上下划动需要拦截
	 * 2.向右划第一个页面，需要拦截
	 * 3.向左划最后一个页面，需要拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 请求父控件及祖宗控件不要拦截当前控件的事件
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) ev.getX();
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			endX = (int) ev.getX();
			endY = (int) ev.getY();
			int dx=endX-startX;
			int dy=endY-startY;
			if(Math.abs(dx)-Math.abs(dy)>0){//左右滑动
				if(dx>0&&this.getCurrentItem()==0){//向右划动第一个页面
					// 请求父控件及祖宗控件不要拦截当前控件的事件
					getParent().requestDisallowInterceptTouchEvent(false); 
				}else if(dx<0&&this.getCurrentItem()==getAdapter().getCount()-1){//向左划动最后一个页面
					getParent().requestDisallowInterceptTouchEvent(false); 
				}
			}else
				getParent().requestDisallowInterceptTouchEvent(false);
			break;

		default:
			break;
		}
		// 请求父控件及祖控件不要拦截事件
		return super.dispatchTouchEvent(ev);
	}
}

package com.example.zhsh.base;

import android.app.Activity;
import android.view.View;
/**
 * 侧边栏详情页基类
 * @author liangjun
 *
 */
public abstract class BaseMenuDetailPager {
	public Activity mActivity;
	public View mRootView;
	public BaseMenuDetailPager(Activity activity){
		mActivity=activity;
		mRootView=initView();
	}
	public abstract View initView() ;
	public void initData(){
		
	}
}

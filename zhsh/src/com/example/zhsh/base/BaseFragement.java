package com.example.zhsh.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 1.初始化布局initView
 * 2.初始化initData
 * @author liangjun
 *
 */
public abstract class BaseFragement extends Fragment {
	public Activity mActivity;
	//Fragement被创建
	@Override
	public void onCreate(Bundle savedInstanceState) {
	 	super.onCreate(savedInstanceState);
	 	mActivity = getActivity();
	}
	//初始化Fragement布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=initView();
		return view;
	}
	//activity创建结束
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
 		super.onActivityCreated(savedInstanceState);
 		initData();
	}
	 
	/**
	 * 初始化布局，子类必须实现
	 * @return
	 */
	public abstract View initView();
	/**
	 * 初始化数据，子类可以不实现
	 */
	public  void initData(){}
}

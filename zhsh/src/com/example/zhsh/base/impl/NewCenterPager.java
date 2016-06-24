package com.example.zhsh.base.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;

import com.example.zhsh.MainActivity;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.base.BasePage;
import com.example.zhsh.base.impl.menudetail.InteractMenuDetailPager;
import com.example.zhsh.base.impl.menudetail.NewsMenuDetailPager;
import com.example.zhsh.base.impl.menudetail.PhotosMenuDetailPager;
import com.example.zhsh.base.impl.menudetail.TopicMenuDetailPager;
import com.example.zhsh.domain.NewMenuData;
import com.example.zhsh.global.Constants;
import com.example.zhsh.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewCenterPager extends BasePage {
	private ArrayList<BaseMenuDetailPager> menuDetails;
	private NewMenuData newsMenuData;
	 
	public NewCenterPager(Activity activity) {
		super(activity); 
	}

	@Override
	public void initData() {
		tvTitle.setText("新闻中心"); 
		String cache=CacheUtils.getCache(Constants.CATEGORIES_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){
			//有缓存
			processResult(cache);
		} 
		getDataFromServer(); 
	}
	/**
	 * 从服务器获取数据
	 */
	private void getDataFromServer(){
		HttpUtils utils=new HttpUtils(); 
		utils.send(HttpMethod.GET, Constants.CATEGORIES_URL, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				String result=responseInfo.result; 
				processResult(result);
				CacheUtils.setCache(Constants.CATEGORIES_URL, result, mActivity);
			}

			@Override
			public void onFailure(
					com.lidroid.xutils.exception.HttpException error, String msg) {  
			} 
		});
	}
	/**
	 * 解析json数据
	 */
	protected void processResult(String result) {
		Gson gson=new Gson();
		newsMenuData = gson.fromJson(result, NewMenuData.class);
		MainActivity mainUI=(MainActivity)mActivity;
		LeftMenuFragement leftMenuFragment = mainUI.getLeftMenuFragment();
		leftMenuFragment.setData(newsMenuData.data);
		//初始化4个菜单详情页面
		menuDetails=new ArrayList<BaseMenuDetailPager>();
		menuDetails.add(new NewsMenuDetailPager(mActivity,newsMenuData.data.get(0).children));
		menuDetails.add(new TopicMenuDetailPager(mActivity,newsMenuData.data.get(1).children));
		menuDetails.add(new PhotosMenuDetailPager(mActivity,newsMenuData.data.get(2).children));
		menuDetails.add(new InteractMenuDetailPager(mActivity,newsMenuData.data.get(3).children));
		//菜单详情页---新闻作为初始页面
		setCurrentMenuDetailPager(0);
	}
	protected void setCurrentMenuDetailPager(int position) { 
		BaseMenuDetailPager pager=menuDetails.get(position);
		flContent.removeAllViews();
		flContent.addView(pager.initView());
		pager.initData();
		//更改标题
		tvTitle.setText(newsMenuData.data.get(position).title);
		
	}
}

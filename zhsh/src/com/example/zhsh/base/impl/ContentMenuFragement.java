package com.example.zhsh.base.impl;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.zhsh.MainActivity;
import com.example.zhsh.R;
import com.example.zhsh.base.BaseFragement;
import com.example.zhsh.base.BasePage;
import com.example.zhsh.base.impl.menudetail.NewsMenuDetailPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class ContentMenuFragement extends BaseFragement {

	@ViewInject(R.id.vp_pager)
	private ViewPager mVierPager;
	@ViewInject(R.id.rg_group)
	private RadioGroup RgGroup;
	private ArrayList<BasePage> mPagers;
	@Override
	public View initView() { 
		View view=View.inflate(mActivity, R.layout.fragement_content ,null);
		ViewUtils.inject(this,view);//注入view和事件
		return view;
	}
	@Override
	public void initData() {
	 	super.initData();
	 	//初始化5个标签页面
	 	mPagers=new ArrayList<BasePage>();
	 	mPagers.add(new HomePager(mActivity));
	 	mPagers.add(new NewCenterPager(mActivity));
	 	mPagers.add(new SmartServicePager(mActivity));
	 	mPagers.add(new GovAffairsPager(mActivity));
	 	mPagers.add(new SettingPager(mActivity));
	 	
	 	mVierPager.setAdapter(new ContentAdapter());
	 	RgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
					switch (checkedId) {
					case R.id.rb_home:
						mVierPager.setCurrentItem(0);
						mPagers.get(0).initData();//初始化数据
						setSlidingMenuEnable(false);
						break;
					case R.id.rb_news:
						mVierPager.setCurrentItem(1);
						mPagers.get(1).initData();//初始化数据
						setSlidingMenuEnable(true);
						break;
					case R.id.rb_smart:
						mVierPager.setCurrentItem(2);
						mPagers.get(2).initData();//初始化数据
						setSlidingMenuEnable(true);
						break;
					case R.id.rb_gov:
						mVierPager.setCurrentItem(3);
						mPagers.get(3).initData();//初始化数据
						setSlidingMenuEnable(true);
						break;
					case R.id.rb_setting:
						mVierPager.setCurrentItem(4);
						mPagers.get(4).initData();//初始化数据
						setSlidingMenuEnable(false);
						break;

					default:
						break;
					} 
			}
		}); 
	 	mPagers.get(0).initData();
		setSlidingMenuEnable(false);
	}
	class ContentAdapter extends PagerAdapter{

		@Override
		public int getCount() { 
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) { 
			return arg0==arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePage pager=mPagers.get(position);
			container.addView(pager.mRootView);//将页面添加到容器中
			pager.initData();//初始化数据
			return pager.mRootView; 
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}
	protected NewCenterPager getNewsMenuDetail(){
		NewCenterPager newCenter=(NewCenterPager)mPagers.get(1);
		return newCenter;
	}
	/**
	 * 设置侧边栏可用不可用
	 * @param enable
	 */
	private void setSlidingMenuEnable(boolean enable){
		MainActivity mainUI=(MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else
			//禁用掉侧边栏
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE); 
	}
}

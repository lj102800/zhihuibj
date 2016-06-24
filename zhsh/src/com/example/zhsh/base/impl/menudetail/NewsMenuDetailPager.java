package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhsh.MainActivity;
import com.example.zhsh.R;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
//菜单详情页-新闻
public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{
	@ViewInject(R.id.vp_news_detail)
	private ViewPager mViewPager;
	@ViewInject(R.id.indicator)
	private TabPageIndicator mIndicator;
	ArrayList<NewsTabData> mTabList;
	ArrayList<TabDetailPager> mTabPager;
	public NewsMenuDetailPager(Activity activity,ArrayList<NewsTabData> children) {
		super(activity);
		mTabList=children;
	}

	@Override
	public View initView() {
		View view=View.inflate(mActivity, R.layout.pager_menu_detail_news, null);
		ViewUtils.inject(this,view);
		return view; 
	}
	@Override
	public void initData() {
		mTabPager=new ArrayList<TabDetailPager>();
		for (NewsTabData tableData:mTabList) {
			TabDetailPager pager=new TabDetailPager(mActivity,tableData);
			mTabPager.add(pager);
		}
		mViewPager.setAdapter(new NewsMenuAdapter());
//		mViewPager.setOnPageChangeListener(this);
//		//此方法在viewpager设置完数据之后再调用
		mIndicator.setViewPager(mViewPager);//将页面指示器和viewpager关联起来
		mIndicator.setOnPageChangeListener(this);//当viewpage和指针绑定时，需要将页面切换监听设置给指针
	}
	class NewsMenuAdapter extends PagerAdapter{
		//返回页面指示器的标题
		@Override
		public CharSequence getPageTitle(int position) { 
			return mTabList.get(position).title;
		}
		@Override
		public int getCount() {
			
			return  mTabPager.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0==arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager=mTabPager.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
 		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
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

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	@Override
	public void onPageSelected(int arg0) {
		if(arg0==0){
			//开启侧边栏
			setSlidingMenuEnable(true);
		}else
			setSlidingMenuEnable(false);
			//关闭侧边栏 
	}

	@OnClick(R.id.iv_next_page)
	public void nextPage(View view){
		int currentItem=mViewPager.getCurrentItem();
		currentItem++;
		mViewPager.setCurrentItem(currentItem);
	}
}

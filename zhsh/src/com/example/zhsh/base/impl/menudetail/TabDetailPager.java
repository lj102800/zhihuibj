package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

import com.example.zhsh.R;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
import com.example.zhsh.domain.NewsData;
import com.example.zhsh.domain.NewsData.TopNews;
import com.example.zhsh.global.Constants;
import com.example.zhsh.utils.CacheUtils;
import com.example.zhsh.view.HorizontalScrollViewPager;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
/**
 * 12个页签的页面对象
 * @author Administrator
 *
 */
public class TabDetailPager extends BaseMenuDetailPager {
 
	private NewsTabData mTableData;  
	
	@ViewInject(R.id.lv_table_detail)
	private ListView lvList;
	@ViewInject(R.id.vp_table_detail)
	private   HorizontalScrollViewPager mViewPager;
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator;
	@ViewInject(R.id.tv_title)
	private TextView tvTopNewsTitle;
	 
	private String mUrl;
	private NewsData mNewsTabData;
	private ArrayList<TopNews> mTopNewsList;
	private TopNewAdapter mTopNewAdapter;
	private View view;
	public TabDetailPager(Activity activity,NewsTabData tabData) {
		super(activity);
		mTableData=tabData;
		mUrl=Constants.SERVER_URL+tabData.url;
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
		ViewUtils.inject(this,view);
		return view; 
	}
	@Override
	public void initData() { 
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if(!TextUtils.isEmpty(cache)){
			processResult(cache);
		}
		getDataFromServer();
	}
	private void getDataFromServer(){
		HttpUtils utils=new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				String result = responseInfo.result;
				System.out.println(result);
 				processResult(result);
 				CacheUtils.setCache(mUrl, result, mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) { 
				System.err.println(error+"msg"+msg);
			}
		});
	}
	protected void processResult(String result) {
		Gson gson=new Gson();
		mNewsTabData = gson.fromJson(result, NewsData.class);
		mTopNewsList = mNewsTabData.data.topnews;
		
		//初始化头条新闻
		if(mTopNewsList!=null){
			mTopNewAdapter = new TopNewAdapter();
			mViewPager.setAdapter(mTopNewAdapter); 
			mIndicator.setViewPager(mViewPager);
			mIndicator.setSnap(true);//快照模式
			mIndicator.setCurrentItem(0);
			tvTopNewsTitle.setText(mTopNewsList.get(0).title);
			mIndicator.setOnPageChangeListener(new OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int arg0) {
					tvTopNewsTitle.setText(mTopNewsList.get(arg0).title);
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					 
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) {
					 
				}
			});
		}
		System.out.println(mNewsTabData);
	}
	class TopNewAdapter extends PagerAdapter{
		BitmapUtils mBitmapUtils;
		public TopNewAdapter(){
			mBitmapUtils=new BitmapUtils(mActivity);
		}
		@Override
		public int getCount() { 
			return mTopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) { 
			return arg0==arg1;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) { 
			ImageView view=new ImageView(mActivity);
			view.setScaleType(ScaleType.FIT_XY);//设置图片填充效果，表示填充父窗口
			//获取图片链接，使用链接下载图片，将图片设置为ImageView，考虑内存溢出问题
			mBitmapUtils.display(view, mTopNewsList.get(position).topimage);
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) { 
			container.removeView((View)object); 
		}
	}
	class NewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolde holder;
			if(arg1==null){
				arg1=View.inflate(mActivity, R.layout.list_item_news, null);
				holder=new ViewHolde();
				holder.tvTitle=(TextView) arg1.findViewById(R.id.tv_title);
				holder.tvDate=(TextView) arg1.findViewById(R.id.tv_date);
				holder.ivIcon=(ImageView) arg1.findViewById(R.id.iv_icon);
				arg1.setTag(holder);
			}else{
				holder=(ViewHolde) arg1.getTag();
			} 
			return arg1;
		} 
	}
	static class ViewHolde{
		public TextView tvTitle;
		public TextView tvDate;
		public ImageView ivIcon;
	}
}

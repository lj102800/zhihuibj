package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.sax.StartElementListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhsh.NewsDetailActivity;
import com.example.zhsh.R;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
import com.example.zhsh.domain.NewsData;
import com.example.zhsh.domain.NewsData.News;
import com.example.zhsh.domain.NewsData.TopNews;
import com.example.zhsh.global.Constants;
import com.example.zhsh.utils.CacheUtils;
import com.example.zhsh.utils.PrefUtils;
import com.example.zhsh.view.HorizontalScrollViewPager;
import com.example.zhsh.view.RefreshListView;
import com.example.zhsh.view.RefreshListView.OnRefreshListener;
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
	private RefreshListView lvList;
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

	private View header;

	private ArrayList<News> mNewsList;

	private String mMoreUrl;

	private NewAdapter mNewAdapter;
	public TabDetailPager(Activity activity,NewsTabData tabData) {
		super(activity);
		mTableData=tabData;
		mUrl=Constants.SERVER_URL+tabData.url;
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
		ViewUtils.inject(this,view);
		header = View.inflate(mActivity, R.layout.list_header_topnews, null);
		ViewUtils.inject(this,header);//必须也将布局注入到ViewUtils
		lvList.addHeaderView(header);
		lvList.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() { 
				getDataFromServer();
			}

			@Override
			public void loadMore() { 
				if(mMoreUrl!=null)
					getMoreDataFromServer();
				else{
					Toast.makeText(mActivity, "没有更多数据了", 0).show();
					//收起下拉刷新控件
					lvList.refreshComplete(false);
				}
			}

		});
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("3"+arg2);
				News news=mNewsList.get(arg2);
				TextView tvTitle=(TextView) arg1.findViewById(R.id.tv_title);
				tvTitle.setTextColor(Color.GRAY);
 				//将已读持久化到本地
				String readIds=PrefUtils.getString("read_ids", "", mActivity);
				if(!readIds.contains(news.id)){
					readIds=readIds+mNewsList.get(arg2).id+",";
					PrefUtils.putString("read_ids", readIds, mActivity);
				}
				//跳到详情页
				Intent intent=new Intent(mActivity,NewsDetailActivity.class);
				intent.putExtra("url", news.url);
				mActivity.startActivity(intent);
			}
		});
		return view; 
	}
	@Override
	public void initData() { 
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if(!TextUtils.isEmpty(cache)){
			processResult(cache,false);
		}
		getDataFromServer();
	}
	/**
	 * 加载更多数据
	 */
	private void getMoreDataFromServer() {
		HttpUtils utils=new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				String result = responseInfo.result; 
 				processResult(result,true); 
				//收起下拉刷新控件
				lvList.refreshComplete(false);
			}

			@Override
			public void onFailure(HttpException error, String msg) { 
				//收起下拉刷新控件
				lvList.refreshComplete(false);
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				System.err.println(error+"msg"+msg);
			}
		});
		
	}
	/**
	 * 从网络获取数据
	 */
	private void getDataFromServer(){
		HttpUtils utils=new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				String result = responseInfo.result;
				System.out.println(result);
 				processResult(result,false);
 				CacheUtils.setCache(mUrl, result, mActivity);
				//收起加载更多控件
				lvList.refreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) { 
				//收起加载更多控件
				lvList.refreshComplete(false);
				Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
				System.err.println(error+"msg"+msg);
			}
		});
	}
	/**
	 * 解析json数据
	 * @param result
	 */
	protected void processResult(String result,boolean isMore) {
		Gson gson=new Gson();
		mNewsTabData = gson.fromJson(result, NewsData.class);
		mTopNewsList = mNewsTabData.data.topnews;
		if(!TextUtils.isEmpty(mNewsTabData.data.more))
			mMoreUrl=Constants.SERVER_URL+mNewsTabData.data.more;
		else
			mMoreUrl=null;
		if(!isMore){
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
				mIndicator.onPageSelected(0);//将小圆点位置归零，否则它会在页面销毁时仍记录上次位置的bug 
			}
			mNewsList = mNewsTabData.data.news;
			if(mNewsList!=null){
				mNewAdapter = new NewAdapter();
				lvList.setAdapter(mNewAdapter);
			}
		}else{
			ArrayList<News> moreData = mNewsTabData.data.news;
			mNewsList.addAll(moreData);//追加数据 
			mNewAdapter.notifyDataSetChanged();//刷新listview
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
		public BitmapUtils mBitmapUtils;

		public NewAdapter(){
			mBitmapUtils = new BitmapUtils(mActivity);
			mBitmapUtils.configDefaultLoadFailedImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() { 
			return mNewsList.size();
		}

		@Override
		public News getItem(int arg0) { 
			return mNewsList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) { 
			return arg0;
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
			News news= getItem(arg0);
			holder.tvTitle.setText(news.title);
			holder.tvDate.setText(news.pubdate);
			mBitmapUtils.display(holder.ivIcon, news.listimage);
			
			//标记已读和未读
			String readIds=PrefUtils.getString("read_ids", "", mActivity);
			if(readIds.contains(news.id)){
				//已读
				holder.tvTitle.setTextColor(Color.GRAY);
			}else
				//未读
				holder.tvTitle.setTextColor(Color.BLACK);
			return arg1;
		} 
	}
	static class ViewHolde{
		public TextView tvTitle;
		public TextView tvDate;
		public ImageView ivIcon;
	}
}

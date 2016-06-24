package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhsh.R;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
import com.example.zhsh.domain.NewsData;
import com.example.zhsh.domain.NewsData.TopNews;
import com.example.zhsh.global.Constants;
import com.example.zhsh.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
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
	private ViewPager mViewPager;
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
//		view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
//		ViewUtils.inject(this,view);
//		return view;
		TextView view=new TextView(mActivity); 
		view.setText("菜单详情-互动");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		return view;
	}
	@Override
	public void initData() { 
//		String cache = CacheUtils.getCache(mUrl, mActivity);
//		if(!TextUtils.isEmpty(cache)){
//			processResult(cache);
//		}
//		getDataFromServer();
	}
	private void getDataFromServer(){
		HttpUtils utils=new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				String result = responseInfo.result;
				System.out.println(result);
 				processResult(result);
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
			//获取图片链接，使用链接下载图片，将图片设置为ImageView，考虑内存溢出问题
			mBitmapUtils.display(view, mTopNewsList.get(position).topimage);
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) { 
			container.removeView((View)object);
			super.destroyItem(container, position, object);
		}
	}
}

package com.example.zhsh.base.impl.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable.ConstantState;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhsh.R;
import com.example.zhsh.base.BaseMenuDetailPager;
import com.example.zhsh.domain.NewMenuData.NewsTabData;
import com.example.zhsh.domain.PhotoBean;
import com.example.zhsh.domain.PhotoBean.PhotoData;
import com.example.zhsh.domain.PhotoBean.PhotoNewsData;
import com.example.zhsh.global.Constants;
import com.example.zhsh.utils.CacheUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
//菜单详情-组图
public class PhotosMenuDetailPager extends BaseMenuDetailPager implements OnClickListener{

	@ViewInject(R.id.lv_list)
	private ListView lvlist;
	@ViewInject(R.id.gv_list)
	private GridView gvlist;
	ImageButton btnDisplay;
	private ArrayList<PhotoNewsData> mPhotoList;
	private boolean islist=true;
	public PhotosMenuDetailPager(Activity activity,ImageButton btnDisplay) {
		super(activity);
		btnDisplay.setOnClickListener(this);
		this.btnDisplay=btnDisplay;
	}

	@Override
	public View initView() {
		View view=View.inflate(mActivity, R.layout.pager_menu_detail_photo, null);
		ViewUtils.inject(this, view);
		return view;
	}
	@Override
	public void initData() {
		String cache=CacheUtils.getCache(Constants.PHOTOS_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){
			processResult(cache);
		}
		getDataFromServer();
		super.initData();
	}
	private void getDataFromServer() {
		HttpUtils utils=new HttpUtils();
		utils.send(HttpMethod.GET, Constants.SERVER_URL+"/photos/photos_1.json", new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) { 
				processResult(responseInfo.result);
				CacheUtils.setCache(Constants.PHOTOS_URL, responseInfo.result, mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});
	}
	protected void processResult(String result) {
		Gson gson=new Gson();
		PhotoBean photoBean = gson.fromJson(result, PhotoBean.class);
		mPhotoList = photoBean.data.news;
		lvlist.setAdapter(new PhotoAdapter());
		gvlist.setAdapter(new PhotoAdapter());
	}
	class PhotoAdapter extends BaseAdapter{
		private BitmapUtils mBitmapUtils;
		public PhotoAdapter(){
			mBitmapUtils=new BitmapUtils(mActivity);
			mBitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}
		@Override
		public int getCount() { 
			return mPhotoList.size();
		}

		@Override
		public PhotoNewsData getItem(int arg0) { 
			return mPhotoList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) { 
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder=null;
			if(arg1==null){
				arg1=View.inflate(mActivity, R.layout.list_item_photo, null);
				holder=new ViewHolder();
				holder.tvTitle=(TextView) arg1.findViewById(R.id.tv_title);
				holder.ivIcon=(ImageView) arg1.findViewById(R.id.iv_icon);
				arg1.setTag(holder);
			}else{
				holder=(ViewHolder) arg1.getTag(); 
			}
			PhotoNewsData item=getItem(arg0);
			holder.tvTitle.setText(item.title);
			mBitmapUtils.display(holder.ivIcon, item.listimage);
			return arg1;
		} 
	}
	static class ViewHolder{
		public 	TextView tvTitle;
		public ImageView ivIcon; 
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_display:
			if(islist){
				lvlist.setVisibility(View.GONE);
				gvlist.setVisibility(View.VISIBLE);	
				btnDisplay.setImageResource(R.drawable.icon_pic_grid_type);
				islist=false;
			}else{
				lvlist.setVisibility(View.VISIBLE);
				gvlist.setVisibility(View.GONE);			
				btnDisplay.setImageResource(R.drawable.icon_pic_list_type);
				islist=true;
			}
			break;

		default:
			break;
		}
	}
}

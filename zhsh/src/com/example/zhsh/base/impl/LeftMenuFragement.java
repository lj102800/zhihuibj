package com.example.zhsh.base.impl;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhsh.MainActivity;
import com.example.zhsh.R;
import com.example.zhsh.base.BaseFragement;
import com.example.zhsh.domain.NewMenuData.NewsData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LeftMenuFragement extends BaseFragement {
	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private ArrayList<NewsData> mMenuList;
	private int mCurrentPos;
	private MenuAdapter menuAdapter;
	@Override
	public View initView() { 
		View view=View.inflate(mActivity, R.layout.fragement_left_menu ,null);
		ViewUtils.inject(this,view);
		return view;
	} 
	public void setData(ArrayList<NewsData> data){
		mMenuList=data;
		menuAdapter = new MenuAdapter();
		lvList.setAdapter(menuAdapter);
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				mCurrentPos=arg2;
				menuAdapter.notifyDataSetChanged();
				setCurrentMenuDetailPager(arg2);
				toggle();
			} 
		});
		mCurrentPos=0;
	}
	private void setCurrentMenuDetailPager(int arg2) { 
		MainActivity mainUI=(MainActivity)mActivity;
		ContentMenuFragement contentMenuFragement = mainUI.getContentMenuFragement();
		NewCenterPager newCenter=contentMenuFragement.getNewsMenuDetail();
		newCenter.setCurrentMenuDetailPager(arg2);
	}
	private void toggle(){
		MainActivity mainUI=(MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	class MenuAdapter extends BaseAdapter{

		@Override
		public int getCount() { 
			return mMenuList.size();
		}

		@Override
		public NewsData getItem(int position) { 
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) { 
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view=View.inflate(mActivity, R.layout.list_item_left_menu ,null);
			TextView tvMenu=(TextView) view.findViewById(R.id.tv_menu);
			NewsData data = getItem(position);
			tvMenu.setText(data.title);
			if(mCurrentPos==position){
				tvMenu.setEnabled(true);
			}else
				tvMenu.setEnabled(false);
			return view;
		}
		
	}
}

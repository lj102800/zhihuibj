package com.example.zhsh.base;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.zhsh.MainActivity;
import com.example.zhsh.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
/**
 * 五个标签页的基类
 * @author liangjun
 *
 */
public abstract class BasePage {
	public Activity mActivity;
	public View mRootView;
	public ImageButton btnMenu;
	public FrameLayout flContent;
	public TextView tvTitle;
	public BasePage(Activity activity){
		mActivity=activity;
		initView();
	}
	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.base_paper, null);
		tvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
		btnMenu=(ImageButton)mRootView.findViewById(R.id.btn_menu);
		flContent=(FrameLayout)mRootView.findViewById(R.id.fl_content2);
		btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
	}
	/**
	 * 初始化数据
	 */
	public abstract void initData();

	private void toggle(){
		MainActivity mainUI=(MainActivity)mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
}

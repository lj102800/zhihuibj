package com.example.zhsh;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.example.zhsh.base.impl.ContentMenuFragement;
import com.example.zhsh.base.impl.LeftMenuFragement;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {
	String TAG_LEFT_MENU = "TAG_LEFT_MENU";
	String TAG_CONTENT="TAG_CONTENT";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setBehindContentView(R.layout.left_menu);
		SlidingMenu slidingMenu=getSlidingMenu();
		//全屏触摸
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		WindowManager wm=(WindowManager) getSystemService(WINDOW_SERVICE);
		int width=wm.getDefaultDisplay().getWidth();
		//修改侧边栏宽度
		slidingMenu.setBehindOffset((int)(width*0.625));//屏幕预留200个像素宽度(预留一定比例，进行屏幕适配)

		initFragement();
	}
	private void initFragement() {
		FragmentManager fm=getSupportFragmentManager();
		FragmentTransaction transaction=fm.beginTransaction();//开启事务
		
		//将帧布局替换为对应的Fragement

		transaction.replace(R.id.fl_left_menu, new LeftMenuFragement(),TAG_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentMenuFragement(),TAG_CONTENT);
		transaction.commit();//提交事务
 	}
	/**
	 * 获取侧边栏对象
	 */
	public LeftMenuFragement getLeftMenuFragment(){
		FragmentManager fm=getSupportFragmentManager();
		LeftMenuFragement fragment=(LeftMenuFragement) fm.findFragmentByTag(TAG_LEFT_MENU);
		return fragment;
	}
	/**
	 * 获取主页面对象
	 */
	public ContentMenuFragement getContentMenuFragement(){
		FragmentManager fm=getSupportFragmentManager();
		ContentMenuFragement fragment=(ContentMenuFragement) fm.findFragmentByTag(TAG_CONTENT);
		return fragment;
	}
}	

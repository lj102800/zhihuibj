package com.example.zhsh;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
/**
 * 新闻详情页面
 * @author Administrator
 *
 */
public class NewsDetailActivity extends Activity {
	@ViewInject(R.id.ll_controller)
	private LinearLayout llController;
	@ViewInject(R.id.btn_back)
	private ImageButton btnBack;
	@ViewInject(R.id.btn_menu)
	private ImageButton btnMenu;
	@ViewInject(R.id.btn_textsize)
	private ImageButton btnTextSize;
	@ViewInject(R.id.btn_share)
	private ImageButton btnShare;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		ViewUtils.inject(this);
	}
}

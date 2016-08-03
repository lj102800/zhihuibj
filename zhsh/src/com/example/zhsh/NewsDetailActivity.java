package com.example.zhsh;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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
	@ViewInject(R.id.wv_webview)
	private WebView mWebView;
	@ViewInject(R.id.pb_loading)
	private ProgressBar pbLoading;
	private String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);
		ViewUtils.inject(this);
		
		btnBack.setVisibility(View.VISIBLE);
		btnMenu.setVisibility(View.GONE);
		llController.setVisibility(View.VISIBLE);
		
		url = getIntent().getStringExtra("url");
		mWebView.loadUrl(url);
		WebSettings settings=mWebView.getSettings();
		settings.setBuiltInZoomControls(true);//显示放大缩小按钮
		settings.setUseWideViewPort(true);//支持双击缩放
		settings.setJavaScriptEnabled(true);//打开js功能
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pbLoading.setVisibility(View.VISIBLE);
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				
				super.onPageFinished(view, url);
				pbLoading.setVisibility(View.GONE);
			}
			//网页跳转
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);//强制在当前页面加载网页，不用跳转浏览器
				return true;
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}
		});
	}
}

package com.example.zhsh;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.TextSize;
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
public class NewsDetailActivity extends Activity implements OnClickListener{
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
		btnTextSize.setVisibility(View.VISIBLE);
		btnMenu.setVisibility(View.GONE);
		llController.setVisibility(View.VISIBLE);

		btnBack.setOnClickListener(this);
		btnTextSize.setOnClickListener(this);
		btnShare.setOnClickListener(this); 
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
	private int mCurrentItem;
	private int mSelectItem=2;
	private void showChooseDialog(){
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("字体设置");
		String[] items=new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
		builder.setSingleChoiceItems(items, mSelectItem, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mCurrentItem=which;
				
			} 
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				WebSettings settings = mWebView.getSettings();
				switch (mCurrentItem) {
				case 0:
					settings.setTextSize(TextSize.LARGEST);
					//					settings.setTextZoom(10);
					break;
				case 1:
					settings.setTextSize(TextSize.LARGER);
					
					break;
				case 2:
					settings.setTextSize(TextSize.NORMAL);
					
					break;
				case 3:
					settings.setTextSize(TextSize.SMALLER);
					
					break;
				case 4:
					settings.setTextSize(TextSize.SMALLEST);
					
					break; 

				default:
					break;
				}
				mSelectItem=mCurrentItem;
			}
		});
		builder.setNegativeButton("取消",  new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		});
		builder.show();
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:

			break;
		case R.id.btn_textsize:
			showChooseDialog();
			break;
		case R.id.btn_share:

			break; 

		default:
			break;
		}
	
	}
}

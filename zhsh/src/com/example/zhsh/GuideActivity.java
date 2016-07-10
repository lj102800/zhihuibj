package com.example.zhsh;

import java.util.ArrayList;

import com.example.zhsh.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GuideActivity extends Activity implements OnClickListener{
	private ViewPager mViewPage;
	private LinearLayout llContainer;
	private ImageView ivRedPoint;
	private Button btStart;

	private int[] mImageIds=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
	
	private ArrayList<ImageView> mImageViewList;
	private int mPointWith;//两个小圆点的宽度
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		mViewPage = (ViewPager) findViewById(R.id.vp_pager);
		llContainer=(LinearLayout)findViewById(R.id.ll_container);
		ivRedPoint=(ImageView)findViewById(R.id.iv_red_point);
		btStart=(Button)findViewById(R.id.bt_start);
		btStart.setOnClickListener(this);
		mImageViewList = new ArrayList<ImageView>();
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView view=new ImageView(this);
			view.setBackgroundResource(mImageIds[i]);
			mImageViewList.add(view);
			
			ImageView pointView=new ImageView(this);
			pointView.setImageResource(R.drawable.shape_circle_default);
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
			if(i>0){
				 params.leftMargin=10;//从第二个点开始设置边距
			}
			pointView.setLayoutParams(params);
			
			llContainer.addView(pointView);
		} 
		mViewPage.setAdapter(new MyAdapter());
		//页面绘制结束后，计算两个圆点的距离
		ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			//layout方法执行结束之后(位置确定)
			@Override
			public void onGlobalLayout() {
				//移除监听
				ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mPointWith = llContainer.getChildAt(1).getLeft()-llContainer.getChildAt(0).getLeft();
			}
		});
		//页面滑动过程中回调
		mViewPage.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) { 
				if(position==mImageIds.length-1){
					btStart.setVisibility(View.VISIBLE);
				}else
					btStart.setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// 计算当前小红点的左边距
				int leftMargin=(int)(mPointWith*positionOffset+position*mPointWith);
				//修改小红点的左边距
				RelativeLayout.LayoutParams params=	(LayoutParams) ivRedPoint.getLayoutParams();
				params.leftMargin=leftMargin;
				ivRedPoint.setLayoutParams(params);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) { 
				
			}
		});
	}
	class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() { 
			return mImageIds.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) { 
			return view==object;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			ImageView view=new ImageView(getApplicationContext());
//			view.setBackgroundResource(mImageIds[position]);
			ImageView view=mImageViewList.get(position);
			container.addView(view);
			return view;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_start:
			PrefUtils.putBoolean("is_guide_show",true, this);
			startActivity(new Intent(this, MainActivity.class));
			finish();
			break;

		default:
			break;
		}
	}
}

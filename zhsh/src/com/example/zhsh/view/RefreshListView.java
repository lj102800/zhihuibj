package com.example.zhsh.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhsh.R;

public class RefreshListView extends ListView implements OnScrollListener,android.widget.AdapterView.OnItemClickListener{

	private static final int STATE_PULL_TO_REFRESH=1;//下拉刷新
	private static final int STATE_RELEASE_TO_REFRESH=2;//松开刷新
	private static final int STATE_REFRESHING=3;//正在刷新
	
	private View mHeaderView ;
	private int mHeaderViewHeight;
	private int startY;
	private int endY;
	private int mCurrentState=STATE_PULL_TO_REFRESH;//默认是下拉刷新
	private TextView tvTitle;
	private ImageView ivArrow;
	private ProgressBar pbLoading;
	private RotateAnimation animDown;
	private RotateAnimation animUp;
	private View mFooterView;
	private int mFooterViewHeight; 
	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}
	private void initHeaderView(){
		mHeaderView = View.inflate(getContext(), R.layout.list_refresh_header, null);
		this.addHeaderView(mHeaderView);//添加布局
		//隐藏布局（1.获取头布局高度，2.设置负paddingTop，布局就会往上走）
//		int height=mHeaderView.getHeight();//为0 此处无法获取高度，因为布局还没有绘制完成
		//绘制之前就要获取布局高度
		mHeaderView.measure(0, 0);//手动测量布局
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arrow);
		pbLoading = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);
		tvTime = (TextView) findViewById(R.id.tv_time);
		initAnim();
		setCurrentTime();
	}
	/**
	 * 初始化脚步布局
	 */
	private void initFooterView(){
		mFooterView = View.inflate(getContext(), R.layout.list_refresh_footer, null);
		this.addFooterView(mFooterView);//添加布局mHeaderView.measure(0, 0);//手动测量布局
		mFooterView.measure(0, 0);//手动测量布局
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
		this.setOnScrollListener(this);
	}
	/**
	 * 初始化箭头动画
	 */
	private void initAnim(){
		animUp = new RotateAnimation(0,-180,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animUp.setDuration(500);
		animUp.setFillAfter(true);//保持状态
		animDown = new RotateAnimation(-180,0,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
		animDown.setDuration(500);
		animDown.setFillAfter(true);//保持状态
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getY();
			break;

		case MotionEvent.ACTION_MOVE:
			if(startY==-1){//如果用户按住头条新闻向下滑动，会导致listview无法拿到ACTIVITY_DOWN，此时呀重新获取startY
				startY=(int) ev.getY();
			}
			//如果当前正在刷新，什么都不做了
			if(mCurrentState==STATE_REFRESHING){
				break;
			}
			endY = (int) ev.getY();
			int dy=endY-startY; 
			if(dy>0&&getFirstVisiblePosition()==0){//向下滑动&&当前显示的第一个item,才允许下拉刷新出来
				int paddingTop=dy-mHeaderViewHeight;//计算当前paddingTop值
				System.out.println(paddingTop+"===="+mHeaderViewHeight);
				if(paddingTop>=0&&mCurrentState!=STATE_RELEASE_TO_REFRESH){//切换到松开刷新
					mCurrentState=STATE_RELEASE_TO_REFRESH;
					refreshState(); 
				}else if(paddingTop<0&&mCurrentState!=STATE_PULL_TO_REFRESH){//切换到下拉刷新
					mCurrentState=STATE_PULL_TO_REFRESH;
					refreshState(); 
				}
				mHeaderView.setPadding(0, paddingTop, 0, 0);//重新设置头布局padding
				return true;
			}
			break;

		case MotionEvent.ACTION_UP:
			startY=-1;//起始坐标归零
			if(mCurrentState==STATE_RELEASE_TO_REFRESH){
				//如果当前是松开刷新，就要切换到正在刷新
				mCurrentState=STATE_REFRESHING;
				refreshState();
				//显示头布局
				mHeaderView.setPadding(0, 0, 0, 0);
			}else if(mCurrentState==STATE_REFRESHING){
				//隐藏头布局
				mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			}
			break;
	
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
	/**
	 * 根据当前状态刷新界面
	 */
	private void refreshState(){
		switch (mCurrentState) {
		case STATE_PULL_TO_REFRESH:
			tvTitle.setText("下拉刷新");
			ivArrow.startAnimation(animDown);
			pbLoading.setVisibility(View.INVISIBLE);
			break;
		case STATE_RELEASE_TO_REFRESH:
			tvTitle.setText("松开刷新");
			ivArrow.startAnimation(animUp);
			pbLoading.setVisibility(View.INVISIBLE);
			//下拉刷新毁掉
			if(mListener!=null){
				mListener.onRefresh();
			}
			break;
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新...");
			pbLoading.setVisibility(View.VISIBLE);
			ivArrow.clearAnimation();//必须清除动画才能隐藏
			ivArrow.setVisibility(View.INVISIBLE);
			break;

		default:
			break;
		}
	}
	/**
	 * 设置上次刷新时间
	 */
	private void setCurrentTime(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//HH表示24小时制
		String time=format.format(new Date());
		tvTime.setText(time);
	}
	/**
	 * 刷新完成
	 */
	public void refreshComplete(boolean success){
		if(!isLoadingMore){
			//隐藏头布局
			mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			mCurrentState=STATE_PULL_TO_REFRESH;
			tvTitle.setText("下拉刷新");
			pbLoading.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			//刷新失败不用更新时间
			if(success)
			setCurrentTime();
		}else{
			System.out.println("=======complete"+mFooterViewHeight);
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			isLoadingMore=false;
		}
	}
	private OnRefreshListener mListener;
	private TextView tvTime;
	private boolean isLoadingMore;
	public void setOnRefreshListener(OnRefreshListener listener){
		mListener=listener; 
	}
	public interface OnRefreshListener{
		public  void onRefresh();
		public  void loadMore();
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) { 
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState==SCROLL_STATE_IDLE){
			int lastVisiblePosition = getLastVisiblePosition();//当前界面显示的最后一个item的位置
			if(lastVisiblePosition>=getCount()-1&&!isLoadingMore){
				isLoadingMore=true;
				System.out.println("onScroll========");
				//显示底部
				mFooterView.setPadding(0, 0, 0, 0);
				//listview设置当前要展示的item的位置
				setSelection(getCount()-1);//跳到加载更多item的位置去展示
				if(mListener!=null){
					mListener.loadMore();
				}
			}
		}
		
	}
	//重写item点击方法
	private OnItemClickListener mItemClickListener;
	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		mItemClickListener=listener;
		super.setOnItemClickListener(this);
		System.out.println("1");
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 if(mItemClickListener!=null){
			 mItemClickListener.onItemClick(arg0, arg1, arg2-getHeaderViewsCount(), arg3);
				System.out.println("2"+(arg2-getHeaderViewsCount()));
		 }
	}	
}

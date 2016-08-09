package com.example.zhsh.utils.bitmap;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 三级缓存工具类
 * @author liangjun
 *
 */
public class MyBitmapUtil { 
	private NetCacheUtils mNetUtils;
	private LocalCacheUtil mLocalUtils;
	public MyBitmapUtil(){
		mLocalUtils=new LocalCacheUtil();
		mNetUtils=new NetCacheUtils(mLocalUtils); 
	}
	public void display(ImageView ivIcon, String listimage) { 
		//先从内存缓存加载
		//再从本地缓存加载
		Bitmap bitmap=mLocalUtils.getBitmapFromLocal(listimage);
		if(bitmap!=null){
			ivIcon.setImageBitmap(bitmap);
			return;
		}
		//从网络缓存加载
		mNetUtils.getBitmapFromNet(ivIcon,listimage);
	}

}
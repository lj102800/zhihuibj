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
	private MemoryCacheUtils mMemoryUtils;
	public MyBitmapUtil(){
		mMemoryUtils=new MemoryCacheUtils();
		mLocalUtils=new LocalCacheUtil();
		mNetUtils=new NetCacheUtils(mLocalUtils,mMemoryUtils); 
	}
	public void display(ImageView ivIcon, String listimage) { 
		//先从内存缓存加载
		Bitmap bitmap=mMemoryUtils.getBitmapFormMemory(listimage);
		if(bitmap!=null){
			ivIcon.setImageBitmap(bitmap);
			System.out.println("先从内存缓存加载");
			return;
		}
		//再从本地缓存加载
		bitmap=mLocalUtils.getBitmapFromLocal(listimage);
		if(bitmap!=null){
			ivIcon.setImageBitmap(bitmap);
			System.out.println("再从本地缓存加载");
			mMemoryUtils.setBitmapToMemory(listimage, bitmap); 
			return;
		}
		//从网络缓存加载
		mNetUtils.getBitmapFromNet(ivIcon,listimage);
	}

}
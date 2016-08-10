package com.example.zhsh.utils.bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoryCacheUtils {
//	private HashMap<String, SoftReference<Bitmap>> mMemoryCache=new HashMap<String, SoftReference<Bitmap>>();
	//android2.3(API level 9)开始，垃圾回收器会更倾向于回收持有软引用或弱引用的对象，这让软引用和弱引用变得不可靠，建议用LRuCache
	private LruCache<String , Bitmap> mCache;
	public MemoryCacheUtils(){
		int maxMemory=(int) Runtime.getRuntime().maxMemory();//获取虚拟机分配的内存16M
		//LRU最近最少使用，通过控制内存不用超过最大值（由开发者指定），来解决内存溢出
		mCache=new LruCache<String, Bitmap>(maxMemory/8){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int size=value.getRowBytes() * value.getHeight();//每一行的字节数乘以高度
				return size;
			}
		};
	}
	public Bitmap getBitmapFormMemory(String url){
//		SoftReference<Bitmap> softReference = mMemoryCache.get(url);
//		if(softReference!=null){
//			Bitmap bitmap = softReference.get();
//			return bitmap;			
//		}
		return mCache.get(url);
//		return null;
	}
	public void setBitmapToMemory(String url,Bitmap bitmap){
//		SoftReference<Bitmap> soft=new SoftReference<Bitmap>(bitmap);
//		mMemoryCache.put(url, soft);
		mCache.put(url, bitmap);
	}
}

package com.example.zhsh.utils;

import android.content.Context;

/**
 * 网络缓存工具类
 * 原则：
 * 以url为参数key,以json为value保存起来
 * @author Administrator
 *
 */
public class CacheUtils {
	
	public static void setCache(String url,String json,Context ctx){
		//有时候，可以将缓存写在本地文件中，以md5(url)为文件名，以json为文件内容保存	
		PrefUtils.putString(url, json, ctx);
		}
	public static String getCache(String url,Context ctx){
		return PrefUtils.getString(url, null, ctx);
	}
}

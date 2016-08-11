package com.example.zhsh.utils;

import android.content.Context;

public class DensityUtils {
	public static int dp2px(int dp,Context ctx){
		float density=ctx.getResources().getDisplayMetrics().density;//设备密度
		int px=(int)(dp*density+0.5);//+0.5为四舍五入
		return px;
	}
	public static float px2dp(int px,Context ctx){
		float density=ctx.getResources().getDisplayMetrics().density;//设备密度
		float dp= px/density ;
		return dp;
	}
}

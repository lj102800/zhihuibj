package com.example.zhsh;

import android.app.Application;
import cn.jpush.android.api.JPushInterface;

public class ExampleApplication extends Application {
	@Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
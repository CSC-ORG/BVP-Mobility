package com.csc.application;

import android.app.Application;

public class MyApplication extends Application{
	
	private static MyApplication mApplication;
	public static String BASE_URL="http://smarthome.net84.net";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mApplication=this;
	}

	public static MyApplication getmApplication() {
		return mApplication;
	}
	

}

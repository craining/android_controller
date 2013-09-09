package com.zgy.controller;

import com.zgy.controller.util.InitUtil;

import android.app.Application;


public class ControllerApplication extends Application{

	@Override
	public void onCreate() {
		InitUtil.init(this);
		super.onCreate();
	}

}

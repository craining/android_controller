package com.zgy.controller;

import com.zgy.controller.util.InitUtil;

import android.app.Application;
import android.text.TextUtils;

public class ControllerApplication extends Application {
	private String controlTel = "";
	private String appPwd = "";

	private static ControllerApplication instence;

	public static ControllerApplication getInstence() {
		return instence;
	}

	@Override
	public void onCreate() {
		instence = this;
		InitUtil.init(this);
		super.onCreate();
	}

	public String getControlTel() {
		if (TextUtils.isEmpty(controlTel))
			InitUtil.getConfig(instence);
		return controlTel;
	}

	public void setControlTel(String controlTel) {
		this.controlTel = controlTel;
	}

	public String getAppPwd() {
		if (TextUtils.isEmpty(appPwd))
			InitUtil.getConfig(instence);
		return appPwd;
	}

	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}

}

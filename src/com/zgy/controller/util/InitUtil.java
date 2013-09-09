package com.zgy.controller.util;

import com.zgy.controller.ControllerApplication;
import com.zgy.controller.GlobleCs;
import com.zgy.controller.beans.ConfigInfo;

import android.content.Context;

public class InitUtil {

	public static void init(Context context) {
		getConfig(context);
	}

	public static void getConfig(Context context) {
		ConfigInfo config = XmlUtil.getConfigInfo(context);
		ControllerApplication.getInstence().setAppPwd(config.getAppPwd());
		ControllerApplication.getInstence().setControlTel(config.getControlTel());
	}
}

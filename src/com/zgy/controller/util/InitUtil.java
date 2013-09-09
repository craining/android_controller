package com.zgy.controller.util;

import com.zgy.controller.GlobleCs;
import com.zgy.controller.beans.ConfigInfo;

import android.content.Context;


public class InitUtil {

	
	
	public static void init(Context context) {
		getConfig(context);
	}
	
	
	private static void getConfig(Context context) {
		ConfigInfo config = XmlUtil.getConfigInfo(context);
		GlobleCs.appPwd = config.getAppPwd();
		GlobleCs.controlTel = config.getControlTel();
	}
}

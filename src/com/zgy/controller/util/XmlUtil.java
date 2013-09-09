package com.zgy.controller.util;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.zgy.controller.R;
import com.zgy.controller.beans.ConfigInfo;

/**
 * xml解析
 * 
 * @Description:
 * @author: zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-9-9
 */
public class XmlUtil {

	private static String getXmlAttribute(Context context, XmlResourceParser xml, String name) {
		int resId = xml.getAttributeResourceValue(null, name, 0);
		if (resId == 0) {
			return xml.getAttributeValue(null, name);
		} else {
			return context.getString(resId);
		}
	}

	public static ConfigInfo getConfigInfo(Context context) {
		ConfigInfo result = new ConfigInfo();
		try {
			XmlResourceParser xml = context.getResources().getXml(R.xml.providers);
			int xmlEventType;
			while ((xmlEventType = xml.next()) != XmlResourceParser.END_DOCUMENT) {
				if (xmlEventType == XmlResourceParser.START_TAG && "provider".equals(xml.getName())) {
					result.setControlTel(getXmlAttribute(context, xml, "controltel"));
					result.setAppPwd(getXmlAttribute(context, xml, "apppwd"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

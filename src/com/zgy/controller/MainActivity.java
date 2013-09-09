package com.zgy.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.zgy.controller.pwdview.NinePointLineView;
import com.zgy.controller.pwdview.PwdViewListener;

/**
 * the main activity, only the right password can jump into the control activity.
 * 
 * @Description:
 * @author:zhuanggy
 * @see:
 * @since:
 * @copyright © 35.com
 * @Date:2013-5-31
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);

		LinearLayout layout = new LinearLayout(MainActivity.this);
		LayoutParams lp = new LayoutParams(dMetrics.widthPixels, dMetrics.widthPixels);
		layout.setLayoutParams(lp);
		NinePointLineView v = new NinePointLineView(MainActivity.this);
		v.setBackgroundColor(0xFF9CC2D5);
		v.setLayoutParams(lp);
		layout.addView(v);
		setContentView(layout);

		v.setPwdViewListener(new PwdViewListener() {

			@Override
			public void afterInput(String pwd) {
				Log.e("afterInput", "pwd=" + pwd);
				if (pwd.equals(GlobleCs.appPwd)) {
					Log.e("afterInput", "Success!!!");
					GlobleCs.is_open = true;
					startActivity(new Intent(MainActivity.this, CtrlActivity.class));
					finish();
				} else {
					// finish();
				}
			}
		});
	}

}

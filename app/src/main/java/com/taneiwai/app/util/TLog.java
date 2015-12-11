package com.taneiwai.app.util;

import android.util.Log;

/**
 * 日志工具类
 * @author weiTeng
 */
public class TLog {
	public static final String LOG_TAG = "Taneiwai";
	public static boolean DEBUG = false;

	public TLog() {
	}


	public static final void i(String tag, String log) {
		if (DEBUG)
			Log.i(tag, log);
	}
	public static final void d(String tag, String log) {
		if (DEBUG)
			Log.d(tag, log);
	}
	
	public static final void d(String tag, String log,Throwable throwable) {
		if (DEBUG)
			Log.d(tag, log, throwable);
	}

	public static final void e(String tag, String log) {
		if (DEBUG)
			Log.e(tag, "" + log);
	}
	
	public static final void e(String tag, String log, Throwable throwable) {
		if (DEBUG)
			Log.e(tag, "" + log, throwable);
	}

	public static final void analytics(String log){
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}

	public static final void logv(String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
	}

	public static final void warn(String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}
}

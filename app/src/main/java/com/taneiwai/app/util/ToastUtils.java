package com.taneiwai.app.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * @author weiTeng
 *
 */
public class ToastUtils {

	public static Toast mToast;
	
	/**
	 * 只显示一条Toast信息
	 * @param context
	 * @param showText
	 */
	public static void showToast(Context context, String showText){
		
		/*
		if(mToast==null){
			mToast = new Toast(context);
		}
		mToast.setText(showText);
		mToast.show();
		*/
		Toast.makeText(context, showText, Toast.LENGTH_SHORT).show();		// 由于以上代码在5.1的系统中会出现闪退
	}
	
	public static void show(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getText(resId), duration);
	}

	public static void show(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, CharSequence text, int duration) {
		Toast.makeText(context, text, duration).show();
	}

	public static void show(Context context, int resId, Object... args) {
		show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration, Object... args) {
		show(context, String.format(context.getResources().getString(resId), args), duration);
	}

	public static void show(Context context, String format, int duration, Object... args) {
		show(context, String.format(format, args), duration);
	}
}

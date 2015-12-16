package com.taneiwai.app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.taneiwai.app.R;

/**
 * 加载动画对话框
 */
class SimpleHUDDialog extends Dialog {

	public SimpleHUDDialog(Context context, int theme) {
		super(context, theme);
	}

	public SimpleHUDDialog(Context context, int theme, OnCancelListener cancelListener) {
		super(context, theme);
		this.setOnCancelListener(cancelListener);
	}
	
	public static SimpleHUDDialog createDialog(Context context) {
		SimpleHUDDialog dialog = new SimpleHUDDialog(context, R.style.SimpleHUD);
		dialog.setContentView(R.layout.simplehud);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return dialog;
	}

	public static SimpleHUDDialog createDialog(Context context, OnCancelListener cancelListener) {
		SimpleHUDDialog dialog = new SimpleHUDDialog(context, R.style.SimpleHUD, cancelListener);
		dialog.setContentView(R.layout.simplehud);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return dialog;
	}

	public void setMessage(String message) {
		TextView msgView = (TextView)findViewById(R.id.simplehud_message);
		msgView.setText(message);
	}
	
	public void setImage(Context ctx, int resId) {
		ImageView image = (ImageView)findViewById(R.id.simplehud_image);
		
		if(resId==R.drawable.simplehud_spinner) {
			image.setBackgroundResource(R.drawable.loading_anim);
			AnimationDrawable animDrawable = (AnimationDrawable) image.getBackground();
			animDrawable.start();
		}else{
			image.setImageResource(resId);
		}
	}
}

package com.taneiwai.app.adapter;

import android.os.Bundle;

public final class ViewPageInfo {

	public final String tag;
    public final Class<?> clss;
    public final Bundle args;
    public final String title;

    public ViewPageInfo(String title, String tag, Class<?> clazz, Bundle args) {
    	this.title = title;
        this.tag = tag;
        this.clss = clazz;
        this.args = args;
    }
}
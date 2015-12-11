package com.taneiwai.app.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taneiwai.app.R;
import com.taneiwai.app.util.NetworkUtils;


/**
 * 空页面和错误提示页面
 * @author weiTeng on 2015/10/30.
 * @version v1.3.1
 */
public class EmptyLayout extends LinearLayout implements android.view.View.OnClickListener {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;

    private boolean clickEnable = true;
    private final Context context;
    public ImageView img;
    private android.view.View.OnClickListener listener;
    private int mErrorState;
    private String strNoDataContent;
    private TextView mMessageTv;

    public EmptyLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflate(context, R.layout.view_error_layout, this);
        img = (ImageView) findViewById(R.id.img_error_layout);
        mMessageTv = (TextView) findViewById(R.id.tv_error_tv);
        setBackgroundColor(-1);
        setOnClickListener(this);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
    }

    public void dismiss() {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return mErrorState;
    }

    public boolean isLoadError() {
        return mErrorState == NETWORK_ERROR;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    public void setErrorMessage(String msg) {
        strNoDataContent = msg;
        mMessageTv.setText(msg);
    }

    /**
     * 新添错误的背景图标提示
     */
    public void setErrorImag(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                if (NetworkUtils.isNetworkConnected(context)) {
                    mMessageTv.setText(R.string.error_view_load_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.page_icon_empty);
                } else {
                    mMessageTv.setText(R.string.error_view_network_error_click_to_refresh);
                    img.setBackgroundResource(R.mipmap.page_icon_network);
                }
                img.setVisibility(View.VISIBLE);
                clickEnable = true;
                break;

            case NODATA:
                mErrorState = NODATA;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                setTvNoDataContent();
                clickEnable = true;
                break;

            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;

            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setOnLayoutClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (!TextUtils.isEmpty(strNoDataContent))
            mMessageTv.setText(strNoDataContent);
        else
            mMessageTv.setText(R.string.error_view_no_data);
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}

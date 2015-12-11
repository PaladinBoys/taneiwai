package com.taneiwai.app.interf;

import android.os.Bundle;
import android.view.View;

/**
 * 基类Fragment接口
 * @author weiTeng
 * @since 2015-12-06 22:14:09
 * @version v1.0.0
 */
public interface BaseFragmentInterface {

    int getLayoutId();

    void initView(View view);

    void initData(Bundle savedInstanceState);

    void reload();

    void release();
}

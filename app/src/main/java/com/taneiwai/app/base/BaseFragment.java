package com.taneiwai.app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taneiwai.app.cache.ACache;
import com.taneiwai.app.dialog.SimpleHUD;
import com.taneiwai.app.interf.BaseFragmentInterface;
import com.taneiwai.app.util.NetworkUtils;
import com.taneiwai.app.util.ToastUtils;

import org.json.JSONObject;

/**
 * Created by weiTeng on 15/12/6.
 */
public abstract class BaseFragment extends Fragment implements BaseFragmentInterface{

    protected Activity mActivity;
    private boolean isLoad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            reload();
        }
    }

    @Override
    public void reload() {

    }

    @Override
    public void release() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }

    protected void cacheJson(String key, JSONObject jsonObject){
        ACache cache = ACache.get(mActivity);
        cache.put(key, jsonObject);
    }

    protected JSONObject getJsonCache(String key){
        return ACache.get(mActivity).getAsJSONObject(key);
    }


    public void showLoading(String msg){
        SimpleHUD.showLoadingMessage(mActivity, msg, true);
    }

    public void showLoading(String msg, boolean canceled){
        SimpleHUD.showLoadingMessage(mActivity, msg, canceled);
    }


    public void hudDismiss(){
        SimpleHUD.dismiss();
    }

    public boolean hasNetWork() {
        return NetworkUtils.isNetworkConnected(mActivity);
    }

    public void showShortToast(String msg){
        ToastUtils.show(mActivity, msg);
    }
}

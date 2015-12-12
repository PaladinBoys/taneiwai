package com.taneiwai.app.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.taneiwai.app.R;
import com.taneiwai.app.application.AppManager;
import com.taneiwai.app.cache.ACache;
import com.taneiwai.app.dialog.SimpleHUD;
import com.taneiwai.app.interf.BaseViewInterface;
import com.taneiwai.app.util.NetworkUtils;
import com.taneiwai.app.util.ToastUtils;

import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * Created by weiTeng on 15/12/12.
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener, BaseViewInterface{

    protected AppManager mAppManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppManager = AppManager.getAppManager();
        mAppManager.addActivity(this);
        if(getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mAppManager.finishActivity();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    protected int getLayoutId(){
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    public void loadingHud(){
        loadingHud("正在加载");
    }

    public void loadingHud(String msg){
        SimpleHUD.showLoadingMessage(this, msg, true);
    }

    public void showErrorMessage(String errorMsg){
        SimpleHUD.showErrorMessage(this, errorMsg);
    }

    public void showSuccessMessage(String successMsg){
        SimpleHUD.showSuccessMessage(this, successMsg);
    }

    public void showInfoMessage(String info){
        SimpleHUD.showInfoMessage(this, info);
    }

    public void hudDismiss(){
        SimpleHUD.dismiss();
    }

    public void showShortToast(String msg) {
        ToastUtils.show(this, msg);
    }


    public boolean hasNetWork() {
        return NetworkUtils.isNetworkConnected(this);
    }

    public void setCacheJSONObject(String key, JSONObject value) {
        if (value != null) {
            ACache.get(this).put(key, value);
        }
    }

    public JSONObject getCacheJSONObject(String key) {
        return ACache.get(this).getAsJSONObject(key);
    }

    public void removeCache(String key){
        ACache.get(this).remove(key);
    }
}

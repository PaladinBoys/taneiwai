package com.taneiwai.app.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.taneiwai.app.R;
import com.taneiwai.app.application.AppManager;
import com.taneiwai.app.cache.ACache;
import com.taneiwai.app.constant.ConstantValues;
import com.taneiwai.app.dialog.SimpleHUD;
import com.taneiwai.app.interf.BaseViewInterface;
import com.taneiwai.app.util.NetworkUtils;
import com.taneiwai.app.util.ToastUtils;

import org.json.JSONObject;

import butterknife.ButterKnife;

/**
 * 所有activity的基类
 * @author weiTeng
 * @since 2015-12-16 23:00:07
 */
public class BaseActivity extends FragmentActivity implements View.OnClickListener, BaseViewInterface{

    protected static final int NORMAL = 100;
    protected static final int LAYER = 101;
    protected static final int BUTTOM = 102;
    protected static final int ZOOM = 103;

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

    public void finishActivity(){
        finishActivity(NORMAL);
    }

    public void finishActivity(int type){
        mAppManager.finishActivity();
        exitAnimationType(type);
    }

    public void exitAnimationType(int type){
        if(type == LAYER){
            overridePendingTransition(R.anim.push_left_static_out, R.anim.push_right_out);
        }else if(type == ZOOM){
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if(type == BUTTOM){
            overridePendingTransition(R.anim.push_bottom_out_static, R.anim.push_right_out);
        }else{
            overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
        }
    }

    public void enterAnimation(){
        enterAnimation(NORMAL);
    }

    public void enterAnimation(int type){
        enterAnimationType(type);
    }

    public void enterAnimationType(int type){
        if(type == LAYER){
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_static_out);
        }else if(type == ZOOM){
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        }else if(type == BUTTOM){
            overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out_static);
        }else{
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
    }

    public void startNewActivity(Class<Activity> clazz){
        startNewActivity(clazz, null, null);
    }

    public void startNewActivity(Class<Activity> clazz, Bundle bundle, String action){
        startNewActivity(clazz, bundle, action, NORMAL);
    }

    public void startNewActivity(Class<Activity> clazz, Bundle bundle, String action, int animType){
        Intent intent = new Intent(this, clazz);
        if(bundle != null)
            intent.putExtra(ConstantValues.DATA, bundle);
        if(!TextUtils.isEmpty(action))
            intent.setAction(action);
        startActivity(intent);
        enterAnimation(animType);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finishActivity();
            return true;
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

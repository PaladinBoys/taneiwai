package com.taneiwai.app.interf;

import org.json.JSONObject;

/**
 * Created by weiTeng on 15/12/6.
 */
public interface THttpJsonResult {
    void onFaile(int stateCode, JSONObject json, Throwable error);

    void success(int stateCode, JSONObject json);
}

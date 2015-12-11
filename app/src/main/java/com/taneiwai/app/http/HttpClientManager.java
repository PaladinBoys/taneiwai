package com.taneiwai.app.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by weiTeng on 15/12/6.
 */
public class HttpClientManager {

    private static HttpClientManager sClientManager;
    private static AsyncHttpClient client;
    private static ThreadPoolExecutor cacheThreadPool;

    static {

        client = new AsyncHttpClient();
        cacheThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        client.setThreadPool(cacheThreadPool);		// 设置线程池中线程数不能超过5
        client.setTimeout(60000);					// 请求超时60秒
    }

    private HttpClientManager(){}

    public static HttpClientManager getInstance(){
        if(sClientManager == null){
            synchronized (HttpClientManager.class){
                if(sClientManager == null){
                    sClientManager = new HttpClientManager();
                }
            }
        }
        return sClientManager;
    }


    /**
     * post提交数据
     * @param context   上下文
     * @param url       地址
     * @param json		json格式参数
     * @param asyHandle 回调对象
     */
    public static void post(Context context, String url, JSONObject json, AsyncHttpResponseHandler asyHandle) {

        RequestParams params = new RequestParams();

        for(@SuppressWarnings("unchecked")
            Iterator<String> iterator = json.keys(); iterator.hasNext();){

            String name = iterator.next();
            Object value = json.opt(name);
            params.put(name, value.toString());
        }
        client.post(context, url, params, asyHandle);
    }

    /**
     * get提交数据（没有参数的)
     * @param context
     * @param url
     * @param asyHandle
     */
    public static void get(Context context, String url, AsyncHttpResponseHandler asyHandle){
        client.get(context, url, asyHandle);
    }


    /**
     * get提交数据
     * @param context
     * @param url
     * @param json		json格式参数
     * @param asyHandle
     */
    public static void get(Context context, String url, JSONObject json, AsyncHttpResponseHandler asyHandle){

        RequestParams params = new RequestParams();

        for(@SuppressWarnings("unchecked")
            Iterator<String> iterator = json.keys(); iterator.hasNext();){

            String name = iterator.next();
            Object value = json.opt(name);
            params.put(name, value.toString());
        }
        client.get(context, url, params, asyHandle);
    }
}

package com.taneiwai.app.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 整个应用级别的activity堆栈式管理
 *
 * @author weiTeng
 */
public class AppManager {

	private static final String TAG = "AppManager";

    private static AppManager instance;
    
    // 记录Activity
 	private static Map<String, Activity> activitys;
 	
 	private static LinkedList<String> tagLists;


	/** 是否是远程的打开 */
	private boolean mIsPending;

    private AppManager() {}

	public boolean ismIsPending() {
		return mIsPending;
	}

	public void setmIsPending(boolean mIsPending) {
		this.mIsPending = mIsPending;
	}

	/**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

	public synchronized void addActivity(Activity activity) {
		if(tagLists == null){
			tagLists = new LinkedList<String>();
		}
		if (activitys == null) {
			activitys = new HashMap<String, Activity>();
		}
		String tag = activity.getClass().getSimpleName();
		if(tagLists.contains(tag)){
			tag = tag + UUID.randomUUID().toString();	// 避免相同键覆盖
			tagLists.add(tag);
			activitys.put(tag, activity);
		}else {
			tagLists.add(tag);
			activitys.put(tag, activity);
		}
	}
	
	public Activity getActivity(Class<?> cls){
		if(activitys!=null && activitys.size()>0){
			return activitys.get(cls.getSimpleName());
		}else{
			return null;
		}
	}

	public void finishActivity() {
		if (activitys.size() > 0 && tagLists.size()>0 && tagLists.size() == activitys.size()) {
			finishActivity(tagLists.getLast());
		}
	}
	
	public void finishActivity(String tag) {
		try {
			if (activitys.size() > 0) {
				Activity activity = activitys.remove(tag);
				if(activity!=null){
					tagLists.remove(tag);
					activity.finish();
					activity = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finishActivity(Class<?> cls){
		finishActivity(cls.getSimpleName());
	}
	
	public void finishAllActivityExcept(Class<?> cls){
		finishAllActivityExcept(cls.getSimpleName());
	}
	
	public void finishAllActivityExcept(String activtyTag){
		ArrayList<String> tags = getActivityTags();
		for(String tag : tags){
			if(tag.equals(activtyTag)){
				continue;
			}else{
				finishActivity(tag);
			}
		}
	}
	
	/**
	 * 关闭所有的activity
	 */
	public void finishAllActivity() {
		if (activitys.size() > 0) {
			ArrayList<String> tags = getActivityTags();
			for(String tag : tags){
				finishActivity(tag);
			}
		}
	}
	
	/**
	 * 返回所有的activity的tag
	 */
	private ArrayList<String> getActivityTags(){
		Set<String> tags = activitys.keySet();
		ArrayList<String> tagList = new ArrayList<String>();
		tagList.addAll(tags);
		return tagList;
	}

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
        	finishAllActivity();
            // 杀掉该应用进程
            // android.os.Process.killProcess(android.os.Process.myPid());
            // System.exit(0);
        } catch (Exception e) {
			e.printStackTrace();
        }
    }
}
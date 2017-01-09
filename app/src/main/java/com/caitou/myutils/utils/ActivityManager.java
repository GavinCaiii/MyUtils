package com.caitou.myutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Stack;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * @className : ActivityManager.java
 * @classDescription : Activity管理类
 * @author : Guangzhao Cai
 * @createTime : 2017-01-06
 *
 */
public class ActivityManager {
	
    private static ActivityManager instance;
    
    private ActivityManager(){}
    
	private static Stack<Activity> activityStack;
	
	/**
	 * 单一实例
	 */
	public static ActivityManager getInstance(){
		if(instance==null){
			instance=new ActivityManager();
		}
		return instance;
	}
	
	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity){
		if(activityStack==null){
			activityStack=new Stack<>();
		}
		activityStack.add(activity);
	}
	
	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity(){
		Activity activity = null;
		try{		
			if(activityStack.size()>0){
				activity=activityStack.lastElement();				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return activity;
	}
	
	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(){
		try{
			Activity activity=activityStack.lastElement();
			finishActivity(activity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity){
		try{
			if(activity!=null){
				activityStack.remove(activity);
				activity.finish();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void removeCurrentActivity() {
		if (activityStack == null || activityStack.isEmpty())
			return;
		activityStack.remove(activityStack.lastElement());
	}

	/**
	 * 结束所有Activity,除了当前Activity
	 * @param activityCls
	 */
	public void finishAllActivityExceptOne(Class activityCls) {
		for (int i=0; i<activityCounts(); i++) {
			Activity activity = activityStack.get(i);
			if (!activity.getClass().equals(activityCls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity,除了当前Activity
	 */
	public void finishAllActivityExceptCurrent() {
		Activity currentActivity = currentActivity();
		finishAllActivityExceptOne(currentActivity.getClass());
	}
	
	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls){
		try{
			for (Activity activity : activityStack) {
				if(activity.getClass().equals(cls) ){
					finishActivity(activity);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(){
        try {
            for (int i = 0; i < activityStack.size(); i++) {
				if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }catch (Exception e){e.printStackTrace();}
	} 
	
	/**
     * 拿到应用程序当前活跃Activity的个数
     * @return counts
     */
    public int activityCounts(){
    	int counts = 0 ;
    	if(activityStack !=null && activityStack.size()>0){
    		counts = activityStack.size();
    	}
    	return counts ;
    }
    
    /**
     * 退出应用程序
     */
    public void exit(){
    	try{
//			ImageLoader.getInstance().clearDiskCache();
//			ImageLoader.getInstance().clearMemoryCache();
//			if (currentActivity() != null)
//				UpdateUtil.getUpdateUtil().autoClear();
//				UpdateUtil.getUpdateUtil().unregisterReceiver(currentActivity());
	    	finishAllActivity();
			System.exit(0);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

	/**
	 * 判断某一个activity是否为当前activity
	 * @author swallow
	 * @createTime 2015/8/18
	 * @lastModify 2015/8/18
	 * @param activity
	 * @return
	 */
	public boolean isCurrent(Activity activity){
		if (activity == null || currentActivity() == null)
			return false;
		if (activity == currentActivity())
			return true;
		else
			return false;
	}

	/**
	 * 判断某一个activityCls是否为当前activity
	 * @author leibing
	 * @createTime 2016/6/7
	 * @lastModify 2016/6/7
	 * @param activityCls
	 * @return
	 */
	public boolean isCurrent(Class activityCls){
		if (currentActivity() == null)
			return false;
		Class currentActivityCls = currentActivity().getClass();
		if (currentActivityCls.equals(activityCls))
			return true;
		else
			return false;
	}

	/**
	 * 是否存在此activity
	 * @author leibing
	 * @createTime 2016/6/7
	 * @lastModify 2016/6/7
	 * @param activityCls
	 * @return
	 */
	public boolean isHasActivity(Class activityCls){
		if (activityStack == null || activityStack.size() == 0)
			return false;
		try {
			for (int i = 0; i < activityStack.size(); i++) {
				if (null != activityStack.get(i)) {
					if (activityCls.equals(activityStack.get(i).getClass()))
						return true;
				}
			}
		}catch (Exception e){e.printStackTrace();}

		return false;
	}

	/**
	 * 启动新的Activity
	 * @param context
	 * @param classic
     */
	public void launchActivity(Context context, Class<? extends Activity> classic){
		Intent intent = new Intent();
		intent.setClass(context, classic);
		intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 启动新的activity，并销毁当前activity
	 */
	public void turnTo(Class<?> cls, Bundle bundle) {
		Activity curActivity = currentActivity();

		Intent intent = new Intent(curActivity, cls);
		if (bundle != null)
			intent.putExtras(bundle);
		curActivity.startActivity(intent);
		finishActivity(curActivity);
	}
}

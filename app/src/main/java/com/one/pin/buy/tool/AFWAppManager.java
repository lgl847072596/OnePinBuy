package com.one.pin.buy.tool;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AFWAppManager
{
	private static Stack<Activity> activityStack;
	private static AFWAppManager instance;

	private AFWAppManager()
	{

	}

	/**
	 * 单一实例
	 */
	public static AFWAppManager getAppManager()
	{
		if (instance == null)
		{
			instance = new AFWAppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity)
	{
		if (activityStack == null)
		{
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中最后一个压入的）
	 */
	public Activity currentActivity()
	{
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity()
	{
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 移除指定的Activity
	 */
	public void removeActivity(Activity activity)
	{
		if (activity != null)
		{
			activityStack.remove(activity);
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls)
	{
		for (Activity activity : activityStack)
		{
			if (activity.getClass().equals(cls))
			{
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity()
	{
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			if (null != activityStack.get(i))
			{
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	

	/**
	 * @description 只保留最后一个activity
	 */
	public Activity finishBeforeTopActivity(){
		Activity current=activityStack.lastElement();
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			Activity activity=activityStack.get(i);
			if (null != activity&& activity!=current)
			{
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
		activityStack.add(current);
		return current;
	}

	public Stack<Activity>getStack(){
		return activityStack;
	}
	
	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity(Activity current)
	{
		for (int i = 0, size = activityStack.size(); i < size; i++)
		{
			if (null != activityStack.get(i)&& activityStack.get(i)!=current)
			{
				activityStack.get(i).finish();
			}
		}
		current.finish();
		activityStack.clear();
	}
	

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 *            上下文
	 * @param isBackground
	 *            是否开开启后台运行
	 */
	public void AppExit(Context context, Boolean isBackground)
	{
		try
		{
			Activity activity=finishBeforeTopActivity();
			if(activity!=null){
				activity.finish();
			}
			activityStack.clear();
			
			ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
		} catch (Exception e)
		{

		} finally
		{
			// 注意，如果您有后台程序运行，请不要支持此句子
			if (!isBackground)
			{
				System.exit(0);
			}
		}
	}
}
package com.one.pin.buy.tool;

import java.util.Map;
import java.util.Set;


import com.ylzinfo.android.inject.ResourceHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/**
 * @description: Intent工具类
 * @date: 2014-12-12
 * @author: Liu Guilin
 */
public class IntentUtil {
	protected final static ResourceHelper resHelper=ResourceHelper.getInstance();
	/**
	 * 开始动画
	 * @param activity
	 */
	private static void startAnima(Activity activity) {
		activity.overridePendingTransition(resHelper.getResourceId(ResourceHelper.mAnim, "in_from_right"),resHelper.getResourceId(ResourceHelper.mAnim, "out_to_left"));                 
	}

	/**
	 * @description 结束activity并从左到右播放动画
	 * @param activity
	 */
	public static void finishActivity(Activity context){
		context.finish();
		context.overridePendingTransition(resHelper.getResourceId(ResourceHelper.mAnim, "in_from_left"),resHelper.getResourceId(ResourceHelper.mAnim, "out_to_right"));

	}


	public static void startActivity(Activity activity,Class<?> cls,Map<String,Object>map){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		if(map!=null){
			Set<String>set=map.keySet();
			for (String str : set) {
				if(str!=null&&map.get(str)!=null){
				intent.putExtra(str,map.get(str).toString());
				}
			}
		}		
		activity.startActivity(intent);
		startAnima(activity);
		
	}

	
	public static void startActivityWithFinish(Activity activity,Class<?> cls,Map<String,Object>map){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		if(map!=null){
			Set<String>set=map.keySet();
			for (String str : set) {
				if(str!=null&&map.get(str)!=null){
					intent.putExtra(str,map.get(str).toString());
					}
			}
		}	
		activity.startActivity(intent);
		startAnima(activity);
		activity.finish();
	}
	

	public static void startActivityWithFinishAndTop(Activity activity,Class<?> cls,Map<String,Object>map){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		if(map!=null){
			Set<String>set=map.keySet();
			for (String str : set) {
				if(str!=null&&map.get(str)!=null){
					intent.putExtra(str,map.get(str).toString());
					}
			}
		}	
		AFWAppManager.getAppManager().finishBeforeTopActivity();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
		startAnima(activity);
		activity.finish();
	}

	
	public static void startActivityForResult(Activity activity,Class<?> cls,int requestCode,Map<String,Object>map){
		Intent intent=new Intent();
		intent.setClass(activity,cls);
		if(map!=null){
			Set<String>set=map.keySet();
			for (String str : set) {
				if(str!=null&&map.get(str)!=null){
					intent.putExtra(str,map.get(str).toString());
				}
			}
		}
		activity.startActivityForResult(intent,requestCode);
		startAnima(activity);
	}

	

}

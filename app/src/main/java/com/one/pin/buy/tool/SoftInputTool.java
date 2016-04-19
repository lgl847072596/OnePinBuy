package com.one.pin.buy.tool;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputTool {

	public static  boolean isShouldHideKeyboard(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] l = { 0, 0 };
			v.getLocationInWindow(l);
			int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击EditText的事件，忽略它。
				return false;
			} else {
				return true;
			}
		}
		// 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
		return false;
	}
	
	public static void hidSoftInputByView(Context context,View view){
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputMethodManager != null) {
			IBinder localIBinder = view.getWindowToken();
			if (localIBinder != null)
				inputMethodManager.hideSoftInputFromWindow(localIBinder, 0);
		}
	}
	
	public static void hideSoftKeyboard(Activity  context) {
		View localView = context.getCurrentFocus();
		if (localView != null && localView.getWindowToken() != null) {
			hidSoftInputByView(context,localView);
		}
}
	
}

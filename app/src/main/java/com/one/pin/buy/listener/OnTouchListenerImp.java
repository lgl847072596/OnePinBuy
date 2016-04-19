package com.one.pin.buy.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class OnTouchListenerImp implements OnTouchListener {
	
	private CallBackInterface<View>mCallBack=null;
	public OnTouchListenerImp(View view,CallBackInterface<View>callBack) {
		
		mCallBack=callBack;
		view.setClickable(true);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCallBack!=null)mCallBack.callBack(v);
			}
		});
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 更改为按下时的背景图片
			v.setAlpha(0.5f);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			// 改为抬起时的图片
			v.setAlpha(1.0f);
		}
		//在scrollview中监听不到up事件
		else if(event.getAction()==MotionEvent.ACTION_CANCEL){
			// 改为抬起时的图片
			v.setAlpha(1.0f);
		}
		return false;
	}

}

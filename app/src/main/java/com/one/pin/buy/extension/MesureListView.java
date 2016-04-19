package com.one.pin.buy.extension;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @description 自动计算listview的高度
 * 若刷新前后位置保持不变则需要在刷新前设置
 * 刷新前listView.setFocusable(false);
 * 刷新后listView.setFocusable(true);
 *
 */
public class MesureListView extends ListView {

	public MesureListView(Context context) {
		super(context);
	}
	 public MesureListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MesureListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/** 
     * 设置不滚动 
     */  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                            MeasureSpec.AT_MOST);  
            super.onMeasure(widthMeasureSpec, expandSpec);  

    }



}

package com.one.pin.buy.tool;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.StateListDrawable;
/**
 * @description 渐变工具类，边框工具
 * @author lgl
 *
 */
public class GradientDrawableToolKit {
	//蓝色渐变
	public final static String[]BLUE_GRADIENT={"#04b2e6","#0192dd"};
	//黄色渐变
	public final static String[]YELLOW_GRADIENT={"#efc113","#e99d14"};
	//紫色渐变
	public final static String[]PURPLE_GRADIENT={"#b788d8","#8d61ae"};
	//橙色渐变
	public final static String[]ORANGE_GRADIENT={"#ef8513","#e96e14"};
	//绿色渐变
	public final static String[]GREEN_GRADIENT={"#aedb39","#77b516"};

	/**
	 * @description 生成渐变色
	 * @param orientation 渐变方向
	 * @param array 颜色数组
	 * @return
	 */
	public static  GradientDrawable  getGradientDrawable(Orientation orientation, String[]array) {
		int []tempArray=new int[array.length];
		for (int i = 0; i < tempArray.length; i++) {
			tempArray[i]=Color.parseColor(array[i]);
		}
		GradientDrawable drawable = new GradientDrawable(orientation,	tempArray);
		drawable.setGradientType(GradientDrawable.RECTANGLE);
		return drawable;
	}
	/**
	 * @description 生成直角或圆角边框 
	 * @param fillColor 填充颜色
	 * @param borderColor 边框颜色
	 * @param radii 圆角度数
	 * @return
	 */
	public static GradientDrawable getBorderGradientDrawable(String fillColor,String borderColor,int radii){
		return getBorderGradientDrawable(fillColor,borderColor,1,radii);
	}
	
	public static GradientDrawable getBorderGradientDrawable(String fillColor,String borderColor,int border,int radii){
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.parseColor(fillColor));
		drawable.setCornerRadius(radii);
		drawable.setStroke(border, Color.parseColor(borderColor));
		drawable.setShape(GradientDrawable.RECTANGLE);
		return drawable;
	}
	
	public static GradientDrawable getBorderGradientDrawable(String fillColor,String borderColor,int border,float[] radii){
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.parseColor(fillColor));
		drawable.setCornerRadii(radii);
		drawable.setStroke(border, Color.parseColor(borderColor));
		drawable.setShape(GradientDrawable.RECTANGLE);
		return drawable;
	}
	
	public static GradientDrawable getCycleGradientDrawable(String fillColor,String borderColor,int radii){
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(Color.parseColor(fillColor));
		drawable.setCornerRadius(radii);
		drawable.setShape(GradientDrawable.OVAL);
		return drawable;
	}
	
    /** 设置Selector。 */  
    public static StateListDrawable newBackgroundSelector(Context context, int idNormal, int idPressed, int idFocused,  
            int idUnable) {  
        StateListDrawable bg = new StateListDrawable();  
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);  
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);  
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);  
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);  
        // View.PRESSED_ENABLED_STATE_SET  
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);  
        // View.ENABLED_FOCUSED_STATE_SET  
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);  
        // View.ENABLED_STATE_SET  
        bg.addState(new int[] { android.R.attr.state_enabled }, normal);  
        // View.FOCUSED_STATE_SET  
        bg.addState(new int[] { android.R.attr.state_focused }, focused);  
        // View.WINDOW_FOCUSED_STATE_SET  
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);  
        // View.EMPTY_STATE_SET  
        bg.addState(new int[] {}, normal);  
        return bg;  
    }  
  
	
	
}

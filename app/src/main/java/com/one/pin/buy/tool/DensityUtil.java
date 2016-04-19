package com.one.pin.buy.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera.Size;
import android.graphics.Paint.FontMetricsInt;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DensityUtil {
	
	public final static float density=2.0f;

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取手机分辨率
	 */
	public static Point getDisplayPoint(Context context) {
		Point point = new Point();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		point.x = metrics.widthPixels;
		point.y = metrics.heightPixels;
		return point;
	}
	
	public static float getDensity(Context context){
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return metrics.density;
	}
	/**
	 * @description 获取长度
	 * @param context
	 * @param width
	 * @return
	 */
	public static int getWidth(Context  context,double width){
		double scale=width/720.0f;
		return (int)(getDisplayPoint(context).x*scale);
	}
	/**
	 * @description 获取高度
	 * @param context
	 * @param height
	 * @return
	 */
	public static int getHeight(Context  context,double height){
		double scale=height/1236.0f;
		return (int)((getDisplayPoint(context).y-getStatusHeight(context))*scale);
	}

	/**
	 * 放大bitmap，最大的宽高(x,y)
	 * @param bitmap
	 * @param x
	 * @param y
	 * @return
	 */
	public static Bitmap imageBigger(Bitmap bitmap, float x, float y) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		float sx = (float) x / w;// 要强制转换，不转换我的在这总是死掉。
		float sy = (float) y / h;
		Matrix matrix = new Matrix();
		float scale=sx>sy?sy:sx;
		matrix.postScale(scale, scale); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap
				.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return resizeBmp;
	}
	
	/**
	 * @description 获取状态栏高度
	 * @param context
	 * @return
	 */
	 public static int getStatusHeight(Context context){
		 int statusHeight = -1;
	        try {
	            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
	            Object object = clazz.newInstance();
	            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
	            statusHeight = context.getResources().getDimensionPixelSize(height);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	        	 return statusHeight;
	        }
	       
	    }
	 /**
	  * @description 计算控件的宽高
	  * @param view
	  * @return
	  */
	 public static Point getViewWH(View view){
		 int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
		 int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
				 View.MeasureSpec.AT_MOST);
		 view.measure(width, expandSpec);  
		 
		 Point point=new Point(view.getMeasuredWidth(),view.getMeasuredHeight());
		 return point;
	 }
	 

		/**
		 * @description 根据屏幕大小获得字体大小
		 * @param cxt
		 * @param size
		 * @return
		 */
		public static int getFontSize(Context cxt,int size){
			return Math.round(size * (getDensity(cxt)/density));
		}
		 /**  
	     * @return 返回指定笔和指定字符串的长度  
	     */  
	    public static float getFontlength(Paint paint, String str) {  
	        return paint.measureText(str);  
	    }  
	    /**  
	     * @return 返回指定笔的文字高度  
	     */  
	    public static float getFontHeight(Paint paint)  {    
	    	FontMetricsInt fontMetrics = paint.getFontMetricsInt(); 
	        return fontMetrics.bottom-fontMetrics.top;    
	    }
	    
		/**
		 * @description 获取view的屏幕中位置宽高
		 * @param view
		 * @return
		 */
		public static Rect getViewRect(View view){
			
			Point point=getViewWH(view);
			 int[] location = new int[2];
			 view.getLocationOnScreen(location);
			 int x = location[0];
			 int y = location[1];

			return new Rect(x,y,point.x,point.y);
		}
	     
}

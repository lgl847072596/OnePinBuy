package com.one.pin.buy.extension;

import com.one.pin.buy.listener.CallBackInterface;
import com.one.pin.buy.listener.OnTouchListenerImp;
import com.one.pin.buy.tool.DensityUtil;
import com.one.pin.buy.tool.GradientDrawableToolKit;
import com.ylzinfo.android.inject.ResourceHelper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 自定义对话框（默认开启点击灰色区域关闭）
 * @author guilin
 *
 */
public class AppAlertDialog extends Dialog {
	
	protected final static ResourceHelper resourceHelper=ResourceHelper.getInstance();
	private RelativeLayout  rootLayout;
	private Context context;
	//文字内容显示
	private TextView contentTxt;
	//底部按钮显示
	private LinearLayout bottomLayout;
	
	public AppAlertDialog(Context context,String content) {
		super(context,resourceHelper.getResourceId(ResourceHelper.mStyle, "AppAlertDialog"));
		this.context=context;
		initView(content);
		setCancelable(true);
		setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams lp=getWindow().getAttributes();
		lp.width=(int)(DensityUtil.getDisplayPoint(context).x*0.8);
		lp.height=DensityUtil.getFontSize(getContext(),280);
		getWindow().setAttributes(lp);
		show();

	}
	
	
   /**
    * @description 是或不是对话框
    * @param yesLabel
    * @param noLabel
    * @param yesCallBack
    * @param noCallBack
    */
	public  void addNoOrYes(String noLabel, String yesLabel, CallBackInterface<View> noCallBack, CallBackInterface<View>yesCallBack){
		List<CallBackInterface<View>> list = new ArrayList<CallBackInterface<View>>();
		list.add(noCallBack);
		list.add(yesCallBack);
		addButton(new String[]{noLabel==null?"No":noLabel,yesLabel==null?"Yes":yesLabel}, list);
	}
	/**
	 * @description 一个按钮的对话框
	 * @param label
	 * @param callBack
	 */
	public void oneButton(String label,CallBackInterface<View>callBack){
		List<CallBackInterface<View>> list = new ArrayList<CallBackInterface<View>>();
		list.add(callBack);
		addButton(new String[]{label==null?"确定":label}, list);
	}
	
   /**
    * @description 初始化
    * @param content
    */
	private void initView(String content) {
		rootLayout=new RelativeLayout(context);
		rootLayout.setBackgroundDrawable(GradientDrawableToolKit.getBorderGradientDrawable("#ffffff", "#959595",DensityUtil.dip2px(context, 8)));
		rootLayout.setPadding(0, 15, 0, 0);
		rootLayout.setLayoutParams(new ViewGroup.LayoutParams((int)(DensityUtil.getDisplayPoint(context).x*0.8),DensityUtil.getFontSize(getContext(),280)));
		
		bottomLayout=new LinearLayout(context);
//		bottomLayout.setId(10001);
		RelativeLayout.LayoutParams bottomLP=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		bottomLP.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bottomLayout.setLayoutParams(bottomLP);
		bottomLayout.setGravity(Gravity.CENTER);
		bottomLayout.setOrientation(LinearLayout.VERTICAL);
		rootLayout.addView(bottomLayout);
		
		contentTxt=new TextView(context);
		RelativeLayout.LayoutParams contentLP=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
		contentLP.addRule(RelativeLayout.ABOVE, bottomLayout.getId());
		contentTxt.setLayoutParams(contentLP);
		contentTxt.setGravity(Gravity.CENTER);
		contentTxt.setText(content);
		contentTxt.setTextSize(16);
		rootLayout.addView(contentTxt);
		setContentView(rootLayout);
	}
	/**
	 * @description 增加按钮
	 * @param choiceNames
	 */
	public void addButton(String[]choiceNames,List<CallBackInterface<View>>callBacks){
		if(choiceNames!=null){
			View line=new View(context);
			line.setBackgroundColor(Color.parseColor("#bcbcbc"));
			line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 0.3f)));
			bottomLayout.addView(line);
			
			LinearLayout layout=new LinearLayout(context);
			layout.setGravity(Gravity.CENTER);
			
		    if(choiceNames.length==1){
		    	layout.setOrientation(LinearLayout.HORIZONTAL);
		    	layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 40)));
				
		    	TextView txt=new TextView(context);
		    	txt.setTextSize(16);
		    	txt.setTextColor(Color.BLACK);
		    	txt.setText(choiceNames[0]);
		    	txt.setGravity(Gravity.CENTER);
		    	txt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		    	layout.addView(txt);
		      	CallBackInterface< View>callBack=callBacks!=null&&callBacks.size()>0?callBacks.get(0):null;
		    	layout.setOnTouchListener(new OnTouchListenerImp(layout, callBack));
		    	
		    }
			else if(choiceNames.length==2){
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 40)));
				for (int i = 0; i <choiceNames.length; i++) {
					TextView txt=new TextView(context);
			    	txt.setTextSize(16);
			    	txt.setTextColor(Color.BLACK);
			    	txt.setText(choiceNames[i]);
			    	txt.setGravity(Gravity.CENTER);
			    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
			    	lp.weight=1;
			    	txt.setLayoutParams(lp);
			    	layout.addView(txt);
			       	CallBackInterface< View>callBack=callBacks!=null&&callBacks.size()>0?callBacks.get(i):null;
			    	txt.setOnTouchListener(new OnTouchListenerImp(txt, callBack));
			    	if(i%2==0){
			    		View divideLine=new View(context);
			    		divideLine.setBackgroundColor(Color.parseColor("#bcbcbc"));
			    		divideLine.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 0.3f),LinearLayout.LayoutParams.MATCH_PARENT));
			    		layout.addView(divideLine);
			    	}
				}
				
			}else{
				layout.setOrientation(LinearLayout.VERTICAL);
				layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
				layout.setPadding(DensityUtil.dip2px(context, 10), 0, DensityUtil.dip2px(context, 10),  DensityUtil.dip2px(context, 10));
				for (int i = 0; i <choiceNames.length; i++) {
					TextView txt=new TextView(context);
			    	txt.setTextSize(16);
			    	txt.setTextColor(Color.BLACK);
			    	txt.setBackgroundDrawable(GradientDrawableToolKit.getBorderGradientDrawable("#ffffff", "#959595", 10));
			    	txt.setText(choiceNames[i]);
			    	txt.setGravity(Gravity.CENTER);
			    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context, 40));
			    	lp.setMargins(0, DensityUtil.dip2px(context, 10), 0, 0);
			    	txt.setLayoutParams(lp);
			       	CallBackInterface< View>callBack=callBacks!=null&&callBacks.size()>0?callBacks.get(i):null;
			    	txt.setOnTouchListener(new OnTouchListenerImp(txt, callBack));
			    	layout.addView(txt);
				}
				
				int height=choiceNames.length*50+10;
				
				WindowManager.LayoutParams lp=getWindow().getAttributes();
				lp.height=DensityUtil.getFontSize(getContext(),280)+DensityUtil.dip2px(context, height);
				getWindow().setAttributes(lp);
				ViewGroup.LayoutParams llp=rootLayout.getLayoutParams();
				llp.height=lp.height;
				rootLayout.setLayoutParams(llp);
			}
		    bottomLayout.addView(layout);
		}
	}
	
	

}

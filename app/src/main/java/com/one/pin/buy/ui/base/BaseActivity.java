package com.one.pin.buy.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.one.pin.buy.tool.AFWAppManager;
import com.one.pin.buy.tool.http.OKHttpTool;

/**
 * Created by liuguilin on 16/4/19.
 */
public class BaseActivity extends Activity{

    //本类的环境
    protected Activity context=this;
    //日志tag
    private String tag="";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        AFWAppManager.getAppManager().addActivity(context);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //activity停止则取消这个Activity发出的所有的请求
        OKHttpTool.cancleRequest(getTag());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AFWAppManager.getAppManager().removeActivity(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击空白区域关闭键盘
        InputMethodManager manager  = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }

    public String getTag() {
        return getClassName();
    }

    /**
     * @description 获取类名字(不含包名)
     * @return
     */
    private String getClassName(){
        String moduleName = getClass().getName();
        String[] array = moduleName.split("\\.");
        moduleName = array[array.length - 1];
        return moduleName;

    }
}

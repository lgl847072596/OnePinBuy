package com.one.pin.buy.tool;

import android.util.Log;

/**
 * 日志打印管理
 * Created by liuguilin on 16/4/19.
 */
public class LogTool {

    private static final boolean on=true;

    private LogTool(){}

    public static void v(String tag,String msg){
        if(on)Log.v(tag,msg);
    }

    public static void i(String tag,String msg){if(on)Log.i(tag,msg);}

    public static void e(String tag,String msg){
        if(on)Log.e(tag,msg);
    }

    public static void w(String tag,String msg){
        if(on)Log.w(tag,msg);
    }

}

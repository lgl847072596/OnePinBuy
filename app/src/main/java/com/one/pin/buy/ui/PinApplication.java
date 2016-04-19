package com.one.pin.buy.ui;

import android.app.Application;

import com.one.pin.buy.tool.AppContextTools;
import com.one.pin.buy.tool.AppException;
import com.one.pin.buy.tool.ShardPreferenceTool;
import com.ylzinfo.android.inject.ResourceHelper;

/**
 * Created by liuguilin on 16/4/19.
 */
public class PinApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();

    }

    /**
     * @description 初始化数据
     */
    private void init() {

        if(getPackageName().equals(AppContextTools.getCurProcessName(getApplicationContext()))){
            // 注册默认的未捕捉异常处理类
            Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
            //资源注入初始化
            ResourceHelper.init(getApplicationContext());
            //xml操作初始化
            ShardPreferenceTool.getInstance(getApplicationContext());
        }

    }
}

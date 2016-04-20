package com.one.pin.buy.tool.http;

import android.app.Activity;

import com.one.pin.buy.controller.manager.UserInfoManager;
import com.one.pin.buy.listener.HttpCallBack;
import com.one.pin.buy.tool.ErrorCode;
import com.one.pin.buy.tool.IntentUtil;
import com.one.pin.buy.tool.LogTool;
import com.one.pin.buy.ui.function.enter.LoginActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuguilin on 16/4/19.
 */
public class OKHttpTool {

    public static OkHttpClient okHttpClient=new OkHttpClient();
    static {
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * json请求
     * @param tag  //用于取消请求
     * @param url
     * @param param
     * @param callBack
     */
    public static void jsonRequest(final Activity context,final String tag, final String url, final JSONObject param, final HttpCallBack<JSONObject,String,String>callBack){
        Request.Builder builder=new Request.Builder();

        Map<String,String> header=addDefaultHeader(url);
        //头部添加
        if(header!=null){
            Set<String> keys=header.keySet();
            for(String key :keys){
                builder.addHeader(key,header.get(key));
            }
        }


        builder.url(url);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),param==null?new JSONObject().toString():param.toString());
        builder.post(body);
        if(tag!=null){
            builder.tag(tag);
        }

        Request request=builder.build();
        LogTool.i(tag+"-http请求",request.toString()+""+header+param);

        okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request,final IOException e) {
                    LogTool.e(tag+"-http请求error结果",e.getMessage()+"");
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callBack.error(e.getMessage()+"");
                            callBack.complete();
                        }
                    });

                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    final String result=new String(response.body().bytes(),"utf-8");
                    LogTool.i(tag+"-http请求结果",result);
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                JSONObject res=new JSONObject(result);
                                //请求成功,分为两种情况 1.获取的是token,2.获取的是内容
                                if(res.getBoolean("success")){
                                    //获取的是令牌
                                    if(res.has("errorCode")&& ErrorCode.error_102==res.getInt("errorCode")){
                                        String token=res.getString("token");
                                        UserInfoManager.getInstance().getLoginPO().setToken(token);
                                        UserInfoManager.getInstance().getLoginPO().setState(true);
                                        //重新请求
                                        jsonRequest(context, tag, url,  param, callBack);
                                    }else{
                                        callBack.successCall(res);
                                        callBack.complete();
                                    }
                                }
                                //令牌失效
                                else if(res.has("errorCode")&& ErrorCode.error_101==res.getInt("errorCode")){
                                    //令牌失效,需要重新请求
                                    UserInfoManager.getInstance().getLoginPO().setToken(null);
                                    UserInfoManager.getInstance().getLoginPO().setState(false);
                                    //用户存在
                                    if(UserInfoManager.getInstance().getLoginPO().getUser()!=null){
                                        String password=UserInfoManager.getInstance().getLoginPO().getUser().getPassword();
                                        String account=UserInfoManager.getInstance().getLoginPO().getUser().getAccount();
                                        //用户名密码部位空
                                        if(password!=null&&account!=null){
                                            //重新请求
                                            jsonRequest(context, tag, url,  param, callBack);
                                        }else{
                                            callBack.complete();
                                            //跳转到登录
                                            IntentUtil.startActivityWithFinishAndTop(context, LoginActivity.class,null);
                                        }
                                    }else{
                                        callBack.complete();
                                        //跳转到登录
                                        IntentUtil.startActivityWithFinishAndTop(context, LoginActivity.class,null);
                                    }

                                }else{
                                    callBack.failedCall(res.getString("message"));
                                    callBack.complete();
                                }


                            }catch (Exception e){
                                callBack.failedCall(e.getMessage());
                                callBack.complete();
                            }
                        }
                    });


                }
            });

    }

    /**
     * 文本请求
     * @param tag  //用于取消请求
     * @param url
     * @param content
     * @param callBack
     */
    public static void stringRequest(final Activity context, final String tag, int method, String url, final String content, final HttpCallBack<String,String,String>callBack){
        Request.Builder builder=new Request.Builder();
        Map<String,String> header=addDefaultHeader(url);
        //头部添加
        if(header!=null){
            Set<String> keys=header.keySet();
            for(String key :keys){
                builder.addHeader(key,header.get(key));
            }
        }
        builder.url(url);
        RequestBody body=RequestBody.create(MediaType.parse("html/text; charset=utf-8"),content==null?"":content.toString());
        if(method== 0){
            builder.get();
        }else{
            builder.post(body);
        }

        if(tag!=null){
            builder.tag(tag);
        }

        Request request=builder.build();


        LogTool.i(tag+"-http请求",request.toString()+"");

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                LogTool.e(tag+"-http请求error结果",e.getMessage()+"");
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.error(e.getMessage()+"");
                        callBack.complete();
                    }
                });


            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String result=new String(response.body().bytes(),"utf-8");
                LogTool.i(tag+"-http请求结果",result);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.successCall(result);
                        callBack.complete();
                    }
                });

            }
        });

    }


    /**
     * @description 加入默认的头部
     * @param url
     * @return
     */
    public static  Map<String,String> addDefaultHeader(String url){
        Map<String,String>header=new HashMap<String,String>();
        //私有请求需要加入请求头
        if(!url.contains("/public/")){
            if(UserInfoManager.getInstance().getLoginPO().isState()){
                header.put("token",UserInfoManager.getInstance().getLoginPO().getToken());
                header.put("account",UserInfoManager.getInstance().getLoginPO().getUser().getAccount());
            }else{
                if(UserInfoManager.getInstance().getLoginPO().getUser()!=null){
                    header.put("password",UserInfoManager.getInstance().getLoginPO().getUser().getPassword());
                    header.put("account",UserInfoManager.getInstance().getLoginPO().getUser().getAccount());
                }
            }
        }
        return header;
    }

    /**
     * @description 取消被tag标识的请求
     * @param tag
     */
    public static void cancleRequest(String tag){
        okHttpClient.cancel(tag);
    }

}

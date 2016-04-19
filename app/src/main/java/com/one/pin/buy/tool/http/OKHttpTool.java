package com.one.pin.buy.tool.http;

import android.app.Activity;

import com.one.pin.buy.listener.HttpCallBack;
import com.one.pin.buy.tool.LogTool;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by liuguilin on 16/4/19.
 */
public class OKHttpTool {

    private static OkHttpClient okHttpClient=new OkHttpClient();
    static {
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
    }

    /**
     * json请求
     * @param tag  //用于取消请求
     * @param url
     * @param param
     * @param header
     * @param callBack
     */
    public static void jsonRequest(final Activity context,final String tag, String url, JSONObject param, Map<String,String> header, final HttpCallBack<JSONObject,String,String>callBack){
        Request.Builder builder=new Request.Builder();
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
        LogTool.i(tag+"-http请求",request.toString()+"");

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
                                callBack.successCall(new JSONObject(result));
                                callBack.complete();
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
     * @param header
     * @param callBack
     */
    public static void stringRequest(final Activity context, final String tag, int method, String url, final String content, Map<String,String> header, final HttpCallBack<String,String,String>callBack){
        Request.Builder builder=new Request.Builder();
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
     * @description 取消被tag标识的请求
     * @param tag
     */
    public static void cancleRequest(String tag){
        okHttpClient.cancel(tag);
    }

}

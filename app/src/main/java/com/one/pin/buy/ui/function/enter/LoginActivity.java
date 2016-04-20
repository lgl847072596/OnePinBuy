package com.one.pin.buy.ui.function.enter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.one.pin.buy.R;
import com.one.pin.buy.config.ShareKeyConfig;
import com.one.pin.buy.config.Urls;
import com.one.pin.buy.controller.manager.UserInfoManager;
import com.one.pin.buy.listener.HttpCallBack;
import com.one.pin.buy.model.LoginPO;
import com.one.pin.buy.tool.CharacterTool;
import com.one.pin.buy.tool.IntentUtil;
import com.one.pin.buy.tool.MD5;
import com.one.pin.buy.tool.ShardPreferenceTool;
import com.one.pin.buy.tool.http.OKHttpTool;
import com.one.pin.buy.ui.base.BaseActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {



    private EditText accountInput;
    private EditText passwordInput;

    private Button loginBtn;
    private Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        accountInput=(EditText)findViewById(R.id.account);
        passwordInput=(EditText)findViewById(R.id.password);

        loginBtn=(Button)findViewById(R.id.login_btn);
        registerBtn=(Button)findViewById(R.id.register_btn);

        if(UserInfoManager.getInstance().getLoginPO().isState()) {
            IntentUtil.startActivityWithFinish(context, HomeActivity.class, null);
        }else{
            initData();
            listener();
        }
    }
    //初始化数据
    private void initData() {
        String account=ShardPreferenceTool.getInstance(context).get(ShareKeyConfig.ACCOUNT_PRIVATE);
        String password=ShardPreferenceTool.getInstance(context).get(ShareKeyConfig.PASSWORD_PRIVATE);
        if(account!=null){
            accountInput.setText(account);
            if(password!=null){
                passwordInput.setText(password);
            }
        }
    }


    private void listener() {
        //登录监听
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //校验
                final String account=accountInput.getText().toString();
                final String password=passwordInput.getText().toString();

                if(!CharacterTool.isRightAccount(account)){
                    accountInput.setFocusable(true);
                    accountInput.requestFocus();
                    Toast.makeText(context,"用户名格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CharacterTool.isRightPassword(password)){
                    passwordInput.setFocusable(true);
                    passwordInput.requestFocus();
                    Toast.makeText(context,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }

                //防止多次点击
                loginBtn.setEnabled(false);
               try{
                   JSONObject param=new JSONObject();
                   param.put("account",account);
                   param.put("password", MD5.getMessageDigest(password.getBytes(Charset.forName("utf-8"))));
                   OKHttpTool.jsonRequest(context, getTag(), Urls.getUrl(Urls.LOGIN_URL), param, new HttpCallBack<JSONObject, String, String>() {
                       @Override
                       public void successCall(JSONObject jsonObject) {

                           Gson gson=new Gson();
                           try {
                               LoginPO po = gson.fromJson(jsonObject.getJSONObject("entity").toString(),LoginPO.class);
                               if(po.getUser()!=null){
                                   po.setState(true);
                               }
                               UserInfoManager.getInstance().setLoginPO(po);
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }finally {
                               if(UserInfoManager.getInstance().getLoginPO().isState()){
                                   Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();

                                   Map<String,String> map=new HashMap<String,String>();
                                   map.put(ShareKeyConfig.ACCOUNT_PRIVATE,account);
                                   map.put(ShareKeyConfig.PASSWORD_PRIVATE,password);
                                   ShardPreferenceTool.getInstance(context).save(map);

                                   IntentUtil.startActivityWithFinish(context,HomeActivity.class,null);
                               }else{
                                   Toast.makeText(context,"返回参数异常",Toast.LENGTH_SHORT).show();
                               }
                           }

                       }

                       @Override
                       public void failedCall(String s) {
                          Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void error(String s) {
                           Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
                       }

                       @Override
                       public void complete() {
                           loginBtn.setEnabled(true);
                       }
                   });
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });

        //注册监听
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //校验
                final String account=accountInput.getText().toString();
                final String password=passwordInput.getText().toString();

                if(!CharacterTool.isRightAccount(account)){
                    accountInput.setFocusable(true);
                    accountInput.requestFocus();
                    Toast.makeText(context,"用户名格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!CharacterTool.isRightPassword(password)){
                    passwordInput.setFocusable(true);
                    passwordInput.requestFocus();
                    Toast.makeText(context,"密码格式不正确",Toast.LENGTH_SHORT).show();
                    return;
                }

                //防止多次点击
                registerBtn.setEnabled(false);

                try{
                    JSONObject param=new JSONObject();
                    param.put("account",account);
                    param.put("password", MD5.getMessageDigest(password.getBytes(Charset.forName("utf-8"))));
                    OKHttpTool.jsonRequest(context, getTag(), Urls.getUrl(Urls.REGISTER_URL), param, new HttpCallBack<JSONObject, String, String>() {
                        @Override
                        public void successCall(JSONObject jsonObject) {

                            Gson gson=new Gson();
                            try {
                                LoginPO po = gson.fromJson(jsonObject.getJSONObject("entity").toString(),LoginPO.class);
                                if(po.getUser()!=null){
                                    po.setState(true);
                                }
                                UserInfoManager.getInstance().setLoginPO(po);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }finally {
                                if(UserInfoManager.getInstance().getLoginPO().isState()){
                                    Toast.makeText(context,"注册成功",Toast.LENGTH_SHORT).show();

                                    Map<String,String> map=new HashMap<String,String>();
                                    map.put(ShareKeyConfig.ACCOUNT_PRIVATE,account);
                                    map.put(ShareKeyConfig.PASSWORD_PRIVATE,password);
                                    ShardPreferenceTool.getInstance(context).save(map);

                                    IntentUtil.startActivityWithFinish(context,HomeActivity.class,null);
                                }else{
                                    Toast.makeText(context,"返回参数异常",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        @Override
                        public void failedCall(String s) {
                            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void error(String s) {
                            Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void complete() {
                            registerBtn.setEnabled(true);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


}

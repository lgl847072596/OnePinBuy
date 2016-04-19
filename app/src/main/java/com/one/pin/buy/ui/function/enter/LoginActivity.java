package com.one.pin.buy.ui.function.enter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.one.pin.buy.R;
import com.one.pin.buy.listener.HttpCallBack;
import com.one.pin.buy.tool.http.OKHttpTool;
import com.one.pin.buy.ui.base.BaseActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends BaseActivity {


    private Button loginBtn;
    private Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        loginBtn=(Button)findViewById(R.id.login_btn);
        registerBtn=(Button)findViewById(R.id.register_btn);

        listener();
    }

    private void listener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setEnabled(false);
                OKHttpTool.stringRequest(context,getTag(),0,"http://www.baidu.com",null,null,new HttpCallBack<String,String,String>(){

                    @Override
                    public void successCall(String s) {

                    }

                    @Override
                    public void failedCall(String s) {

                    }

                    @Override
                    public void error(String s) {

                    }

                    @Override
                    public void complete() {
                        loginBtn.setEnabled(true);
                    }
                });
            }
        });
    }


}

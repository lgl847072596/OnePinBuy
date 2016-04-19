package com.one.pin.buy.ui.function.enter;

import android.content.Intent;
import android.os.Bundle;

import com.one.pin.buy.R;
import com.one.pin.buy.ui.base.BaseActivity;

public class IndexActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);

        startActivity(new Intent(this,LoginActivity.class));
    }
}

package cn.star.csxsjx.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.star.csxsjx.R;
import cn.star.csxsjx.base.BaseActivity;
import cn.star.csxsjx.view.main.MainActivity;

public class WelcomeActivity extends BaseActivity {
    private int TIME = 2300;//延时多少秒启动
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        HiddenWindos();

    }

    @Override
    protected void initData() {
        new Handler().postDelayed(() -> {
           startActivity(new Intent(this,MainActivity.class));
           finish();
        }, TIME);
    }
}

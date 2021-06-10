package com.spider.bookmyroom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.spider.bookmyroom.Utility.UserSessionManager;
import com.spider.bookmyroom.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding splashBinding;
    UserSessionManager session;
    private final Context context = SplashActivity.this;
    int SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(splashBinding.getRoot());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        session = new UserSessionManager(getApplicationContext());
        new Handler().postDelayed(() -> {
            if (session.checkLogin()) {
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
                finish();
            } else {
                finish();
            }
        }, SPLASH_TIME);

    }
}
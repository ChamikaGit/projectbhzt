package com.fidenz.chami.dev.bhooztapp.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.fidenz.chami.dev.bhooztapp.R;
import com.crashlytics.android.Crashlytics;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 3000;
    private StatusBarColor statusBarColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_actvity);

        init();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void init() {
        statusBarColor = new StatusBarColor(this);
        statusBarColor.setStatusBarColour();
    }
}

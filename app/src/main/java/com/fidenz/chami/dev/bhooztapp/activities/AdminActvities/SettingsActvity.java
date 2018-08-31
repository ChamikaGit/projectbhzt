package com.fidenz.chami.dev.bhooztapp.activities.AdminActvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.activities.MainActivity;
import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActvity extends AppCompatActivity {

    @BindView(R.id.idbtnAdvertistmnets)
    Button btnAdvertisements;

    @BindView(R.id.idbtnticker)
    Button btnTicker;

    @BindView(R.id.idbtnscaneresults)
    Button btnScanResults;

    @BindView(R.id.idbtngrandprice)
    Button btnGrandPrice;

    @BindView(R.id.idbtnwinloose)
    Button btnWinLoose;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.toobarname)
    TextView tvToolbar;

    @BindView(R.id.idscoll)
    ScrollView sclScroll;

    StatusBarColor barColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        tvToolbar.setText("Settings");

        swipeTouchListener();
    }

    private void swipeTouchListener() {
        sclScroll.setOnTouchListener(new OnSwipeTouchListener(SettingsActvity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(SettingsActvity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    @OnClick(R.id.idbtnAdvertistmnets)
    public void switchToAdvertisementActivity(View view) {
        Intent intentAd = new Intent(SettingsActvity.this, AdvertisementsActivity.class);
        startActivity(intentAd);
    }

    @OnClick(R.id.idbtnticker)
    public void switchToTickerActivity(View view) {
        Intent intentTicker = new Intent(SettingsActvity.this, TickertextActivity.class);
        startActivity(intentTicker);
    }

    @OnClick(R.id.idbtnscaneresults)
    public void switchToScanResultActivity(View view) {
        Intent intentScan = new Intent(SettingsActvity.this, ScanResultActivity.class);
        startActivity(intentScan);
    }

    @OnClick(R.id.idbtngrandprice)
    public void switchToGrandPriceActivity(View view) {
        Intent intentGrandPrice = new Intent(SettingsActvity.this, GrandprizeActivity.class);
        startActivity(intentGrandPrice);
    }

    @OnClick(R.id.idbtnwinloose)
    public void switchToWinLooseActivity(View view) {
        Intent intentWinLoose = new Intent(SettingsActvity.this, WinlooseActivity.class);
        startActivity(intentWinLoose);
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intentHome = new Intent(SettingsActvity.this, MainActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActvity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}

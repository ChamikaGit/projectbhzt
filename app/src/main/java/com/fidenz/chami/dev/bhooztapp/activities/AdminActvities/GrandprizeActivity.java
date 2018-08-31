package com.fidenz.chami.dev.bhooztapp.activities.AdminActvities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;
import com.fidenz.chami.dev.bhooztapp.helpers.softkeybordhide.KeyboardHide;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GrandprizeActivity extends AppCompatActivity {

    @BindView(R.id.idetgrandprizetext)
    EditText etGrandPrizeText;

    @BindView(R.id.idbtnsave)
    Button btnSave;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.toobarname)
    TextView tvViewToolbar;

    @BindView(R.id.idlinearoutside)
    LinearLayout linOutside;

    @BindView(R.id.idrelgrandprice)
    RelativeLayout relGrandPrice;

    SharedPreference sharedPreference;
    StatusBarColor barColor;
    KeyboardHide keyboardHide;

    String grandPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grandprize);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();
        editTextFocusableEnable();

        etGrandPrizeText.setFocusable(false);

        tvViewToolbar.setText("Grand Prize Text");

        grandPriceText = sharedPreference.getGrandPriceText(getApplicationContext());
        if (grandPriceText != null) {
            etGrandPrizeText.setText(grandPriceText);
        }
    }

    private void init() {
        sharedPreference = new SharedPreference();

        keyboardHide = new KeyboardHide(this);
        keyboardHide.softKeyboardHide();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();
    }

    private void editTextFocusableEnable() {
        etGrandPrizeText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        relGrandPrice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                keyboardHide.buttonClickKeyBordHide(view);
                etGrandPrizeText.setFocusable(false);
                return false;
            }
        });
    }

    private void swipeTouchListener() {
        linOutside.setOnTouchListener(new OnSwipeTouchListener(GrandprizeActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(GrandprizeActivity.this, SettingsActvity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    @OnClick(R.id.idbtnsave)
    public void grandPriceSave(View view) {

        if (TextUtils.isEmpty(etGrandPrizeText.getText().toString().trim())) {
            etGrandPrizeText.setError("Text is empty");
            return;
        }
        sharedPreference.saveGrandPriceText(getApplicationContext(), etGrandPrizeText.getText().toString().trim());
        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_LONG).show();
        etGrandPrizeText.setText("");
        etGrandPrizeText.setFocusable(false);

        keyboardHide.buttonClickKeyBordHide(view);
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(GrandprizeActivity.this, SettingsActvity.class);
        startActivity(intent);
        finish();
    }


}

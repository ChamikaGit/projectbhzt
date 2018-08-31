package com.fidenz.chami.dev.bhooztapp.activities.AdminActvities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class ScanResultActivity extends AppCompatActivity {

    @BindView(R.id.idetheader)
    EditText etScanResultHeader;

    @BindView(R.id.ideresulttext)
    EditText etResultText;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.idbtnsave)
    Button btnSave;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idlinearoutside)
    LinearLayout linOutSide;

    @BindView(R.id.idrelsacan)
    RelativeLayout relScanBack;

    String resultHeader, resultText;
    SharedPreference sharedPreference;
    StatusBarColor barColor;
    KeyboardHide keyboardHide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanresult);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();
        editTextFocusableEnable();

        etScanResultHeader.setFocusable(false);
        etResultText.setFocusable(false);

        tvToolbarName.setText("Result Text");

        resultHeader = sharedPreference.getResultHeader(getApplicationContext());
        resultText = sharedPreference.getResultText(getApplicationContext());

        if (resultHeader != null && resultText != null) {

            etScanResultHeader.setText(resultHeader);
            etResultText.setText(resultText);
        }

    }

    private void init() {
        sharedPreference = new SharedPreference();

        keyboardHide = new KeyboardHide(this);
        keyboardHide.softKeyboardHide();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();
    }

    private void swipeTouchListener() {
        linOutSide.setOnTouchListener(new OnSwipeTouchListener(ScanResultActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(ScanResultActivity.this, SettingsActvity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void editTextFocusableEnable() {
        etScanResultHeader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });


        etResultText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });

        relScanBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                etScanResultHeader.setFocusable(false);
                etResultText.setFocusable(false);

                return false;
            }
        });
    }

    @OnClick(R.id.idbtnsave)
    public void scanResultSave(View view) {

        if (TextUtils.isEmpty(etScanResultHeader.getText().toString().trim())) {
            etScanResultHeader.setError("text is empty");
            return;
        }
        if (TextUtils.isEmpty(etResultText.getText().toString().trim())) {
            etResultText.setError("text is empty");
            return;
        }
        sharedPreference.saveResultText(getApplicationContext(), etScanResultHeader.getText().toString().trim(), etResultText.getText().toString().trim());
        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_LONG).show();

        etScanResultHeader.setText("");
        etResultText.setText("");

        etScanResultHeader.setFocusable(false);
        etResultText.setFocusable(false);

        keyboardHide.buttonClickKeyBordHide(view);
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(ScanResultActivity.this, SettingsActvity.class);
        startActivity(intent);
        finish();
    }
}

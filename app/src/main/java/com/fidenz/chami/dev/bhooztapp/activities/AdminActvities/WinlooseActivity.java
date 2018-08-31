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

public class WinlooseActivity extends AppCompatActivity {

    @BindView(R.id.idetwintext)
    EditText etWinText;

    @BindView(R.id.idetloosetext)
    EditText etLooseText;

    @BindView(R.id.idetwininmessage)
    EditText etWinMessage;

    @BindView(R.id.toobarname)
    TextView tvToolbar;

    @BindView(R.id.idbtnsave)
    Button btnSave;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idlinearoutside)
    LinearLayout linOutSide;

    @BindView(R.id.idrelwinloose)
    RelativeLayout relWinLoose;

    String resultWin, resultLoose, resultWining;
    SharedPreference sharedPreference;
    StatusBarColor barColor;
    KeyboardHide keyboardHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winloose);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();
        editTextFocusableEnable();

        resultWin = sharedPreference.getWinText(getApplicationContext());
        resultLoose = sharedPreference.getLooseText(getApplicationContext());
        resultWining = sharedPreference.getWiningText(getApplicationContext());
        if (resultWin != null && resultLoose != null && resultWining != null) {
            etWinText.setText(resultWin);
            etLooseText.setText(resultLoose);
            etWinMessage.setText(resultWining);
        }
        etWinText.setFocusable(false);
        etLooseText.setFocusable(false);
        etWinMessage.setFocusable(false);

        tvToolbar.setText("Win/Loose Text");

    }

    private void init() {
        sharedPreference = new SharedPreference();

        keyboardHide = new KeyboardHide(this);
        keyboardHide.softKeyboardHide();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();
    }

    private void editTextFocusableEnable() {
        etWinText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
        etLooseText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
        etWinMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
        relWinLoose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                etWinText.setFocusable(false);
                etLooseText.setFocusable(false);
                etWinMessage.setFocusable(false);

                return false;
            }
        });
    }

    private void swipeTouchListener() {
        linOutSide.setOnTouchListener(new OnSwipeTouchListener(WinlooseActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(WinlooseActivity.this, SettingsActvity.class);
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
    public void winLooseText(View view) {
        if (TextUtils.isEmpty(etWinText.getText().toString().trim())) {
            etWinText.setError("Text is empty");
            return;
        }
        if (TextUtils.isEmpty(etLooseText.getText().toString().trim())) {
            etLooseText.setError("Text is empty");
            return;
        }
        if (TextUtils.isEmpty(etWinMessage.getText().toString().trim())) {
            etWinMessage.setError("Text is empty");
            return;
        }
        sharedPreference.saveWinLooseText(getApplicationContext(), etWinText.getText().toString().trim(), etLooseText.getText().toString().trim(), etWinMessage.getText().toString().trim());
        Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_LONG).show();
        etLooseText.setText("");
        etWinText.setText("");
        etWinMessage.setText("");

        etWinText.setFocusable(false);
        etLooseText.setFocusable(false);
        etWinMessage.setFocusable(false);

        keyboardHide.buttonClickKeyBordHide(view);

    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(WinlooseActivity.this, SettingsActvity.class);
        startActivity(intent);
        finish();
    }
}

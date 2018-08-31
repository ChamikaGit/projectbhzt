package com.fidenz.chami.dev.bhooztapp.activities.AdminActvities;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.adapter.TickerAdapter;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;
import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.softkeybordhide.KeyboardHide;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TickertextActivity extends AppCompatActivity implements TickerAdapter.ItemListener {

    @BindView(R.id.idetTickertext)
    EditText etTickerText;

    @BindView(R.id.idbtnsave)
    Button btnSave;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.toobarname)
    TextView tvToolbar;

    @BindView(R.id.idlinearoutside)
    LinearLayout linOutSide;

    @BindView(R.id.relticker)
    LinearLayout linTickerLayout;

    @BindView(R.id.idTickertextrecylerview)
    RecyclerView recTickers;

    private DBHelper dbHelper;
    private Boolean aBoolean = false;
    private int tickerIdNew;
    List mTickerList = new ArrayList<Tickers>();
    RecyclerView.Adapter adapterTickers;
    StatusBarColor barColor;
    KeyboardHide keyboardHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickertext);
        ButterKnife.bind(this);

        init();
        editTextFocusableEnable();
        inititems();

        etTickerText.setFocusable(false);
        recTickers.setHasFixedSize(true);
        recTickers.setLayoutManager(new LinearLayoutManager(this));

        tvToolbar.setText("Ticker Text");
    }

    private void init() {
        dbHelper = new DBHelper(this);

        keyboardHide = new KeyboardHide(this);
        keyboardHide.softKeyboardHide();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();
    }

    private void editTextFocusableEnable() {
        linOutSide.setOnTouchListener(new OnSwipeTouchListener(TickertextActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(TickertextActivity.this, SettingsActvity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });

        etTickerText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });


        linTickerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                etTickerText.setFocusable(false);

                return false;
            }
        });
    }

    private void inititems() {
        mTickerList = getAllTickers();
        adapterTickers = new TickerAdapter(mTickerList, this, this);
        recTickers.setAdapter(adapterTickers);
        adapterTickers.notifyDataSetChanged();
    }

    private void updateTicker() {

        String tickerText = etTickerText.getText().toString().trim();
        Tickers tickers = new Tickers();
        tickers.setId(tickerIdNew);
        tickers.setTickerText(tickerText);

        try {
            dbHelper.createOrUpdateTickers(tickers);
            inititems();
            recTickers.scrollToPosition(mTickerList.size() - 1);
            btnSave.setText("Save");
            etTickerText.setText("");
            etTickerText.setFocusable(false);

            Toast.makeText(getApplicationContext(), "Succesfully Updated", Toast.LENGTH_LONG).show();

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        aBoolean = false;

    }

    private void addTickers() {
        Tickers tickers = new Tickers();
        String tickerText = etTickerText.getText().toString().trim();

        if (TextUtils.isEmpty(tickerText)) {
            etTickerText.setError("Ticker text is empty");
            return;
        }
        tickers.setTickerText(etTickerText.getText().toString());

        try {
            if (mTickerList.size() < 5) {
                dbHelper.createOrUpdateTickers(tickers);
                etTickerText.setText("");
                etTickerText.setFocusable(false);
            } else {

                etTickerText.setText("");
                etTickerText.setFocusable(false);
                Toast.makeText(getApplicationContext(), "Maximum Ticker texts are limited to 5", Toast.LENGTH_LONG).show();
            }
            inititems();
            recTickers.scrollToPosition(mTickerList.size() - 1);
            Log.e("ADD", "ADD TO DB");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }


    public List getAllTickers() {

        List mTickerList = new ArrayList<>();
        try {
            mTickerList.addAll(dbHelper.getAllTickers(Tickers.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return mTickerList;
    }

    @OnClick(R.id.idbtnsave)
    public void tickerSave(View view) {
        if (!aBoolean) {
            addTickers();
        } else {
            updateTicker();
        }
        keyboardHide.buttonClickKeyBordHide(view);

    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {

        Intent intent = new Intent(TickertextActivity.this, SettingsActvity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickEdit(int position) {
        Tickers tk = (Tickers) mTickerList.get(position);
        int id = tk.getId();
        String passTickerText = tk.getTickerText();

        etTickerText.setText(passTickerText);
        tickerIdNew = id;

        btnSave.setText("Update");
        aBoolean = true;

    }

}

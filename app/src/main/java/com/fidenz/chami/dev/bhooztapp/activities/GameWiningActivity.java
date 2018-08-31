package com.fidenz.chami.dev.bhooztapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.MarqueeLayout;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.TickerText;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameWiningActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {


    @BindView(R.id.idimgshare)
    ImageView imgShare;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idgrandpricetext)
    TextView tvGrandPrice;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.toobarname)
    TextView toolbarName;

    @BindView(R.id.idcongatstextdescription)
    TextView tvCongratsTextDescription;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    @BindView(R.id.idgamewiningactvity)
    LinearLayout linGameWiningActivity;

    SharedPreference sharedPreference;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private List<Tickers> tickers = new ArrayList();
    StatusBarColor barColor;
    private int marqueeCurrentIndex = 0;
    String totalPointCount, grandPriceText, getWiningText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamewining_actvity);
        ButterKnife.bind(this);

        swipeTouchListener();
        init();

        toolbarName.setText("Bhoozt");

        totalPointCount = sharedPreference.getTotalCoinCount(getApplicationContext());
        grandPriceText = sharedPreference.getGrandPriceText(getApplicationContext());
        getWiningText = sharedPreference.getWiningText(getApplicationContext());

        if (totalPointCount != null) {
            tvCoinCountFull.setText(totalPointCount);
        }
        if (grandPriceText != null) {
            tvGrandPrice.setText(grandPriceText);
        }
        if (tvCongratsTextDescription != null) {
            tvCongratsTextDescription.setText(getWiningText);
        }
    }

    private void init() {
        sharedPreference = new SharedPreference();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(marqueeLayout, 10);
    }

    private void swipeTouchListener() {
        linGameWiningActivity.setOnTouchListener(new OnSwipeTouchListener(GameWiningActivity.this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                Intent intent = new Intent(GameWiningActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void setupTickerOnUI() {

        tickerText = new TickerText(this);
        tickers = tickerText.getAllTickers();

        this.marqueeCurrentIndex = 0;

        if (tickers != null && tickers.size() != 0) {
            String tickerTxt = tickers.get(marqueeCurrentIndex).getTickerText();
            marquee.addMarquee(true, tickerTxt);

        } else {
            marquee.addMarquee(true, "Ticker Text Empty");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTickerOnUI();
    }

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(GameWiningActivity.this, MainActivity.class);
        intent.putExtra("Coincount", "Coin");
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(GameWiningActivity.this, MainActivity.class);
        intent.putExtra("OpengameList", "gamelist");
        startActivity(intent);
    }

    @OnClick(R.id.idimgshare)
    public void onClickShare(View view) {
        Intent intent = new Intent(GameWiningActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    @Override
    public void onMarqueeAnimationStart() {
    }

    @Override
    public void onMarqueeAnimationFinish() {
    }

    @Override
    public void onMarqueeAnimationRepeat(String currentText) {

        if (tickers != null && tickers.size() != 0) {
            if (marqueeCurrentIndex == (tickers.size() - 1)) {
                marqueeCurrentIndex = 0;
            } else {
                ++marqueeCurrentIndex;
            }

            marquee.addMarquee(true, tickers.get(marqueeCurrentIndex).getTickerText());
        } else {

            marquee.addMarquee(true, "Ticker Text Empty");
        }

    }
}

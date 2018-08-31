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

public class ScanResultsActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {


    @BindView(R.id.idimgshare)
    ImageView imgPlay;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idgrandpricetext)
    TextView tvGrandPriceText;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.toobarname)
    TextView toolbarName;

    @BindView(R.id.idcongatstextdescription)
    TextView tvCongratsTextDescription;

    @BindView(R.id.idcongatstext)
    TextView tvCongratsText;

    @BindView(R.id.relscannerresultpage)
    LinearLayout linScannerResultPage;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    private SharedPreference sharedPreference;
    private Intent intent;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private List<Tickers> tickers = new ArrayList();
    private int marqueeCurrentIndex = 0;
    String gameName, resultHeader, resultText, grandPriceText, totalPointCount;
    StatusBarColor barColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_results);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();

        intent = getIntent();
        gameName = intent.getStringExtra("Gamename");

        toolbarName.setText("Bhoozt");

        totalPointCount = sharedPreference.getTotalCoinCount(getApplicationContext());
        grandPriceText = sharedPreference.getGrandPriceText(getApplicationContext());
        resultHeader = sharedPreference.getResultHeader(getApplicationContext());
        resultText = sharedPreference.getResultText(getApplicationContext());

        tvCoinCountFull.setText(totalPointCount);
        tvGrandPriceText.setText(grandPriceText);
        tvCongratsText.setText(resultHeader);
        tvCongratsTextDescription.setText(resultText);
    }

    private void init() {
        sharedPreference = new SharedPreference();

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(marqueeLayout, 10);
    }

    private void swipeTouchListener() {
        linScannerResultPage.setOnTouchListener(new OnSwipeTouchListener(ScanResultsActivity.this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                Intent intent = new Intent(ScanResultsActivity.this, MainActivity.class);
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
            String tickertxt = tickers.get(marqueeCurrentIndex).getTickerText();
            marquee.addMarquee(true, tickertxt);

        } else {
            marquee.addMarquee(true, "Ticker Text Empty");

        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onResume() {
        super.onResume();

        setupTickerOnUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick(R.id.idimgshare)
    public void onClickShare(View view) {

        if (gameName != null) {
            if (gameName.equals("CocaCola")) {

                Intent intent = new Intent(ScanResultsActivity.this, CocaColaActivity.class);
                intent.putExtra("correctgamescan", "gamec");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else if (gameName.equals("Bhoozt.com")) {

                Intent intent = new Intent(ScanResultsActivity.this, BhooztActivity.class);
                intent.putExtra("correctgamescan", "gameb");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                finish();
            }

        }
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(ScanResultsActivity.this, MainActivity.class);
        intent.putExtra("OpengameList", "gamelist");
        startActivity(intent);
    }

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(ScanResultsActivity.this, MainActivity.class);
        intent.putExtra("Coincount", "Coin");
        startActivity(intent);
        finish();
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

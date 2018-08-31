package com.fidenz.chami.dev.bhooztapp.activities;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;
import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.MarqueeLayout;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.TickerText;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoinXFRActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {

    @BindView(R.id.homeadheader)
    TextView tvXFRAdHeader;

    @BindView(R.id.homeaddbody)
    TextView tvXFRAdBody;

    @BindView(R.id.homeaddlink)
    TextView tvXFRAddLink;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idlinxfr)
    LinearLayout linXFRLayout;

    @BindView(R.id.idmarqueetext)
    RelativeLayout relMarqueeLayout;

    private List<Advertisements> advertisementList;
    private List<Tickers> tickers = new ArrayList();
    private DBHelper dbHelper;
    private Thread thread;
    private MarqueeLayout marqueeLayout;
    private TickerText tickerText;
    private SharedPreference sharedPreference;
    private Handler handler = new Handler();
    private String totalPointCount;
    volatile boolean activityStopped = false;
    private int marqueeCurrentIndex = 0;
    private int count = 0;
    StatusBarColor barColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_xfr);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();

        tvToolbarName.setText("");

        tvXFRAddLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        totalPointCount = sharedPreference.getTotalCoinCount(getApplicationContext());
        tvCoinCountFull.setText(totalPointCount);

    }

    private void init() {
        sharedPreference = new SharedPreference();
        dbHelper = new DBHelper(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marqueeLayout = new MarqueeLayout(this, this);
        marqueeLayout.setupMarquee(relMarqueeLayout, 10);
    }

    private void swipeTouchListener() {
        linXFRLayout.setOnTouchListener(new OnSwipeTouchListener(CoinXFRActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(CoinXFRActivity.this, MainActivity.class);
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
            marqueeLayout.addMarquee(true, tickertxt);

        } else {
            marqueeLayout.addMarquee(true, "Ticker Text Empty");

        }
    }

    public List getAllAdvertisements() {

        List madList = new ArrayList<>();
        try {
            madList.addAll(dbHelper.getAll(Advertisements.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return madList;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (advertisementList.size() == 0) {
                tvXFRAdHeader.setText("This is the World cup -Scarcth and win");
                tvXFRAdBody.setText("Coca-Cola,Open Happiness");
                tvXFRAddLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementList.size()) {
                        Advertisements advertisements = advertisementList.get(finalCount);
                        tvXFRAdHeader.setText(advertisements.getAdheader());
                        tvXFRAdBody.setText(advertisements.getAdbody());
                        tvXFRAddLink.setText(advertisements.getAdlinkname());
                        Log.e("Count", "" + finalCount);
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    count = 0;
                }
            }
            handler.postDelayed(runnable, 5000);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        advertisementList = getAllAdvertisements();
        activityStopped = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTickerOnUI();
        handler.post(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {
        Intent intent = new Intent(CoinXFRActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(CoinXFRActivity.this, MainActivity.class);
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

            marqueeLayout.addMarquee(true, tickers.get(marqueeCurrentIndex).getTickerText());
        } else {

            marqueeLayout.addMarquee(true, "Ticker Text Empty");
        }
    }

}

package com.fidenz.chami.dev.bhooztapp.activities;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.MarqueeLayout;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.TickerText;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {


    @BindView(R.id.homeadheader)
    TextView tvShareAdHeader;

    @BindView(R.id.homeaddbody)
    TextView tvShareAddBody;

    @BindView(R.id.homeaddlink)
    TextView tvShareAddLink;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    @BindView(R.id.idlinshare)
    LinearLayout linShareLayout;

    private List<Advertisements> advertisementsList;
    private DBHelper dbHelper;
    private Thread thread;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private List<Tickers> tickers = new ArrayList();
    private Handler handler = new Handler();
    private SharedPreference sharedPreference;
    private String totalPointCount;
    private int count = 0;
    private int marqueeCurrentIndex = 0;
    volatile boolean activityStopped = false;
    StatusBarColor barColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_actvity);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();

        tvShareAddLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        totalPointCount = sharedPreference.getTotalCoinCount(getApplicationContext());
        tvCoinCountFull.setText(totalPointCount);

        tvToolbarName.setText("Share");
    }

    private void swipeTouchListener() {
        linShareLayout.setOnTouchListener(new OnSwipeTouchListener(ShareActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                Intent intent = new Intent(ShareActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void init() {
        sharedPreference = new SharedPreference();
        dbHelper = new DBHelper(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(marqueeLayout, 10);
    }

    public List getAllAdvertistmnets() {

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

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (advertisementsList.size() == 0) {
                tvShareAdHeader.setText("This is the World cup -Scarcth and win");
                tvShareAddBody.setText("Coca-Cola,Open Happiness");
                tvShareAddLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementsList.size()) {
                        Advertisements advertisements = advertisementsList.get(finalCount);
                        tvShareAdHeader.setText(advertisements.getAdheader());
                        tvShareAddBody.setText(advertisements.getAdbody());
                        tvShareAddLink.setText(advertisements.getAdlinkname());
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

        advertisementsList = getAllAdvertistmnets();
        activityStopped = false;
        //startAdvertisements();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(ShareActivity.this, MainActivity.class);
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


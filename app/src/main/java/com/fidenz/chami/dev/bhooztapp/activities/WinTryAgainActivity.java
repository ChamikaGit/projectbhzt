package com.fidenz.chami.dev.bhooztapp.activities;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

public class WinTryAgainActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {

    @BindView(R.id.idimgtry)
    ImageView imgTryAgain;

    @BindView(R.id.idcongatstext)
    TextView tvCongratsText;

    @BindView(R.id.homeadheader)
    TextView tvWinTryAdHeader;

    @BindView(R.id.homeaddbody)
    TextView tvWinTryAdBody;

    @BindView(R.id.homeaddlink)
    TextView tvWinTryAdLink;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    @BindView(R.id.idlintry)
    LinearLayout linWinTryLayout;

    private Intent intent;
    private SharedPreference sharedPreference;
    private List<Advertisements> advertisementsList;
    private List<Tickers> tickers = new ArrayList();
    private Handler handler = new Handler();
    private DBHelper dbHelper;
    private Thread thread;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private int count = 0;
    private int marqueeCurrentIndex = 0;
    volatile boolean activityStopped = false;
    int imgPlayTry[] = {R.drawable.play, R.drawable.tryagain};
    String winingPiece1, winingPiece2, tryAgain, gamePlay1, gamePlay2, gameTry, passMessage, message, totalPointCount, resultWin, resultLoose;
    StatusBarColor barColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wintryagain);
        ButterKnife.bind(this);

        init();
        swipeTouchListener();

        tvToolbarName.setText("Bhoozt");

        intent = getIntent();

        resultWin = sharedPreference.getWinText(getApplicationContext());
        resultLoose = sharedPreference.getLooseText(getApplicationContext());
        totalPointCount = sharedPreference.getTotalCoinCount(getApplicationContext());

        winingPiece1 = intent.getStringExtra("Gamecoca");
        winingPiece2 = intent.getStringExtra("bhoozt");
        tryAgain = intent.getStringExtra("gametryagain");
        passMessage = intent.getStringExtra("passMessage");

        tvCoinCountFull.setText(totalPointCount);

        tvWinTryAdLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        imageSetFunction();
    }

    private void swipeTouchListener() {
        linWinTryLayout.setOnTouchListener(new OnSwipeTouchListener(WinTryAgainActivity.this) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                if (gameTry.equals("Tryagain")) {
                    finish();
                } else {
                }
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

    private void imageSetFunction() {

        if (winingPiece1 != null) {

            gamePlay1 = winingPiece1;

        } else {

            gamePlay1 = "nogame";
        }

        if (winingPiece2 != null) {

            gamePlay2 = winingPiece2;
        } else {

            gamePlay2 = "nogame";
        }

        if (tryAgain != null) {

            gameTry = tryAgain;
        } else {

            gameTry = "nogame";
        }

        if (passMessage != null) {

            message = passMessage;
        }


        if (gameTry.equals("Tryagain")) {

            imgTryAgain.setImageResource(imgPlayTry[1]);
            tvCongratsText.setText(resultLoose);

        }
        if (gamePlay1.equals("CocaCola")) {

            imgTryAgain.setImageResource(imgPlayTry[0]);
            if (resultWin != null) {
                tvCongratsText.setText(resultWin + " " + message);
            } else {

                tvCongratsText.setText("");
            }
        }

        if (gamePlay2.equals("Bhoozt.com")) {
            imgTryAgain.setImageResource(imgPlayTry[0]);
            if (resultWin != null) {
                tvCongratsText.setText(resultWin + " " + message);
            } else {

                tvCongratsText.setText("");
            }
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
            if (advertisementsList.size() == 0) {
                tvWinTryAdHeader.setText("This is the World cup -Scarcth and win");
                tvWinTryAdBody.setText("Coca-Cola,Open Happiness");
                tvWinTryAdLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementsList.size()) {
                        Advertisements advertisements = advertisementsList.get(finalCount);
                        tvWinTryAdHeader.setText(advertisements.getAdheader());
                        tvWinTryAdBody.setText(advertisements.getAdbody());
                        tvWinTryAdLink.setText(advertisements.getAdlinkname());
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
        advertisementsList = getAllAdvertisements();
        activityStopped = false;
        //StartAdvertisement();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTickerOnUI();
        handler.post(runnable);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityStopped = false;
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @OnClick(R.id.idimgtry)
    public void onClickImagetry(View view) {
        if (gameTry.equals("Tryagain")) {
            finish();
        }
        if ((gamePlay1.equals("CocaCola"))) {

            Intent intentWin = new Intent(WinTryAgainActivity.this, CocaColaActivity.class);
            intentWin.putExtra("correctgame", "Cocacola");
            startActivity(intentWin);
            finish();
        }
        if (gamePlay2.equals("Bhoozt.com")) {
            Intent intentWin = new Intent(WinTryAgainActivity.this, BhooztActivity.class);
            intentWin.putExtra("correctgame", "Bhoozt");
            startActivity(intentWin);
            finish();
        }
    }

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(WinTryAgainActivity.this, MainActivity.class);
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

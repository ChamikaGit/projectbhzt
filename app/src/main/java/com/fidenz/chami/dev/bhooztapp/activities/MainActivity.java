package com.fidenz.chami.dev.bhooztapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.activities.AdminActvities.SettingsActvity;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.R;

import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.MarqueeLayout;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.TickerText;
import com.fidenz.chami.dev.bhooztapp.adapter.PagerAdapter;
import com.fidenz.chami.dev.bhooztapp.fragments.Tab1;
import com.fidenz.chami.dev.bhooztapp.fragments.Tab2;
import com.fidenz.chami.dev.bhooztapp.fragments.Tab3;
import com.fidenz.chami.dev.bhooztapp.app.ImageTargets.ImageTargets;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener, MarqueeLayout.MarqueeAnimationCallback {

    @BindView(R.id.idcamera)
    ImageView imgCamera;

    @BindView(R.id.idekey)
    ImageView imgKey;

    @BindView(R.id.homeadheader)
    TextView tvAdHeader;

    @BindView(R.id.homeaddbody)
    TextView tvAdBody;

    @BindView(R.id.homeaddlink)
    TextView tvAddLink;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.pager)
    ViewPager viewPager;

    @BindView(R.id.dotslayout)
    LinearLayout linDotsLayout;

    @BindView(R.id.idmarqueetext)
    RelativeLayout relMarqueeLayout;

    @BindView(R.id.support_layout)
    LinearLayout linSupportLayout;

    @BindView(R.id.idetcode)
    EditText etGameTokenCode;

    @BindView(R.id.idreltoolbar)
    RelativeLayout relMainToolBar;

    @BindView(R.id.idcoincount)
    LinearLayout linCoinCount;

    @BindView(R.id.idcointcoutfull)
    TextView tvFullCoinCount;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrow;

    @BindView(R.id.idpadlelock)
    ImageView imgPadLock;

    private int count = 0;
    private int Permission_All = 1;
    private int marqueeCurrentIndex = 0;
    private DBHelper dbHelper;
    private Intent intent;
    private List<Advertisements> advertisementsList;
    private TextView[] arrayDots;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private String receiveCoinCount;
    private List<Tickers> tickers = new ArrayList();
    private Handler handler = new Handler();
    volatile boolean activityStopped = false;
    SharedPreference sharedPreference;
    String totalCoin, gameList;
    PagerAdapter adapter;
    StatusBarColor barColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);

        tvAddLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        init();

        intent = getIntent();
        gameList = intent.getStringExtra("OpengameList");
        receiveCoinCount = intent.getStringExtra("Coincount");
        totalCoin = sharedPreference.getTotalCoinCount(getApplicationContext());
        tvFullCoinCount.setText(totalCoin);

        if (receiveCoinCount != null) {
            if (receiveCoinCount.equals("Coin")) {
                viewPager.setCurrentItem(2);
                tvToolbarName.setText("Coins");
            }
        }

        grantPermissions();
        outSideForcesRemove();
        tokenCodeCheck();
    }

    private void init() {

        adapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(viewListener);
        adddotindicator(0);

        sharedPreference = new SharedPreference();
        dbHelper = new DBHelper(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(relMarqueeLayout, 10);
    }

    private void outSideForcesRemove() {

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                etGameTokenCode.setFocusable(false);
                etGameTokenCode.setText("");
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                linSupportLayout.setVisibility(View.GONE);
                imgKey.setVisibility(View.VISIBLE);

                return false;
            }
        });

        etGameTokenCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    private void tokenCodeCheck() {

        etGameTokenCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {
                    String code = etGameTokenCode.getText().toString();
                    if (code.equals("c1") || code.equals("c2")) {
                        imgPadLock.setImageResource(R.drawable.lockopen);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                final Intent mainIntent = new Intent(getApplicationContext(), WinTryAgainActivity.class);
                                mainIntent.putExtra("Gamecoca", "CocaCola");
                                mainIntent.putExtra("passMessage", "Coca-Cola Game");
                                startActivity(mainIntent);
                                linSupportLayout.setVisibility(View.GONE);
                                imgKey.setVisibility(View.VISIBLE);
                                imgPadLock.setImageResource(R.drawable.padlelock);
                                etGameTokenCode.setText("");
                            }
                        }, 1000);
                    } else if (code.equals("b1") || code.equals("b2")) {
                        imgPadLock.setImageResource(R.drawable.lockopen);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                final Intent mainIntent = new Intent(getApplicationContext(), WinTryAgainActivity.class);
                                mainIntent.putExtra("bhoozt", "Bhoozt.com");
                                mainIntent.putExtra("passMessage", "Bhoozt Game");
                                startActivity(mainIntent);
                                linSupportLayout.setVisibility(View.GONE);
                                imgKey.setVisibility(View.VISIBLE);
                                imgPadLock.setImageResource(R.drawable.padlelock);
                                etGameTokenCode.setText("");
                            }
                        }, 1000);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_LONG).show();
                        etGameTokenCode.setText("");
                        etGameTokenCode.setFocusable(false);
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(etGameTokenCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }

                }
                return false;
            }
        });
    }


    private void grantPermissions() {

        String[] Permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,};
        if (!hasPermissions(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        } else {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
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

    private void StartScanner() {
        Intent intent = new Intent(this, ImageTargets.class);
        startActivity(intent);
        this.finish();
    }

    private void adddotindicator(int position) {
        arrayDots = new TextView[3];
        linDotsLayout.removeAllViews();
        for (int i = 0; i < arrayDots.length; i++) {
            arrayDots[i] = new TextView(this);
            arrayDots[i].setText(Html.fromHtml("&#8226;"));
            arrayDots[i].setTextSize(30);
            arrayDots[i].setTextColor(getResources().getColor(R.color.purpledots));
            linDotsLayout.addView(arrayDots[i]);
        }
        if (arrayDots.length > 0) {
            arrayDots[position].setTextColor(getResources().getColor(R.color.colortransparentwhite));
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


    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (advertisementsList.size() == 0) {
                tvAdHeader.setText("This is the World cup -Scarcth and win");
                tvAdBody.setText("Coca-Cola,Open Happiness");
                tvAddLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementsList.size()) {
                        Advertisements advertisements = advertisementsList.get(finalCount);
                        tvAdHeader.setText(advertisements.getAdheader());
                        tvAdBody.setText(advertisements.getAdbody());
                        tvAddLink.setText(advertisements.getAdlinkname());
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

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            adddotindicator(position);
            if (position == 0) {
                tvToolbarName.setText("Bhoozt");
                imgBackArrow.setVisibility(View.GONE);
            }
            if (position == 1) {
                tvToolbarName.setText("Games");
                imgBackArrow.setVisibility(View.VISIBLE);
            }
            if (position == 2) {
                tvToolbarName.setText("Coins");
                imgBackArrow.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @OnClick(R.id.idadminbutton)
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActvity.class);
        startActivity(intent);
    }


    @OnClick(R.id.idekey)
    public void onClickKey(View view) {
        imgKey.setVisibility(View.GONE);
        linSupportLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.leftside));
        linSupportLayout.setVisibility(View.VISIBLE);
        etGameTokenCode.requestFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_FORCED);
    }

    @OnClick(R.id.idcamera)
    public void onClickCamera(View view) {
        String[] Permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,};
        if (!hasPermissions(MainActivity.this, Permissions)) {
            ActivityCompat.requestPermissions(MainActivity.this, Permissions, Permission_All);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, Permissions, Permission_All);
            StartScanner();
        }
    }

    @OnClick(R.id.idreltoolbar)
    public void onClickToolbar(View view) {
        etGameTokenCode.setFocusable(false);
        etGameTokenCode.setText("");
        linSupportLayout.setVisibility(View.GONE);
        imgKey.setVisibility(View.VISIBLE);
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(etGameTokenCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @OnClick(R.id.idcoincount)
    public void onClickCoinCount(View view) {
        viewPager.setCurrentItem(2);
        tvToolbarName.setText("Coins");
    }

    @Override
    public void onResume() {
        super.onResume();
        advertisementsList = getAllAdvertisements();
        handler.post(runnable);
        totalCoin = sharedPreference.getTotalCoinCount(getApplicationContext());
        tvFullCoinCount.setText(totalCoin);
        setupTickerOnUI();
        if (gameList != null) {
            if (gameList.equals("gamelist")) {
                viewPager.setCurrentItem(1);
                tvToolbarName.setText("Games");
                gameList = "abc";
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvToolbarName.setText("Bhoozt");
        intent = getIntent();
        gameList = intent.getStringExtra("OpengameList");
        imgBackArrow.setVisibility(View.GONE);
        activityStopped = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        activityStopped = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
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

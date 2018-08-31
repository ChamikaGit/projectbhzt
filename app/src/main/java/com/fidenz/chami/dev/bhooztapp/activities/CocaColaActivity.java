package com.fidenz.chami.dev.bhooztapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import android.content.ClipData;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.GameIndex;
import com.fidenz.chami.dev.bhooztapp.models.GameIndexcoin;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.R;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.MarqueeLayout;
import com.fidenz.chami.dev.bhooztapp.helpers.TickerText.TickerText;
import com.fidenz.chami.dev.bhooztapp.app.ImageTargets.ImageTargets;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;
import com.fidenz.chami.dev.bhooztapp.database.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CocaColaActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {

    @BindView(R.id.idekey)
    ImageView imgKeyIcon;

    @BindView(R.id.idbackarrow)
    ImageView imgBackArrowIcon;

    @BindView(R.id.idcamera)
    ImageView imgCameraIcon;

    @BindView(R.id.idpadlelock)
    ImageView imgPadlock;

    @BindView(R.id.idpointcount)
    TextView tvPointCount;

    @BindView(R.id.idremainpices)
    TextView tvRemainPieces;

    @BindView(R.id.idcointcoutfull)
    TextView tvCoinCountFull;

    @BindView(R.id.idgrandpricetext)
    TextView tvGrandPriceText;

    @BindView(R.id.toobarname)
    TextView tvToolbarName;

    @BindView(R.id.homeadheader)
    TextView cocaColaAdHeader;

    @BindView(R.id.homeaddbody)
    TextView cocaColaAdBody;

    @BindView(R.id.homeaddlink)
    TextView cocaColaAdLink;

    @BindView(R.id.act_game_pieceholder)
    LinearLayout pieceHolder;

    @BindView(R.id.act_game_puzzelholder)
    RelativeLayout puzzleHolder;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    @BindView(R.id.support_layout)
    LinearLayout SupportLayout;

    @BindView(R.id.idetcode)
    EditText etCode;

    @BindView(R.id.idreltoolbar)
    RelativeLayout relToolbar;

    private DBHelper dbHelper;
    private Handler handler = new Handler();
    private List<GameIndex> mGameindexList;
    private List<GameIndexcoin> mGameindexcoinlist;
    private List<Tickers> tickers = new ArrayList();
    private SharedPreference sharedPreference;
    private List<Advertisements> advertisementsList;
    private Intent intent;
    private MarqueeLayout marquee;
    private TickerText tickerText;
    private ImageView imgGlobal;
    private StatusBarColor barColor;
    volatile boolean activityStopped = false;
    private int count = 0;
    boolean aBoolean = false;
    int remainingPieceCount = 6;
    int CurrentPieceIndex = 0;
    int intremaincount, intcurrentcount, intcointcount, intarray;
    int intremaingamepices = 3;
    int allint;
    private int marqueeCurrentIndex = 0;
    private int Permission_All = 1;
    int[] imageNames = new int[]{
            R.drawable.coke2,
            R.drawable.coke4,
            R.drawable.coke6,
            R.drawable.coke7,
            R.drawable.coke8,
            R.drawable.coke9,
    };
    private String acurrent, bremain, pointcount, remainpices, totalpointcount, all, grandpricetext, arraysizecocacola, correctgamescan, correctgame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        init();

        cocaColaAdLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        bremain = sharedPreference.getRemainPieceCount(getApplicationContext());
        acurrent = sharedPreference.getCurrentPieceCount(getApplicationContext());
        pointcount = sharedPreference.getCoinCount(getApplicationContext());
        remainpices = sharedPreference.getRemainGamePieces(getApplicationContext());
        grandpricetext = sharedPreference.getGrandPriceText(getApplicationContext());
        totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());
        arraysizecocacola = sharedPreference.getArraySizeCocaCola(getApplicationContext());
        all = sharedPreference.getTotalCoinCount(getApplicationContext());

        tvCoinCountFull.setText(totalpointcount);

        if (totalpointcount != null) {
            tvCoinCountFull.setText(totalpointcount);
        }

        if (grandpricetext != null) {
            tvGrandPriceText.setText(grandpricetext);

        }

        swipeTouchListener();
        checkCodeTokens();

        if (pointcount != null && remainpices != null && all != null && arraysizecocacola != null) {

            intcointcount = Integer.parseInt(pointcount);
            intarray = Integer.parseInt(arraysizecocacola);

            allint = Integer.parseInt(all);
            tvPointCount.setText(pointcount + " " + "Coins");
            String remianpices = sharedPreference.getRemainGamePieces(getApplicationContext());
            intremaingamepices = Integer.parseInt(remianpices);
            tvRemainPieces.setText(remianpices + "/9");

        } else {

            sharedPreference.saveCoinCount(getApplicationContext(), String.valueOf(intcointcount), String.valueOf(intremaingamepices));
            sharedPreference.arraySizeSave(getApplicationContext(), "5");
            tvPointCount.setText("0 Coins");
            String remianpices = sharedPreference.getRemainGamePieces(getApplicationContext());
            tvRemainPieces.setText(remianpices + "/9");

            arraysizecocacola = sharedPreference.getArraySizeCocaCola(getApplicationContext());
            intarray = Integer.parseInt(arraysizecocacola);

        }

        tokenCodeCheck();
        puzzelDragListener();
        editTextFocus();
    }

    private void editTextFocus() {

        etCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.setFocusableInTouchMode(true);
                return false;
            }
        });
    }

    private void puzzelDragListener() {

        puzzleHolder.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                // TODO Auto-generated method stub

                int action = event.getAction();
                CharSequence dragData;
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:

                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        break;
                    case DragEvent.ACTION_DROP:

                        ClipData.Item item = event.getClipData().getItemAt(0);
                        dragData = item.getText();
                        LinearLayout container = null;
                        int imageIndex = Integer.parseInt(dragData.toString());

                        switch (imageIndex) {
                            case 0:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_2);
                                break;
                            case 1:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_4);
                                break;
                            case 2:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_6);
                                break;
                            case 3:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_7);
                                break;
                            case 4:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_8);
                                break;
                            case 5:
                                container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_9);
                                break;
                            case 6:
                                //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_7);
                                break;
                            case 7:
                                //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_8);
                                break;
                            case 8:
                                //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_9);
                                break;

                            default:
                                break;
                        }

                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        ImageView img = new ImageView(getBaseContext());
                        img.setImageDrawable(getResources().getDrawable(imageNames[imageIndex]));
                        container.addView(img);
                        view.setVisibility(View.VISIBLE);

                        addgameindex(imageIndex);
                        picedelete(imageIndex + 1);

                        arraysizecocacola = sharedPreference.getArraySizeCocaCola(getApplicationContext());
                        intarray = Integer.parseInt(arraysizecocacola);

                        if (intarray == 0) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(getApplicationContext(), "You Won the game", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(CocaColaActivity.this, GameWiningActivity.class);
                                    finish();
                                    startActivity(intent);


                                    try {
                                        dbHelper.clearTabel(GameIndex.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        dbHelper.dropTabelcoin(GameIndexcoin.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        dbHelper.createTabelcoin(GameIndexcoin.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }

                                    allint++;
                                    sharedPreference.saveTotalCoinCount(getApplicationContext(), String.valueOf(allint));
                                    totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());
                                    tvCoinCountFull.setText(totalpointcount);


                                    sharedPreference.save(getApplicationContext(), String.valueOf(0), String.valueOf(6));
                                    sharedPreference.saveCoinCount(getApplicationContext(), String.valueOf(0), String.valueOf(3));
                                    sharedPreference.arraySizeSave(getApplicationContext(), "5");


                                }
                            }, 1000);


                        } else {
                            intarray--;
                            sharedPreference.arraySizeSave(getApplicationContext(), String.valueOf(intarray));
                        }
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:

                        if (!event.getResult()) {
                            ViewGroup owner2 = (ViewGroup) imgGlobal.getParent();
                            owner2.removeView(imgGlobal);
                            pieceHolder.addView(imgGlobal);
                        }

                        break;


                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void tokenCodeCheck() {
        etCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {

                    String code = etCode.getText().toString();

                    if (code.equals("c1") || code.equals("c2")) {
                        imgPadlock.setImageResource(R.drawable.lockopen);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                final Intent mainIntent = new Intent(getApplicationContext(), WinTryAgainActivity.class);
                                mainIntent.putExtra("Gamecoca", "CocaCola");
                                mainIntent.putExtra("passMessage", "Coca-Cola Game");
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Winning piece", Toast.LENGTH_LONG).show();


                                SupportLayout.setVisibility(View.GONE);
                                imgKeyIcon.setVisibility(View.VISIBLE);
                                imgPadlock.setImageResource(R.drawable.padlelock);
                                etCode.setText("");


                            }
                        }, 1000);


                    } else if (code.equals("b1") || code.equals("b2")) {

                        imgPadlock.setImageResource(R.drawable.lockopen);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                final Intent mainIntent = new Intent(getApplicationContext(), WinTryAgainActivity.class);
                                mainIntent.putExtra("bhoozt", "Bhoozt.com");
                                mainIntent.putExtra("passMessage", "Bhoozt Game");
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Winning piece", Toast.LENGTH_LONG).show();


                                SupportLayout.setVisibility(View.GONE);
                                imgKeyIcon.setVisibility(View.VISIBLE);
                                imgPadlock.setImageResource(R.drawable.padlelock);
                                etCode.setText("");

                            }
                        }, 1000);


                    } else {

                        Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_LONG).show();

                        etCode.setText("");
                        etCode.setFocusable(false);
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(etCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }


                    return true;
                }
                return false;
            }
        });
    }

    private void init() {

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(marqueeLayout, 10);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        sharedPreference = new SharedPreference();
        dbHelper = new DBHelper(this);

        tvToolbarName.setText("Bhoozt");

        intent = getIntent();
        correctgame = intent.getStringExtra("correctgame");
        correctgamescan = intent.getStringExtra("correctgamescan");
    }

    private void swipeTouchListener() {
        puzzleHolder.setOnTouchListener(new OnSwipeTouchListener(CocaColaActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (correctgame != null) {
                    if (correctgame.equals("Cocacola")) {
                        finish();
                    } else {

                        Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
                        intent.putExtra("OpengameList", "gamelist");
                        startActivity(intent);
                        finish();
                    }

                } else {

                    Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
                    intent.putExtra("OpengameList", "gamelist");
                    startActivity(intent);
                    finish();


                }
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void checkCodeTokens() {

        if (bremain != null && acurrent != null) {

            intremaincount = Integer.parseInt(bremain);
            intcurrentcount = Integer.parseInt(acurrent);

            if (correctgame != null) {
                if (correctgame.equals("Cocacola")) {

                    if (!(intremaincount < 1)) {

                        addgamecoinindex(intcurrentcount);

                        intcurrentcount++;
                        intremaincount--;

                        sharedPreference.save(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);

                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
            }

            if (correctgamescan != null) {

                if (correctgamescan.equals("gamec")) {

                    if (!(intremaincount < 1)) {
                        addgamecoinindex(intcurrentcount);

                        intcurrentcount++;
                        intremaincount--;

                        sharedPreference.save(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }
                }

            } else {

            }

        } else {
            sharedPreference.save(getApplicationContext(), String.valueOf(CurrentPieceIndex), String.valueOf(remainingPieceCount));


            acurrent = sharedPreference.getCurrentPieceCount(getApplicationContext());
            bremain = sharedPreference.getRemainPieceCount(getApplicationContext());

            intremaincount = Integer.parseInt(bremain);
            intcurrentcount = Integer.parseInt(acurrent);

            if (correctgame != null) {
                if (correctgame.equals("Cocacola")) {

                    if (!(intremaincount < 1)) {
                        addgamecoinindex(intcurrentcount);

                        intcurrentcount++;
                        intremaincount--;

                        sharedPreference.save(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }

                }
            } else {
            }

            if (correctgamescan != null) {

                if (correctgamescan.equals("gamec")) {

                    if (!(intremaincount < 1)) {

                        addgamecoinindex(intcurrentcount);

                        intcurrentcount++;
                        intremaincount--;

                        sharedPreference.save(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }
                }

            } else {

            }
        }
    }

    private void picedelete(int idddd) {

        try {
            dbHelper.deleteById(GameIndexcoin.class, idddd);
            Log.e("PICESCLEAR", "Clear" + idddd);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    public void addImagePiece(int index) {

        ImageView img = new ImageView(this);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        img.setPadding(5, 5, 5, 5);
        img.setImageDrawable(getResources().getDrawable(imageNames[index]));
        final int imageIndex = index;

        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                //view.setVisibility(View.INVISIBLE);
                imgGlobal = (ImageView) view;
                ClipData data = ClipData.newPlainText("index", "" + imageIndex);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                view.getParent();
                return true;


            }
        });
        pieceHolder.addView(img);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (advertisementsList.size() == 0) {
                cocaColaAdHeader.setText("This is the World cup -Scarcth and win");
                cocaColaAdBody.setText("Coca-Cola,Open Happiness");
                cocaColaAdLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementsList.size()) {
                        Advertisements advertisements = advertisementsList.get(finalCount);
                        cocaColaAdHeader.setText(advertisements.getAdheader());
                        cocaColaAdBody.setText(advertisements.getAdbody());
                        cocaColaAdLink.setText(advertisements.getAdlinkname());
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

    private void StartScanner() {

        Intent intent = new Intent(this, ImageTargets.class);
        startActivity(intent);

    }

    private void addgamecoinindex(int index) {

        GameIndexcoin gameIndexcoin = new GameIndexcoin();
        gameIndexcoin.setGamecoinindex(index);

        try {

            dbHelper.createOrUpdategamecoinindex(gameIndexcoin);
            Log.e("ADD", "ADD TO DB" + index);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    private void addgameindex(int index) {

        GameIndex gameIndex = new GameIndex();

        gameIndex.setGameindex(index);

        intcointcount++;
        intremaingamepices++;

        sharedPreference.saveCoinCount(getApplicationContext(), String.valueOf(intcointcount), String.valueOf(intremaingamepices));

        allint++;

        sharedPreference.saveTotalCoinCount(getApplicationContext(), String.valueOf(allint));
        pointcount = sharedPreference.getCoinCount(getApplicationContext());
        remainpices = sharedPreference.getRemainGamePieces(getApplicationContext());
        totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());

        tvCoinCountFull.setText(totalpointcount);
        tvPointCount.setText(pointcount + " " + "Coins");
        tvRemainPieces.setText(remainpices + "/9");

        try {

            dbHelper.createOrUpdategameindex(gameIndex);
            Log.e("ADD", "ADD TO DB");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public List getGameindex() {

        List mGameindexlist = new ArrayList<>();
        try {
            mGameindexlist.addAll(dbHelper.getAllGameindex(GameIndex.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return mGameindexlist;
    }


    public List getGamecoinIndex() {

        List mGamecoinindexlist = new ArrayList<>();
        try {
            mGamecoinindexlist.addAll(dbHelper.getAllGamecoinndex(GameIndexcoin.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return mGamecoinindexlist;
    }

    public void setgamecoinindex() {

        for (int i = 0; i < mGameindexcoinlist.size(); i++) {

            GameIndexcoin gameIndexcoin = mGameindexcoinlist.get(i);

            int setindex = gameIndexcoin.getGamecoinindex();

            addImagePiece(setindex);

        }
    }

    public void setgameindex() {

        for (int i = 0; i < mGameindexList.size(); i++) {

            GameIndex gameIndex = mGameindexList.get(i);

            int setindex = gameIndex.getGameindex();

            LinearLayout container = null;
            switch (setindex) {
                case 0:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_2);
                    break;
                case 1:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_4);
                    break;
                case 2:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_6);
                    break;
                case 3:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_7);
                    break;
                case 4:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_8);
                    break;
                case 5:
                    container = (LinearLayout) findViewById(R.id.act_game_puzzelholder_9);
                    break;
                case 6:
                    //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_7);
                    break;
                case 7:
                    //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_8);
                    break;
                case 8:
                    //container = (LinearLayout)findViewById(R.id.act_game_puzzelholder_9);
                    break;

                default:
                    break;
            }

            ImageView img = new ImageView(getBaseContext());
            img.setImageDrawable(getResources().getDrawable(imageNames[setindex]));
            container.addView(img);
        }

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

    @Override
    protected void onStart() {
        super.onStart();

        advertisementsList = getAllAdvertistmnets();
        activityStopped = false;
        mGameindexList = getGameindex();
        mGameindexcoinlist = getGamecoinIndex();

        if (!aBoolean) {
            setgameindex();
            setgamecoinindex();
        }
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
        aBoolean = true;
        activityStopped = true;
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @OnClick(R.id.idekey)
    public void onClickKey(View view) {

        imgKeyIcon.setVisibility(View.GONE);
        SupportLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.leftside));
        SupportLayout.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.idreltoolbar)
    public void onClickToolbar(View view) {

        etCode.setFocusable(false);
        etCode.setText("");
        SupportLayout.setVisibility(View.GONE);
        imgKeyIcon.setVisibility(View.VISIBLE);
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(etCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


    }

    @OnClick(R.id.idcamera)
    public void onClickCamera(View view) {

        String[] Permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,};
        if (!hasPermissions(CocaColaActivity.this, Permissions)) {
            ActivityCompat.requestPermissions(CocaColaActivity.this, Permissions, Permission_All);
        } else {
            ActivityCompat.requestPermissions(CocaColaActivity.this, Permissions, Permission_All);
            StartScanner();
        }
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {

        if (correctgame != null) {
            if (correctgame.equals("Cocacola")) {
                finish();
            } else {
                Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
                intent.putExtra("OpengameList", "gamelist");
                startActivity(intent);
            }

        } else {
            Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
            intent.putExtra("OpengameList", "gamelist");
            startActivity(intent);
        }
    }

    @OnClick(R.id.act_game_puzzelholder)
    public void onClickPuzzelHolder(View view) {

        etCode.setFocusable(false);
        etCode.setText("");
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        SupportLayout.setVisibility(View.GONE);
        imgKeyIcon.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "cliked", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {
        Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
        intent.putExtra("Coincount", "Coin");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (correctgame != null) {
            if (correctgame.equals("Cocacola")) {
                finish();
            } else {

                Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
                intent.putExtra("OpengameList", "gamelist");
                startActivity(intent);
            }

        } else {

            Intent intent = new Intent(CocaColaActivity.this, MainActivity.class);
            intent.putExtra("OpengameList", "gamelist");
            startActivity(intent);
        }
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


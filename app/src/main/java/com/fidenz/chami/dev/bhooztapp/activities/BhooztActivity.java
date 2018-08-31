package com.fidenz.chami.dev.bhooztapp.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fidenz.chami.dev.bhooztapp.helpers.swipetouch.OnSwipeTouchListener;
import com.fidenz.chami.dev.bhooztapp.helpers.statusbar.StatusBarColor;
import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.GameBhooztIndex;
import com.fidenz.chami.dev.bhooztapp.models.GameBhooztIndexcoin;
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

public class BhooztActivity extends AppCompatActivity implements MarqueeLayout.MarqueeAnimationCallback {

    @BindView(R.id.idekey)
    ImageView imgKeyIcon;

    @BindView(R.id.idbackarrow)
    ImageView imgArrowIcon;

    @BindView(R.id.idcamera)
    ImageView imgCameraIcon;

    @BindView(R.id.idpadlelock)
    ImageView imgPadLock;

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
    TextView tvBhooztAdHeader;

    @BindView(R.id.homeaddbody)
    TextView tvBhooztAdBody;

    @BindView(R.id.homeaddlink)
    TextView tvBhooztAdLink;

    @BindView(R.id.act_game_puzzelholder)
    RelativeLayout puzzleHolder;

    @BindView(R.id.idmarqueetext)
    RelativeLayout marqueeLayout;

    @BindView(R.id.idreltoolbar)
    RelativeLayout relToolbar;

    @BindView(R.id.support_layout)
    LinearLayout linSupportLayout;

    @BindView(R.id.act_game_pieceholder)
    LinearLayout pieceHolder;

    @BindView(R.id.idetcode)
    EditText etTokenCode;

    private ImageView imgGlobal;
    private DBHelper dbHelper;
    private List<GameBhooztIndex> mGameindexList;
    private List<GameBhooztIndexcoin> mGameindexcoinlist;
    private List<Advertisements> advertisementsList;
    private List<Tickers> tickers = new ArrayList();
    private SharedPreference sharedPreference;
    private Intent intent;
    private MarqueeLayout marquee;
    private StatusBarColor barColor;
    private TickerText tickerText;
    private Handler handler = new Handler();

    volatile boolean activityStopped = false;
    private int count = 0;
    boolean aBoolean = false;
    int remainingPieceCount = 6;
    int CurrentPieceIndex = 0;
    int intremaincount, intcurrentcount, intcointcount, intarray;
    int intremaingamepices = 3;
    int allint;
    int[] imageNames = new int[]{R.drawable.bhoozt2, R.drawable.bhoozt4, R.drawable.bhoozt6, R.drawable.bhoozt7, R.drawable.bhoozt8, R.drawable.bhoozt9,};
    private String acurrent, bremain, pointcount, remainpices, totalpointcount, all, grandpricetext, arraysizecocacola, correctgamescan, correctgame;
    private int marqueeCurrentIndex = 0;
    private int Permission_All = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhoozt);
        ButterKnife.bind(this);

        init();

        tvBhooztAdLink.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        bremain = sharedPreference.getBhooztRemainPieceCount(getApplicationContext());
        acurrent = sharedPreference.getBhooztCurrentPieceCount(getApplicationContext());
        pointcount = sharedPreference.getBhooztCoinCount(getApplicationContext());
        remainpices = sharedPreference.getBhooztRemainGamePieces(getApplicationContext());
        grandpricetext = sharedPreference.getGrandPriceText(getApplicationContext());
        arraysizecocacola = sharedPreference.getArraySizeBhoozt(getApplicationContext());
        totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());
        all = sharedPreference.getTotalCoinCount(getApplicationContext());

        tvCoinCountFull.setText(totalpointcount);

        if (totalpointcount != null) {
            tvCoinCountFull.setText(totalpointcount);
        }
        if (grandpricetext != null) {

            tvGrandPriceText.setText(grandpricetext);
        }

        if (all != null) {
            allint = Integer.parseInt(all);
        }

        swipeTouchListener();
        checkcodeToken();

        if (pointcount != null && remainpices != null && arraysizecocacola != null) {

            intcointcount = Integer.parseInt(pointcount);
            intarray = Integer.parseInt(arraysizecocacola);
            tvPointCount.setText(pointcount + " " + "Coins");
            String remianpices = sharedPreference.getBhooztRemainGamePieces(getApplicationContext());
            intremaingamepices = Integer.parseInt(remianpices);
            tvRemainPieces.setText(remianpices + "/9");

        } else {

            sharedPreference.saveBhooztCoinCount(getApplicationContext(), String.valueOf(intcointcount), String.valueOf(intremaingamepices));
            sharedPreference.arraySizeBhooztSave(getApplicationContext(), "5");
            tvPointCount.setText("0 Coins");
            String remianpices = sharedPreference.getBhooztRemainGamePieces(getApplicationContext());
            tvRemainPieces.setText(remianpices + "/9");
            arraysizecocacola = sharedPreference.getArraySizeBhoozt(getApplicationContext());
            intarray = Integer.parseInt(arraysizecocacola);
        }

        tokenCodeCheck();
        puzzelDragListener();
        editTextFocuse();

    }

    private void editTextFocuse() {
        etTokenCode.setOnTouchListener(new View.OnTouchListener() {
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
                        //v.setBackgroundDrawable(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        //v.setBackgroundDrawable(normalShape);
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


                        // Dropped, reassign View to ViewGroup
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        ImageView img = new ImageView(getBaseContext());
                        img.setImageDrawable(getResources().getDrawable(imageNames[imageIndex]));
                        container.addView(img);
                        view.setVisibility(View.VISIBLE);

                        addgameindex(imageIndex);
                        picedelete(imageIndex + 1);

                        arraysizecocacola = sharedPreference.getArraySizeBhoozt(getApplicationContext());
                        intarray = Integer.parseInt(arraysizecocacola);


                        if (intarray == 0) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {


                                    Toast.makeText(getApplicationContext(), "You Won the game", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(BhooztActivity.this, GameWiningActivity.class);
                                    finish();
                                    startActivity(intent);


                                    try {
                                        dbHelper.clearTabelBhoozt(GameBhooztIndex.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        dbHelper.dropTabebhooztlcoin(GameBhooztIndexcoin.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        dbHelper.createTabelbhooztcoin(GameBhooztIndexcoin.class);
                                    } catch (java.sql.SQLException e) {
                                        e.printStackTrace();
                                    }


                                    allint++;
                                    sharedPreference.saveTotalCoinCount(getApplicationContext(), String.valueOf(allint));
                                    totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());
                                    tvCoinCountFull.setText(totalpointcount);

                                    sharedPreference.saveBhoozt(getApplicationContext(), String.valueOf(0), String.valueOf(6));
                                    sharedPreference.saveBhooztCoinCount(getApplicationContext(), String.valueOf(0), String.valueOf(3));
                                    sharedPreference.arraySizeBhooztSave(getApplicationContext(), "5");


                                }
                            }, 1000);


                        } else {
                            intarray--;
                            sharedPreference.arraySizeBhooztSave(getApplicationContext(), String.valueOf(intarray));
                        }


                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        //v.setBackgroundDrawable(normalShape);


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
        etTokenCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_DONE) {

                    String code = etTokenCode.getText().toString();


                    if (code.equals("c1") || code.equals("c2")) {
                        imgPadLock.setImageResource(R.drawable.lockopen);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                final Intent mainIntent = new Intent(getApplicationContext(), WinTryAgainActivity.class);
                                mainIntent.putExtra("Gamecoca", "CocaCola");
                                mainIntent.putExtra("passMessage", "Coca-Cola Game");
                                startActivity(mainIntent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Winning piece", Toast.LENGTH_LONG).show();


                                linSupportLayout.setVisibility(View.GONE);
                                imgKeyIcon.setVisibility(View.VISIBLE);
                                imgPadLock.setImageResource(R.drawable.padlelock);
                                etTokenCode.setText("");


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
                                finish();
                                Toast.makeText(getApplicationContext(), "Winning piece", Toast.LENGTH_LONG).show();


                                linSupportLayout.setVisibility(View.GONE);
                                imgKeyIcon.setVisibility(View.VISIBLE);
                                imgPadLock.setImageResource(R.drawable.padlelock);
                                etTokenCode.setText("");

                            }
                        }, 1000);


                    } else {

                        Toast.makeText(getApplicationContext(), "Invalid Code", Toast.LENGTH_LONG).show();

                        etTokenCode.setText("");
                        etTokenCode.setFocusable(false);
                        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        in.hideSoftInputFromWindow(etTokenCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    }


                    return true;
                }
                return false;
            }
        });
    }

    private void swipeTouchListener() {
        puzzleHolder.setOnTouchListener(new OnSwipeTouchListener(BhooztActivity.this) {
            public void onSwipeTop() {
            }

            public void onSwipeRight() {
                if (correctgame != null) {
                    if (correctgame.equals("Bhoozt")) {
                        finish();
                    } else {
                        Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
                        intent.putExtra("OpengameList", "gamelist");
                        startActivity(intent);
                    }

                } else {

                    Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
                    intent.putExtra("OpengameList", "gamelist");
                    startActivity(intent);
                }
            }

            public void onSwipeLeft() {
            }

            public void onSwipeBottom() {
            }

        });
    }

    private void init() {
        tvToolbarName.setText("Bhoozt");

        sharedPreference = new SharedPreference();
        dbHelper = new DBHelper(this);

        barColor = new StatusBarColor(this);
        barColor.setStatusBarColour();

        marquee = new MarqueeLayout(this, this);
        marquee.setupMarquee(marqueeLayout, 10);


        intent = getIntent();
        correctgame = intent.getStringExtra("correctgame");
        correctgamescan = intent.getStringExtra("correctgamescan");
    }

    private void checkcodeToken() {


        if (bremain != null && acurrent != null) {

            intremaincount = Integer.parseInt(bremain);
            intcurrentcount = Integer.parseInt(acurrent);


            if (correctgame != null) {
                if (correctgame.equals("Bhoozt")) {

                    if (!(intremaincount < 1)) {


                        addgamebhooztcoinindex(intcurrentcount);
                        intremaincount--;
                        intcurrentcount++;
                        sharedPreference.saveBhoozt(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }

                }
            }


            if (correctgamescan != null) {
                if (correctgamescan.equals("gameb")) {

                    if (!(intremaincount < 1)) {

                        addgamebhooztcoinindex(intcurrentcount);
                        intremaincount--;
                        intcurrentcount++;
                        sharedPreference.saveBhoozt(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }
                }
            }

        } else {

            sharedPreference.saveBhoozt(getApplicationContext(), String.valueOf(CurrentPieceIndex), String.valueOf(remainingPieceCount));

            acurrent = sharedPreference.getBhooztCurrentPieceCount(getApplicationContext());
            bremain = sharedPreference.getBhooztRemainPieceCount(getApplicationContext());

            intremaincount = Integer.parseInt(bremain);
            intcurrentcount = Integer.parseInt(acurrent);


            if (correctgame != null) {
                if (correctgame.equals("Bhoozt")) {

                    if (!(intremaincount < 1)) {

                        addgamebhooztcoinindex(intcurrentcount);
                        intremaincount--;
                        intcurrentcount++;
                        sharedPreference.saveBhoozt(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);


                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }

                }
            }

            if (correctgamescan != null) {
                if (correctgamescan.equals("gameb")) {

                    if (!(intremaincount < 1)) {

                        addgamebhooztcoinindex(intcurrentcount);
                        intremaincount--;
                        intcurrentcount++;
                        sharedPreference.saveBhoozt(getApplicationContext(), "" + intcurrentcount, "" + intremaincount);

                    } else {
                        Toast.makeText(this, "no piece", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }

    private void picedelete(int idddd) {

        try {
            dbHelper.deletebhhoztById(GameBhooztIndexcoin.class, idddd);
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
//				view.setVisibility(View.INVISIBLE);
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
                tvBhooztAdHeader.setText("This is the World cup -Scarcth and win");
                tvBhooztAdBody.setText("Coca-Cola,Open Happiness");
                tvBhooztAdLink.setText("Lean more about bhooztmain here..........");

            } else {
                final int finalCount = count;
                if (finalCount <= 4) {
                    if (finalCount < advertisementsList.size()) {
                        Advertisements advertisements = advertisementsList.get(finalCount);
                        tvBhooztAdHeader.setText(advertisements.getAdheader());
                        tvBhooztAdBody.setText(advertisements.getAdbody());
                        tvBhooztAdLink.setText(advertisements.getAdlinkname());
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


    private void addgameindex(int index) {

        GameBhooztIndex gameIndex = new GameBhooztIndex();

        gameIndex.setGameindex(index);

        intcointcount++;
        intremaingamepices++;

        sharedPreference.saveBhooztCoinCount(getApplicationContext(), String.valueOf(intcointcount), String.valueOf(intremaingamepices));

        allint++;

        sharedPreference.saveTotalCoinCount(getApplicationContext(), String.valueOf(allint));
        pointcount = sharedPreference.getBhooztCoinCount(getApplicationContext());
        remainpices = sharedPreference.getBhooztRemainGamePieces(getApplicationContext());
        totalpointcount = sharedPreference.getTotalCoinCount(getApplicationContext());

        tvCoinCountFull.setText(totalpointcount);
        tvPointCount.setText(pointcount + " " + "Coins");
        tvRemainPieces.setText(remainpices + "/9");


        try {
            dbHelper.createOrUpdategamebhooztindex(gameIndex);
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
            mGameindexlist.addAll(dbHelper.getAllGamebhooztindex(GameBhooztIndex.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return mGameindexlist;
    }


    public void setgamecoinindex() {

        for (int i = 0; i < mGameindexcoinlist.size(); i++) {

            GameBhooztIndexcoin gameBhooztIndexcoin = mGameindexcoinlist.get(i);
            int setindex = gameBhooztIndexcoin.getGamecoinindex();
            addImagePiece(setindex);
        }
    }

    public void setgameindex() {

        for (int i = 0; i < mGameindexList.size(); i++) {

            GameBhooztIndex gameIndex = mGameindexList.get(i);
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

    private void addgamebhooztcoinindex(int index) {

        GameBhooztIndexcoin gameBhooztIndexcoin = new GameBhooztIndexcoin();
        gameBhooztIndexcoin.setGamecoinindex(index);

        try {

            dbHelper.createOrUpdategamebhooztcoinindex(gameBhooztIndexcoin);
            Log.e("ADD", "ADD TO DB" + index);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
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

    public List getGamecoinIndex() {

        List mGamecoinindexlist = new ArrayList<>();
        try {
            mGamecoinindexlist.addAll(dbHelper.getAllGamebhooztcoinndex(GameBhooztIndexcoin.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return mGamecoinindexlist;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (correctgame != null) {
            if (correctgame.equals("Bhoozt")) {

                Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
                intent.putExtra("OpengameList", "gamelist");
                startActivity(intent);

            } else {

                Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
                intent.putExtra("OpengameList", "gamelist");
                startActivity(intent);
            }

        } else {

            Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
            intent.putExtra("OpengameList", "gamelist");
            startActivity(intent);


        }
    }

    @OnClick(R.id.idekey)
    public void onClickKey(View view) {

        imgKeyIcon.setVisibility(View.GONE);
        linSupportLayout.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.leftside));
        linSupportLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.idreltoolbar)
    public void onClickToolbar(View view) {
        linSupportLayout.setVisibility(View.GONE);
        imgKeyIcon.setVisibility(View.VISIBLE);

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(etTokenCode.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @OnClick(R.id.idcamera)
    public void onClickCamera(View view) {
        String[] Permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,};
        if (!hasPermissions(BhooztActivity.this, Permissions)) {
            ActivityCompat.requestPermissions(BhooztActivity.this, Permissions, Permission_All);
        } else {
            ActivityCompat.requestPermissions(BhooztActivity.this, Permissions, Permission_All);
            StartScanner();
        }
    }

    @OnClick(R.id.idbackarrow)
    public void backArrowFunction(View view) {

        if (correctgame != null) {
            if (correctgame.equals("Bhoozt")) {
                finish();
            } else {

                Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
                intent.putExtra("OpengameList", "gamelist");
                startActivity(intent);
            }
        } else {

            Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
            intent.putExtra("OpengameList", "gamelist");
            startActivity(intent);
        }
    }

    @OnClick(R.id.act_game_puzzelholder)
    public void onClickPuzzelHolder(View view) {

        etTokenCode.setFocusable(false);
        etTokenCode.setText("");
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        linSupportLayout.setVisibility(View.GONE);
        imgKeyIcon.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(), "cliked", Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.idcointcoutfull)
    public void onClickCoinCount(View view) {

        Intent intent = new Intent(BhooztActivity.this, MainActivity.class);
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
}

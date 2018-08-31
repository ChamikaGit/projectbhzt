package com.fidenz.chami.dev.bhooztapp.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fidenz on 6/26/18.
 */

public class SharedPreference {


    //game1
    private String PREFS_REMAINING_PIECES = "AOP_PREFS_REMAININGPICES";
    private String PREFS_KEY_REMAINING_PIECES = "AOP_PREFS_REMAIN";

    private String PREFS_CURRENT_PIECES = "AOP_CURRENTPICES";
    private String PREFS_KEY_CURRENT_PIECES = "AOP_PREFS_CURRENT";

    private String PREFS_COIN_COUNT = "AOP_COINCOUNT";
    private String PREFS_KEY_COIN_COUNT = "AOP_PREFS_COINCOUNT";

    private String PREFS_REMAIN_PIECES = "AOP_PICES";
    private String PREFS_KEY_REAMING_PIECES = "AOP_PREFS_PICES";

    private String PREFS_ARRAY_SIZE = "AOP_ARRAYSIZE";
    private String PREFS_KEY_ARRAY_SIZE = "AOP_PREFS_ARRAYSIZE";


    //game2
    private String PREFS_BHOOZT_REMAINING_PIECES = "AOP_PREFS_BHOOZTREMAININGPICES";
    private String PREFS_BHOOZT_KEY_REMAINING_PIECES = "AOP_PREFS_BHOOZTREMAIN";

    private String PREFS_BHOOZT_CURRENT_PIECES = "AOP_BHOOZTCURRENTPICES";
    private String PREFS_BHOOZT_KEY_CURRENT_PIECES = "AOP_PREFS_BHOOZTCURRENT";

    private String PREFS_BHOOZT_COIN_COUNT = "AOP_BHOOZTCOINCOUNT";
    private String PREFS_BHOOZT_KEY_COIN_COUNT = "AOP_PREFS_BHOOZTCOINCOUNT";

    private String PREFS_BHOOZT_REMAIN_PIECES = "AOP_BHOOZTPICES";
    private String PREFS_BHOOZT_KEY_REAMING_PIECES = "AOP_PREFS_BHOOZTPICES";

    private String PREFS_ARRAY_SIZE2 = "AOP_ARRAYSIZE2";
    private String PREFS_KEY_ARRAY_SIZE2 = "AOP_PREFS_ARRAYSIZE2";


    //////////admintext
    private String PREFS_RESULT_HEADER = "AOP_RESULTHEADER";
    private String PREFS_KEY_RESULT_HEADER = "AOP_PREFS_RESULTHEADER";

    private String PREFS_RESULT_TEXT = "AOP_RESULTTEXT";
    private String PREFS_KEY_RESULT_TEXT = "AOP_PREFS_RESULTTEXT";

    private String PREFS_WIN_TEXT = "AOP_WINTEXT";
    private String PREFS_KEY_WIN_TEXT = "AOP_PREFS_WINTEXT";

    private String PREFS_LOOSE_TEXT = "AOP_LOOSETEXT";
    private String PREFS_KEY_LOOSE_TEXT = "AOP_PREFS_LOOSETEXT";

    private String PREFS_GRAND_PRICE_TEXT = "AOP_GRANDPRICETEXT";
    private String PREFS_KEY_GRAND_PRICE_TEXT = "AOP_PREFS_GRANDPRICETEXT";

    private String PREFS_WINING_TEXT = "AOP_WININGTEXT";
    private String PREFS_KEY_WINING_TEXT = "AOP_PREFS_WININGTEXT";

    //totalcoincount
    private String PREFS_TOTAL_COIN_COUNT = "AOP_TOTALCOINCOUNT";
    private String PREFS_KEY_TOTAL_COIN_COUNT = "AOP_PREFS_TOTALCOINCOUNT";


    public SharedPreference() {

        super();
    }

    public void save(Context context, String currentpicecount, String remainpicecount) {

        SharedPreferences mcurrentpicescount, mremainingpicescount;
        SharedPreferences.Editor editor;

        mremainingpicescount = context.getSharedPreferences(PREFS_REMAINING_PIECES, Context.MODE_PRIVATE); //1
        mcurrentpicescount = context.getSharedPreferences(PREFS_CURRENT_PIECES, Context.MODE_PRIVATE);

        editor = mcurrentpicescount.edit();
        editor.putString(PREFS_KEY_CURRENT_PIECES, currentpicecount);
        editor.commit();

        editor = mremainingpicescount.edit();
        editor.putString(PREFS_KEY_REMAINING_PIECES, remainpicecount);
        editor.commit();
    }

    public void saveCoinCount(Context context, String coincount, String remainpices) {

        SharedPreferences mcoincount, mremainpices;
        SharedPreferences.Editor editor;

        mcoincount = context.getSharedPreferences(PREFS_COIN_COUNT, Context.MODE_PRIVATE);
        mremainpices = context.getSharedPreferences(PREFS_REMAIN_PIECES, Context.MODE_PRIVATE);

        editor = mcoincount.edit();
        editor.putString(PREFS_KEY_COIN_COUNT, coincount);
        editor.commit();

        editor = mremainpices.edit();
        editor.putString(PREFS_KEY_REAMING_PIECES, remainpices);
        editor.commit();
    }

    public void saveBhoozt(Context context, String currentpicecount, String remainpicecount) {

        SharedPreferences mcurrentpicescount, mremainingpicescount;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        mremainingpicescount = context.getSharedPreferences(PREFS_BHOOZT_REMAINING_PIECES, Context.MODE_PRIVATE); //1
        mcurrentpicescount = context.getSharedPreferences(PREFS_BHOOZT_CURRENT_PIECES, Context.MODE_PRIVATE);

        editor = mcurrentpicescount.edit();
        editor.putString(PREFS_BHOOZT_KEY_CURRENT_PIECES, currentpicecount);
        editor.commit();

        editor = mremainingpicescount.edit();
        editor.putString(PREFS_BHOOZT_KEY_REMAINING_PIECES, remainpicecount);
        editor.commit();
    }

    public void saveBhooztCoinCount(Context context, String coincount, String remainpices) {

        SharedPreferences mcoincount, mremainpices;
        SharedPreferences.Editor editor;

        mcoincount = context.getSharedPreferences(PREFS_BHOOZT_COIN_COUNT, Context.MODE_PRIVATE);
        mremainpices = context.getSharedPreferences(PREFS_BHOOZT_REMAIN_PIECES, Context.MODE_PRIVATE);

        editor = mcoincount.edit();
        editor.putString(PREFS_BHOOZT_KEY_COIN_COUNT, coincount);
        editor.commit();

        editor = mremainpices.edit();
        editor.putString(PREFS_BHOOZT_KEY_REAMING_PIECES, remainpices);
        editor.commit();
    }

    public void arraySizeSave(Context context, String arraysize) {

        SharedPreferences marrasize;
        SharedPreferences.Editor editor;

        marrasize = context.getSharedPreferences(PREFS_ARRAY_SIZE, Context.MODE_PRIVATE);

        editor = marrasize.edit();
        editor.putString(PREFS_KEY_ARRAY_SIZE, arraysize);
        editor.commit();
    }


    public void arraySizeBhooztSave(Context context, String arraysizebhoozt) {

        SharedPreferences marrasize2;
        SharedPreferences.Editor editor;

        marrasize2 = context.getSharedPreferences(PREFS_ARRAY_SIZE2, Context.MODE_PRIVATE);

        editor = marrasize2.edit();
        editor.putString(PREFS_KEY_ARRAY_SIZE2, arraysizebhoozt);
        editor.commit();
    }


    ////////////////

    public void saveResultText(Context context, String resultheader, String resulttext) {

        SharedPreferences mresultheader, mresulttext;
        SharedPreferences.Editor editor;

        mresultheader = context.getSharedPreferences(PREFS_RESULT_HEADER, Context.MODE_PRIVATE);
        mresulttext = context.getSharedPreferences(PREFS_RESULT_TEXT, Context.MODE_PRIVATE);

        editor = mresultheader.edit();
        editor.putString(PREFS_KEY_RESULT_HEADER, resultheader);
        editor.commit();

        editor = mresulttext.edit();
        editor.putString(PREFS_KEY_RESULT_TEXT, resulttext);
        editor.commit();
    }


    public void saveWinLooseText(Context context, String wintext, String loosetext, String winingtext) {

        SharedPreferences mwintext, mloosetext, mwiningtext;
        SharedPreferences.Editor editor;

        mwintext = context.getSharedPreferences(PREFS_WIN_TEXT, Context.MODE_PRIVATE);
        mloosetext = context.getSharedPreferences(PREFS_LOOSE_TEXT, Context.MODE_PRIVATE);
        mwiningtext = context.getSharedPreferences(PREFS_WINING_TEXT, Context.MODE_PRIVATE);

        editor = mwintext.edit();
        editor.putString(PREFS_KEY_WIN_TEXT, wintext);
        editor.commit();

        editor = mloosetext.edit();
        editor.putString(PREFS_KEY_LOOSE_TEXT, loosetext);
        editor.commit();


        editor = mwiningtext.edit();
        editor.putString(PREFS_KEY_WINING_TEXT, winingtext);
        editor.commit();
    }


    public void saveGrandPriceText(Context context, String gandpricetext) {

        SharedPreferences mgarandpricetext;
        SharedPreferences.Editor editor;

        mgarandpricetext = context.getSharedPreferences(PREFS_GRAND_PRICE_TEXT, Context.MODE_PRIVATE);

        editor = mgarandpricetext.edit();
        editor.putString(PREFS_KEY_GRAND_PRICE_TEXT, gandpricetext);
        editor.commit();
    }

    public void saveTotalCoinCount(Context context, String totalcoint) {

        SharedPreferences mtotalcoint;
        SharedPreferences.Editor editor;

        mtotalcoint = context.getSharedPreferences(PREFS_TOTAL_COIN_COUNT, Context.MODE_PRIVATE);

        editor = mtotalcoint.edit();
        editor.putString(PREFS_KEY_TOTAL_COIN_COUNT, totalcoint);
        editor.commit();
    }


    ////////////////////////////////

    public String getCurrentPieceCount(Context context) {
        SharedPreferences mcurrentpicescount;
        String mcurrenttext;
        mcurrentpicescount = context.getSharedPreferences(PREFS_CURRENT_PIECES, Context.MODE_PRIVATE); //1
        mcurrenttext = mcurrentpicescount.getString(PREFS_KEY_CURRENT_PIECES, null); //2
        return mcurrenttext;
    }

    public String getRemainPieceCount(Context context) {
        SharedPreferences mremainingpicescount;
        String mremaintext;
        mremainingpicescount = context.getSharedPreferences(PREFS_REMAINING_PIECES, Context.MODE_PRIVATE); //1
        mremaintext = mremainingpicescount.getString(PREFS_KEY_REMAINING_PIECES, null); //2
        return mremaintext;
    }


    public String getCoinCount(Context context) {
        SharedPreferences mcoincount;
        String mcointext;
        mcoincount = context.getSharedPreferences(PREFS_COIN_COUNT, Context.MODE_PRIVATE); //1
        mcointext = mcoincount.getString(PREFS_KEY_COIN_COUNT, null); //2
        return mcointext;
    }

    public String getRemainGamePieces(Context context) {
        SharedPreferences mremainpicescount;
        String mremainpicestext;
        mremainpicescount = context.getSharedPreferences(PREFS_REMAIN_PIECES, Context.MODE_PRIVATE); //1
        mremainpicestext = mremainpicescount.getString(PREFS_KEY_REAMING_PIECES, null); //2
        return mremainpicestext;
    }

    //////////////////////////////////////////////////////////////////////////

    public String getBhooztCurrentPieceCount(Context context) {
        SharedPreferences mcurrentpicescount;
        String mcurrenttext;
        mcurrentpicescount = context.getSharedPreferences(PREFS_BHOOZT_CURRENT_PIECES, Context.MODE_PRIVATE); //1
        mcurrenttext = mcurrentpicescount.getString(PREFS_BHOOZT_KEY_CURRENT_PIECES, null); //2
        return mcurrenttext;
    }

    public String getBhooztRemainPieceCount(Context context) {
        SharedPreferences mremainingpicescount;
        String mremaintext;
        mremainingpicescount = context.getSharedPreferences(PREFS_BHOOZT_REMAINING_PIECES, Context.MODE_PRIVATE); //1
        mremaintext = mremainingpicescount.getString(PREFS_BHOOZT_KEY_REMAINING_PIECES, null); //2
        return mremaintext;
    }


    public String getBhooztCoinCount(Context context) {
        SharedPreferences mcoincount;
        String mcointext;
        mcoincount = context.getSharedPreferences(PREFS_BHOOZT_COIN_COUNT, Context.MODE_PRIVATE); //1
        mcointext = mcoincount.getString(PREFS_BHOOZT_KEY_COIN_COUNT, null); //2
        return mcointext;
    }

    public String getBhooztRemainGamePieces(Context context) {
        SharedPreferences mremainpicescount;
        String mremainpicestext;
        mremainpicescount = context.getSharedPreferences(PREFS_BHOOZT_REMAIN_PIECES, Context.MODE_PRIVATE); //1
        mremainpicestext = mremainpicescount.getString(PREFS_BHOOZT_KEY_REAMING_PIECES, null); //2
        return mremainpicestext;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String getResultHeader(Context context) {
        SharedPreferences mresltheader;
        String mresultheader;
        mresltheader = context.getSharedPreferences(PREFS_RESULT_HEADER, Context.MODE_PRIVATE); //1
        mresultheader = mresltheader.getString(PREFS_KEY_RESULT_HEADER, null); //2
        return mresultheader;
    }

    public String getResultText(Context context) {
        SharedPreferences mresulttext;
        String mmreslttext;
        mresulttext = context.getSharedPreferences(PREFS_RESULT_TEXT, Context.MODE_PRIVATE); //1
        mmreslttext = mresulttext.getString(PREFS_KEY_RESULT_TEXT, null); //2
        return mmreslttext;
    }

    public String getWinText(Context context) {
        SharedPreferences mwintext;
        String mresultwin;
        mwintext = context.getSharedPreferences(PREFS_WIN_TEXT, Context.MODE_PRIVATE); //1
        mresultwin = mwintext.getString(PREFS_KEY_WIN_TEXT, null); //2
        return mresultwin;
    }

    public String getLooseText(Context context) {
        SharedPreferences mloosetext;
        String mmresltloose;
        mloosetext = context.getSharedPreferences(PREFS_LOOSE_TEXT, Context.MODE_PRIVATE); //1
        mmresltloose = mloosetext.getString(PREFS_KEY_LOOSE_TEXT, null); //2
        return mmresltloose;
    }

    public String getWiningText(Context context) {
        SharedPreferences mwiningtext;
        String mmwiningtext;
        mwiningtext = context.getSharedPreferences(PREFS_WINING_TEXT, Context.MODE_PRIVATE); //1
        mmwiningtext = mwiningtext.getString(PREFS_KEY_WINING_TEXT, null); //2
        return mmwiningtext;
    }


    public String getGrandPriceText(Context context) {
        SharedPreferences mgrandpricetext;
        String mmgrandpricetext;
        mgrandpricetext = context.getSharedPreferences(PREFS_GRAND_PRICE_TEXT, Context.MODE_PRIVATE); //1
        mmgrandpricetext = mgrandpricetext.getString(PREFS_KEY_GRAND_PRICE_TEXT, null); //2
        return mmgrandpricetext;
    }

    public String getTotalCoinCount(Context context) {
        SharedPreferences mTotalcoincount;
        String mmTotalcoincount;
        mTotalcoincount = context.getSharedPreferences(PREFS_TOTAL_COIN_COUNT, Context.MODE_PRIVATE); //1
        mmTotalcoincount = mTotalcoincount.getString(PREFS_KEY_TOTAL_COIN_COUNT, null); //2
        return mmTotalcoincount;
    }


    public String getArraySizeCocaCola(Context context) {

        SharedPreferences mArraysize;
        String mmArraysize;

        mArraysize = context.getSharedPreferences(PREFS_ARRAY_SIZE, Context.MODE_PRIVATE);
        mmArraysize = mArraysize.getString(PREFS_KEY_ARRAY_SIZE, null);
        return mmArraysize;

    }


    public String getArraySizeBhoozt(Context context) {

        SharedPreferences mArraysize2;
        String mmArraysize2;

        mArraysize2 = context.getSharedPreferences(PREFS_ARRAY_SIZE2, Context.MODE_PRIVATE);
        mmArraysize2 = mArraysize2.getString(PREFS_KEY_ARRAY_SIZE2, null);
        return mmArraysize2;

    }
}

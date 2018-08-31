package com.fidenz.chami.dev.bhooztapp.helpers.TickerText;

import android.content.Context;
import android.database.SQLException;

import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.fidenz.chami.dev.bhooztapp.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fidenz on 6/29/18.
 */

public final class TickerText {

    public static List<Tickers> tickersList = null;
    public static StringBuffer tickertext = new StringBuffer();
    public static DBHelper dbHelper;
    public static Context context;


    public TickerText(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
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


    public String getticketext() {

        tickersList = getAllTickers();

        for (int i = 0; i < tickersList.size(); i++) {

            Tickers tickers = tickersList.get(i);

            tickertext.append(tickers.getTickerText()).append("                    ");
        }

        return String.valueOf(tickertext);

    }

}


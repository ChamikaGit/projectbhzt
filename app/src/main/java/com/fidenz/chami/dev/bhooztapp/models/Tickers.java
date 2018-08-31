package com.fidenz.chami.dev.bhooztapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fidenz on 6/22/18.
 */

@DatabaseTable(tableName = "Tickers")
public class Tickers {

    @DatabaseField(columnName ="id",generatedId = true)
    private int id;

    @DatabaseField(columnName ="TickerText")
    private String tickerText;

    public Tickers() {
    }

    public Tickers(int id, String tickerText) {
        this.id = id;
        this.tickerText = tickerText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTickerText() {
        return tickerText;
    }

    public void setTickerText(String tickerText) {
        this.tickerText = tickerText;
    }
}

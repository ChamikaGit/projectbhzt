package com.fidenz.chami.dev.bhooztapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fidenz on 7/12/18.
 */

@DatabaseTable(tableName = "GameIndexcoin")
public class GameIndexcoin {

    @DatabaseField(columnName ="id",generatedId =true)
    private int id;

    @DatabaseField(columnName ="GamecoinIndex")
    private int gamecoinindex;

    public GameIndexcoin() {
    }

    public GameIndexcoin(int id, int gamecoinindex) {
        this.id = id;
        this.gamecoinindex = gamecoinindex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGamecoinindex() {
        return gamecoinindex;
    }

    public void setGamecoinindex(int gamecoinindex) {
        this.gamecoinindex = gamecoinindex;
    }
}

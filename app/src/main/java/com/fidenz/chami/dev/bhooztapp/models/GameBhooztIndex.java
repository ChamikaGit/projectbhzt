package com.fidenz.chami.dev.bhooztapp.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by fidenz on 6/26/18.
 */

@DatabaseTable(tableName = "GameBhooztIndex")
public class GameBhooztIndex {

    @DatabaseField(columnName ="id",generatedId = true)
    private int id;

    @DatabaseField(columnName ="GameIndex")
    private int gameindex;

    public GameBhooztIndex() {
    }

    public GameBhooztIndex(int id, int gameindex) {
        this.id = id;
        this.gameindex = gameindex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameindex() {
        return gameindex;
    }

    public void setGameindex(int gameindex) {
        this.gameindex = gameindex;
    }
}

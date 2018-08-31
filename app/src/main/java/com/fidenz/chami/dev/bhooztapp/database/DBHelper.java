package com.fidenz.chami.dev.bhooztapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fidenz.chami.dev.bhooztapp.models.Advertisements;
import com.fidenz.chami.dev.bhooztapp.models.GameBhooztIndex;
import com.fidenz.chami.dev.bhooztapp.models.GameBhooztIndexcoin;
import com.fidenz.chami.dev.bhooztapp.models.GameIndex;
import com.fidenz.chami.dev.bhooztapp.models.GameIndexcoin;
import com.fidenz.chami.dev.bhooztapp.models.Tickers;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fidenz on 6/21/18.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "bhooztmain.db";
    private static final int DB_VERSION = 1;
    private ConnectionSource cs;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {

            // Create Table with given table name with columnName
            TableUtils.createTable(connectionSource, Advertisements.class);
            TableUtils.createTable(connectionSource, Tickers.class);
            TableUtils.createTable(connectionSource, GameIndex.class);
            TableUtils.createTable(connectionSource, GameBhooztIndex.class);
            TableUtils.createTable(connectionSource, GameIndexcoin.class);
            TableUtils.createTable(connectionSource, GameBhooztIndexcoin.class);
            cs=connectionSource;

        } catch (android.database.SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    public List getAll(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<Advertisements, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();

    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Advertisements obj) throws SQLException, java.sql.SQLException {
        Dao<Advertisements, ?> dao = null;
        try {
            dao = (Dao<Advertisements, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }

    //deletegamecoin

    public  int deleteById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
        Dao<GameIndexcoin, Object> dao = null;
        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.deleteById(aId);

    }

    //deletegamebhooztcoin

    public  int deletebhhoztById(Class clazz, Object aId) throws SQLException, java.sql.SQLException {
        Dao<GameBhooztIndexcoin, Object> dao = null;
        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.deleteById(aId);

    }
    /////////////////////////////////////////////////////////////////////


    public List getAllTickers(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<Tickers, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();

    }

    public Dao.CreateOrUpdateStatus createOrUpdateTickers(Tickers obj) throws SQLException, java.sql.SQLException {
        Dao<Tickers, ?> dao = null;
        try {
            dao = (Dao<Tickers, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }


    //////////////////////////////////////////////////////////////////

    public List getAllGameindex(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<GameIndex, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();

    }

    public Dao.CreateOrUpdateStatus createOrUpdategameindex(GameIndex obj) throws SQLException, java.sql.SQLException {
        Dao<GameIndex, ?> dao = null;
        try {
            dao = (Dao<GameIndex, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }

    ///game2////////////////////////////////////////


    public List getAllGamebhooztindex(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<GameBhooztIndex, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();

    }

    public Dao.CreateOrUpdateStatus createOrUpdategamebhooztindex(GameBhooztIndex obj) throws SQLException, java.sql.SQLException {
        Dao<GameBhooztIndex, ?> dao = null;
        try {
            dao = (Dao<GameBhooztIndex, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }

    ////////////////////////////////////////////////////////////

//
//    public  int deletegameindex(Class clazz) throws android.database.SQLException, java.sql.SQLException {
//        Dao<GameIndex,?> dao = null;
//        try {
//            dao = getDao(clazz);
//        } catch (java.sql.SQLException e) {
//            e.printStackTrace();
//        }
//
//        return dao.;
//
//    }


    public void clearTabel(Class<GameIndex> tableConfig) throws SQLException {

        TableUtils.clearTable(getConnectionSource(),tableConfig);

    }


    public void clearTabelBhoozt(Class<GameBhooztIndex> tableConfig) throws SQLException {

        TableUtils.clearTable(getConnectionSource(),tableConfig);

    }

    public void dropTabelcoin(Class<GameIndexcoin> tableConfig) throws SQLException {

        //TableUtils.clearTable(getConnectionSource(),tableConfig);
        TableUtils.dropTable(getConnectionSource(),tableConfig,false);

    }

    public void createTabelcoin(Class<GameIndexcoin> tableConfig) throws SQLException {

        //TableUtils.clearTable(getConnectionSource(),tableConfig);
        TableUtils.createTableIfNotExists(getConnectionSource(),tableConfig);

    }

    ////bhoozt

    public void dropTabebhooztlcoin(Class<GameBhooztIndexcoin> tableConfig) throws SQLException {

        //TableUtils.clearTable(getConnectionSource(),tableConfig);
        TableUtils.dropTable(getConnectionSource(),tableConfig,false);

    }

    public void createTabelbhooztcoin(Class<GameBhooztIndexcoin> tableConfig) throws SQLException {

        //TableUtils.clearTable(getConnectionSource(),tableConfig);
        TableUtils.createTableIfNotExists(getConnectionSource(),tableConfig);

    }




    //gamehorizontall

    public List getAllGamecoinndex(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<GameIndexcoin, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();
    }

    public Dao.CreateOrUpdateStatus createOrUpdategamecoinindex(GameIndexcoin obj) throws SQLException, java.sql.SQLException {

        Dao<GameIndexcoin, ?> dao = null;
        try {
            dao = (Dao<GameIndexcoin, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }



    //gamebhoozthorizontall

    public List getAllGamebhooztcoinndex(Class clazz) throws SQLException,java.sql.SQLException {

        Dao<GameBhooztIndexcoin, ?> dao = null;

        try {
            dao = getDao(clazz);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.queryForAll();
    }

    public Dao.CreateOrUpdateStatus createOrUpdategamebhooztcoinindex(GameBhooztIndexcoin obj) throws SQLException, java.sql.SQLException {

        Dao<GameBhooztIndexcoin, ?> dao = null;
        try {
            dao = (Dao<GameBhooztIndexcoin, ?>) getDao(obj.getClass());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        return dao.createOrUpdate(obj);

    }



}

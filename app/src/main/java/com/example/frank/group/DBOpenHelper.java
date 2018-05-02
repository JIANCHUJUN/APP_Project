package com.example.frank.group;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JiachengYe on 3/10/2018.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "StockList";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "stocks";
    public static final String COLUMN_SYMBOL = "symbol";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_EXCHANGE = "exchange";
    public static final String COLUMN_INDUSTRY="industry";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CEO = "ceo";
    public static final String COLUMN_ISSUETYPE = "issuetype";
    public static final String COLUMN_SECTOR = "sector";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_NUMBER = "number";

    public static final String HISTORY_TABLE = "history";
    public static final String COLUMN_TYPE = "type"; // buy or sell
    public static final String COLUMN_TOTAL = "total";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_SYMBOL + " TEXT PRIMARY KEY," +
            COLUMN_COMPANY_NAME + " INT," +
            COLUMN_EXCHANGE + " TEXT," +
            COLUMN_INDUSTRY + " TEXT," +
            COLUMN_WEBSITE + " TEXT," +
            COLUMN_DESCRIPTION + " TEXT," +
            COLUMN_CEO + " TEXT," +
            COLUMN_ISSUETYPE + " TEXT," +
            COLUMN_SECTOR + " TEXT," +
            COLUMN_NUMBER + " INTEGER," +
            COLUMN_PRICE + " FLOAT" +
            ")";
    private static final String SQL_CREATE_HISTORY =  "CREATE TABLE " + HISTORY_TABLE +
            "(" + COLUMN_SYMBOL + " TEXT," + //symbol
            COLUMN_TYPE + " FLOAT," + // buy or sell
            COLUMN_PRICE + " FLOAT," + //the price when buying or selling
            COLUMN_NUMBER + " INTERGER," + // how many stock buy or sell
            COLUMN_TOTAL + " FLOAT" + // total money earned or corsted
            ")";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HISTORY_TABLE);
        onCreate(sqLiteDatabase);
    }



}

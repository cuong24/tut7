package com.example.tut7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Currency.DB";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    public static final String TABLE_NAME = "Currency";
    public static final String ID = "_id";
    public static final String COUNTRY = "country";
    public static final String CURRENCY = "currency";
    public static final String CREATE_TABLE_QL =
            " create table " + TABLE_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COUNTRY + " TEXT NOT NULL, "
            + CURRENCY + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

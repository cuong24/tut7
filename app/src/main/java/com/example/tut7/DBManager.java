package com.example.tut7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DatabaseHelper databaseHelper;
    private Context context;
    private SQLiteDatabase database;
    public DBManager(Context c){
        context = c;
    }

    public DBManager open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void insert(String countryName, String currency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COUNTRY, countryName);
        contentValues.put(DatabaseHelper.CURRENCY, currency);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public int update(int id,String countryName, String currency) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COUNTRY, countryName);
        contentValues.put(DatabaseHelper.CURRENCY, currency);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
        return database.update(DatabaseHelper.TABLE_NAME, contentValues,DatabaseHelper.ID + "=" + id, null);
    }

    public int delete(int id) {
        return database.delete(DatabaseHelper.TABLE_NAME,DatabaseHelper.ID + "=" + id, null);
    }

    public Cursor fetch() {
        String[] columns = new String[] {
                DatabaseHelper.ID,
                DatabaseHelper.COUNTRY,
                DatabaseHelper.CURRENCY
        };

        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }

        return cursor;
    }

    public void finalize() throws Throwable {
        if(null != databaseHelper)
            databaseHelper.close();
        if(null != database)
            database.close();
        super.finalize();
    }

}

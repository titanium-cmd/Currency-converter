package com.example.currencyconverter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "currencies_database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "currencies_table";
    public static final String CURRENCY_ID = "_id";
    public static final String CURRENCY_NAME = "currency_name";
    public static final String CURRENCY_CODE = "currency_code";
    private SQLiteDatabase database;
    private int currencyIdPos;
    private int currencyNamePos;
    private int currencyCodePos;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableCreation = "CREATE TABLE "+ TABLE_NAME + " ( " +
                CURRENCY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                CURRENCY_NAME +" TEXT NOT NULL, " +
                CURRENCY_CODE +" TEXT NOT NULL);";
        db.execSQL(tableCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    //Methods associated with the table...
    public void addCurrency(JsonObject currencies){
        ContentValues values = new ContentValues();

        database.close();
    }

    public void deleteStudent(String rowid){
        database = getWritableDatabase();
        String clause = CURRENCY_ID + " = ?";
        String[] args = {rowid};
        database.delete(TABLE_NAME, clause, args);
    }

    public ArrayList<Currencies> getAllCurrencies(){
        database = getReadableDatabase();
        String[] column = {CURRENCY_ID, CURRENCY_NAME, CURRENCY_CODE};
        Cursor cursor = database.query(true, TABLE_NAME, column, null, null, null, null, null, null);

        currencyIdPos = cursor.getColumnIndex(CURRENCY_ID);
        currencyNamePos = cursor.getColumnIndex(CURRENCY_NAME);
        currencyCodePos = cursor.getColumnIndex(CURRENCY_CODE);

        ArrayList<Currencies> currencyDetails = new ArrayList<>();
        while(cursor.moveToNext()){
            Currencies currencies = new Currencies();
            currencies.setCurrencyId(String.valueOf(cursor.getInt(currencyIdPos)));
            currencies.setCurrencyName(cursor.getString(currencyNamePos));
            currencies.setCurrencyCode(cursor.getString(currencyCodePos));

            currencyDetails.add(currencies);
        }
        cursor.close();
        return currencyDetails;
    }
}

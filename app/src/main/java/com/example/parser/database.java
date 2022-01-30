package com.example.parser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class database extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "db";
    public static final String DB_TYPE = "crypto";

    public static final String TITLE = "text";
    public static final String PRICE = "value";


    public database(Context context){ super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + DB_TYPE + "(" + TITLE + " text," + PRICE + " text " + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + DB_TYPE);
        onCreate(db);
    }
}


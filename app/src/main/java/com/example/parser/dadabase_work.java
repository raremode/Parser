package com.example.parser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class dadabase_work {
private database cryptobase;
private ContentValues cv;
private SQLiteDatabase db;

private Integer index, index2;
public Cursor cursor,  cursor2;

public dadabase_work(Context context){
        cryptobase = new database(context);
        db = cryptobase.getWritableDatabase();
        cv = new ContentValues();
}

        public void inserts(String name, String value){
                cv.put(database.PRICE, value);
                db.update(database.DB_TYPE, cv, "value = ? ", new String[] {value});

                cv.put(database.TITLE, name);
                db.insert(database.DB_TYPE, null, cv);
        }

        public ArrayList<String> read_DB(){
        ArrayList<String> alist=new ArrayList<>();
        cursor = db.query(database.DB_TYPE, null, null, null, null, null, null);
        cursor2 = db.query(database.DB_TYPE, null, null, null, null, null, null);
        while (cursor.moveToNext() && cursor2.moveToNext()){
                index = cursor.getColumnIndex(database.TITLE);
                index2 = cursor2.getColumnIndex(database.PRICE);
                alist.add(cursor.getString(index) + ": " + cursor2.getString(index2));
        }
        return alist;
        }

        public void cleaner(){
                db.delete(database.DB_TYPE, null, null);
        }
}


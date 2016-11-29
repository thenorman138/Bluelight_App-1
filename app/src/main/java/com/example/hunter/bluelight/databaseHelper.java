package com.example.hunter.bluelight;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hunter on 11/27/16.
 */

public  class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "userData";
    public static final String COL_1 = "UTCID";
    public static final String COL_2 = "PASS";
    public static final String COL_3 = "FIRST_NAME";
    public static final String COL_4 = "LAST_NAME";

    public databaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (UTCID TEXT PRIMARY KEY, PASS TEXT, FIRST_NAME TEXT, LAST_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData (String utcid, String pass, String first_name, String last_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, utcid);
        contentValues.put(COL_2, pass);
        contentValues.put(COL_3, first_name);
        contentValues.put(COL_4, last_name);
        long result = db.insert(TABLE_NAME, null, contentValues );
            if (result== -1)
                return false;
            else
                return true;
    }


}

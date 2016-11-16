package com.example.hong.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2016-11-13.
 */
public class Database extends SQLiteOpenHelper{
    public Database(Context context) {
        super(context, "db_table", null, 1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE db_table"
                + "(id integer primary key autoincrement, "
                + "date TEXT, "
                + "time TEXT, "
                + "address TEXT, "
                + "x REAL, "
                + "y REAL, "
                + "story TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS db_table");
        onCreate(db);
    }


}

package com.spider.bookmyroom.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlDataBase extends SQLiteOpenHelper {

    static final String TABLE_NAME = "CONTROL_DATABASE";
    // Table columns
    static final String _ID = "_id";
    static final String USER_NAME = "user_name";
    static final String USER_ADD = "user_add";
    static final String USER_ROOM_ID = "user_room_id";
    static final String USER_DAY = "user_from_day";
    // Database Information
    private static final String DB_NAME = "CONTROL.DB";
    // database version
    private static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT , " + USER_NAME + " TEXT NOT NULL , " + USER_ADD + " TEXT NOT NULL ," + USER_ROOM_ID + " TEXT NOT NULL ," + USER_DAY + " TEXT NOT NULL);";

    public ControlDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor readAllData(String room_id){
        String query = "SELECT * FROM  CONTROL_DATABASE WHERE user_room_id = "+ room_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}

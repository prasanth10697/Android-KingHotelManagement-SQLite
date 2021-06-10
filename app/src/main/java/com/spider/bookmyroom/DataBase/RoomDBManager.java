package com.spider.bookmyroom.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RoomDBManager {

    private final Context context;
    private SQLiteDatabase database;

    public RoomDBManager(Context c) {
        context = c;
    }

    //database open
    public void open() throws SQLException {
        RoomDataBase dbHelper = new RoomDataBase(context);
        database = dbHelper.getWritableDatabase();
    }

    //update in database
    public void update(long _id, String active_status) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RoomDataBase.ACTIVE_STATUS, active_status);
        int i = database.update(RoomDataBase.TABLE_NAME, contentValues, RoomDataBase._ID + " = " + _id, null);
    }

}

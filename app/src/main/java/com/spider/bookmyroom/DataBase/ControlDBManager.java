package com.spider.bookmyroom.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class ControlDBManager {

    private final Context context;
    private SQLiteDatabase database;

    public ControlDBManager(Context c) {
        context = c;
    }

    //database open
    public void open() throws SQLException {
        ControlDataBase dbHelper = new ControlDataBase(context);
        database = dbHelper.getWritableDatabase();
    }

    //insert in database
    public void insert(String name, String address, String room_id, String from_date) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(ControlDataBase.USER_NAME, name);
        contentValue.put(ControlDataBase.USER_ADD, address);
        contentValue.put(ControlDataBase.USER_ROOM_ID, room_id);
        contentValue.put(ControlDataBase.USER_DAY, from_date);
        long result= database.insert(ControlDataBase.TABLE_NAME, null, contentValue);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    //delete in database
    public void delete(long _id) {
        database.delete(ControlDataBase.TABLE_NAME, ControlDataBase._ID + "=" + _id, null);
    }
}

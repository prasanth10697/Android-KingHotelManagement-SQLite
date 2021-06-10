package com.spider.bookmyroom.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spider.bookmyroom.Model.RoomBookModel;

import java.util.ArrayList;

public class RoomDataBase extends SQLiteOpenHelper {

    static final String TABLE_NAME = "ROOM_DETAILS";
    // Table columns
    static final String _ID = "_id";
    static final String ROOM_NUMBER = "room_number";
    static final String ACTIVE_STATUS = "active_status";
    // Database Information
    private static final String DB_NAME = "ROOM.DB";
    // database version
    private static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ROOM_NUMBER + " TEXT NOT NULL , " + ACTIVE_STATUS + " TEXT NOT NULL );";

    public RoomDataBase(Context context) {
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

    public ArrayList<RoomBookModel> readCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<RoomBookModel> courseModalArrayList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(new RoomBookModel(cursorCourses.getString(0), cursorCourses.getString(1), cursorCourses.getString(2)));
            } while (cursorCourses.moveToNext());
        }

        cursorCourses.close();
        return courseModalArrayList;
    }
}

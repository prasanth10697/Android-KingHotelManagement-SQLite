package com.spider.bookmyroom.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.spider.bookmyroom.DataBase.RoomDataBase;
import com.spider.bookmyroom.Utility.UserSessionManager;
import com.spider.bookmyroom.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding loginBinding;
    private final Context context = LoginActivity.this;
    UserSessionManager session;
    ArrayList<String> statusList =new ArrayList<>();
    ArrayList<String> roomLIst =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(loginBinding.getRoot());

        session = new UserSessionManager(getApplicationContext());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loginBinding.btnLogin.setOnClickListener(view -> {
            if (Objects.requireNonNull(loginBinding.email.getText()).toString().equals("admin@admin.com") || Objects.requireNonNull(loginBinding.pass.getText()).toString().equals("12345")) {
                session.createUserLoginSession("loginListModels.get(0).getToken()");
                Intent i = new Intent(context, MainActivity.class);
                startActivity(i);
                roomData();
                finish();
            } else {
                Toast.makeText(context, "please valid login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void roomData(){
        roomLIst.add("Room No:1"); statusList.add("Book Now");
        roomLIst.add("Room No:2"); statusList.add("Book Now");
        roomLIst.add("Room No:3"); statusList.add("Book Now");
        roomLIst.add("Room No:4"); statusList.add("Book Now");
        roomLIst.add("Room No:5"); statusList.add("Book Now");
        roomLIst.add("Room No:6"); statusList.add("Book Now");
        roomLIst.add("Room No:7"); statusList.add("Book Now");
        roomLIst.add("Room No:8"); statusList.add("Book Now");
        roomLIst.add("Room No:9"); statusList.add("Book Now");
        roomLIst.add("Room No:10"); statusList.add("Book Now");
        addSelected(statusList,roomLIst);
    }

    public void addSelected(ArrayList<String> activeStatus, ArrayList<String> roomNumber) {

        int size = roomNumber.size();
        RoomDataBase roomDataBase = new RoomDataBase(context);
        SQLiteDatabase db = roomDataBase.getWritableDatabase();
        try {
            for (int i = 0; i < size; i++) {
                ContentValues cv = new ContentValues();
                cv.put("room_number", roomNumber.get(i));
                cv.put("active_status", activeStatus.get(i));
                Log.d("Added ", "ROOM_DETAILS" + cv);
                db.insertOrThrow("ROOM_DETAILS", null, cv);
            }
            db.close();
        } catch (Exception e) {
            Log.e("Problem", e + " ");
        }
    }
}
package com.spider.bookmyroom.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.spider.bookmyroom.DataBase.ControlDBManager;
import com.spider.bookmyroom.DataBase.ControlDataBase;
import com.spider.bookmyroom.DataBase.RoomDBManager;
import com.spider.bookmyroom.Utility.SharedHelper;
import com.spider.bookmyroom.databinding.ActivityRoomdetailsBinding;
import com.spider.bookmyroom.databinding.UserDetailsBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Roomdetails extends AppCompatActivity {

    ActivityRoomdetailsBinding roomdetailsBinding;
    UserDetailsBinding userDetailsBinding;
    ControlDBManager controlManager;
    ControlDataBase controlDataBase;
    RoomDBManager dbManager;
    private final Context context = Roomdetails.this;
    private String room_ID, room_No, date="", user_name = "", user_add = "", db_id = "", dayDifference = "";
    Dialog bookingDialog,checkoutDialog;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomdetailsBinding = ActivityRoomdetailsBinding.inflate(getLayoutInflater());
        setContentView(roomdetailsBinding.getRoot());
        controlManager = new ControlDBManager(context);
        controlDataBase = new ControlDataBase(context);
        controlManager.open();
        dbManager = new RoomDBManager(context);
        dbManager.open();
        bookingDialog = new Dialog(context);
        checkoutDialog = new Dialog(context);
        Bundle bundle = getIntent().getExtras();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        if (bundle != null) {
            room_ID = getIntent().getStringExtra("ROOM_ID");
            room_No = getIntent().getStringExtra("ROOM_NO");
        }
        if (SharedHelper.getKey(context, "ROOM_BOOKING_ID").equals(room_ID)) {
            roomdetailsBinding.checkOut.setVisibility(View.VISIBLE);
            roomdetailsBinding.rvCardview.setVisibility(View.VISIBLE);
            roomdetailsBinding.booking.setVisibility(View.GONE);
        } else if (SharedHelper.getKey(context, "ROOM_BOOKING_ID").equals("0")) {
            roomdetailsBinding.checkOut.setVisibility(View.GONE);
            roomdetailsBinding.rvCardview.setVisibility(View.GONE);
            roomdetailsBinding.booking.setVisibility(View.VISIBLE);
        }
        roomdetailsBinding.booking.setOnClickListener(view -> {
            userDetailsBinding = UserDetailsBinding.inflate(getLayoutInflater());
            bookingDialog.setContentView(userDetailsBinding.getRoot());

            userDetailsBinding.btnSumbit.setOnClickListener(view1 -> {
                if (Objects.requireNonNull(userDetailsBinding.userName.getText()).toString().isEmpty() && Objects.requireNonNull(userDetailsBinding.userAdd.getText()).toString().isEmpty()) {
                    Log.d("hit", "onCreate: is empty ");
                } else {
                    controlManager.insert(userDetailsBinding.userName.getText().toString(), Objects.requireNonNull(userDetailsBinding.userAdd.getText()).toString(), room_ID, date);
                    SharedHelper.putKey(context, "ROOM_BOOKING_ID", room_ID);
                    dbManager.update(Long.parseLong(room_ID), "Check-Out");
                    onBackPressed();
                }
            });
            userDetailsBinding.closePopup.setOnClickListener(v1 -> bookingDialog.dismiss());
            Objects.requireNonNull(bookingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            bookingDialog.show();
        });

        roomdetailsBinding.checkOut.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Hi..."+user_name+"your stay room count was "+ dayDifference +"days and confirm your room vacate click the ok button")
                    .setTitle("Confirm ...!")
                    .setCancelable(false)
                    .setPositiveButton("ok", (dialog, id) -> {
                        SharedHelper.putKey(context, "ROOM_BOOKING_ID", "0");
                        dbManager.update(Long.parseLong(room_ID), room_No);
                        controlManager.delete(Long.parseLong(db_id));
                        onBackPressed();
                        dialog.dismiss();
                    });
            AlertDialog alert = builder.create();
            alert.show();
        });

        showDetails();
    }

    private void showDetails() {
        Cursor cursor = controlDataBase.readAllData(room_ID);
        if (cursor.getCount() == 0) {
            Log.d("hit", "showDetails: is a empty");
        } else {
            while (cursor.moveToNext()) {
                db_id = cursor.getString(0);
                user_name = cursor.getString(1);
                user_add = cursor.getString(2);
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                    Date date1 = dates.parse(date);
                    Date date2 = dates.parse(cursor.getString(4));
                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    long differenceDates = difference / (24 * 60 * 60 * 1000);
                    dayDifference = Long.toString(differenceDates);
                    Log.d("TAG", "onCreate: " + dayDifference);
                } catch (Exception exception) {
                    Log.d("TAG", "showDetails: error");
                }
                roomdetailsBinding.userName.setText(user_name);
                roomdetailsBinding.userAdres.setText(user_add);
                roomdetailsBinding.userCount.setText(dayDifference);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(context, MainActivity.class);
        startActivity(i);
        finish();
    }
}
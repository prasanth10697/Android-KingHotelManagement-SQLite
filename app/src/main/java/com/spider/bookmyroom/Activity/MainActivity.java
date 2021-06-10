package com.spider.bookmyroom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.spider.bookmyroom.DataBase.RoomDataBase;
import com.spider.bookmyroom.Model.RoomBookModel;
import com.spider.bookmyroom.databinding.ActivityMainBinding;
import com.spider.bookmyroom.databinding.RoomBookingBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    private final Context context = MainActivity.this;
    ArrayList<RoomBookModel> bookingItemList = new ArrayList<>();
    BookRoomAdapter bookRoomAdapter;
    RoomDataBase roomDataBase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        roomDataBase = new RoomDataBase(context);
        bookingItemList = roomDataBase.readCourses();

        GridLayoutManager layoutManager = new GridLayoutManager(getApplication(), 2);
        mainBinding.rvRecyle.setLayoutManager(layoutManager);
        bookRoomAdapter = new BookRoomAdapter(bookingItemList,context);
        mainBinding.rvRecyle.setAdapter(bookRoomAdapter);
        mainBinding.rvRecyle.setNestedScrollingEnabled(false);
    }

    public class BookRoomAdapter extends RecyclerView.Adapter<BookRoomAdapter.BookRoomView>{

        RoomBookingBinding bookingBinding;
        ArrayList<RoomBookModel> roomBookModelsList;
        private final Context context;

        public BookRoomAdapter(ArrayList<RoomBookModel> bookingItemList, Context context) {
            this.roomBookModelsList = bookingItemList;
            this.context = context;
        }

        @NonNull
        @Override
        public BookRoomAdapter.BookRoomView onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
            bookingBinding = RoomBookingBinding.inflate(getLayoutInflater(),parent,false);
            return new BookRoomView(bookingBinding.getRoot());
        }

        @Override
        public void onBindViewHolder(@NonNull BookRoomView holder, int position) {
            bookingBinding.roomNumber.setText(roomBookModelsList.get(position).getRoomNumber());

            if(roomBookModelsList.get(position).getRoomStatus().equals("Check-Out")){
                bookingBinding.checkout.setVisibility(View.VISIBLE);
                bookingBinding.imageCheckout.setVisibility(View.VISIBLE);
                bookingBinding.booking.setVisibility(View.GONE);
                bookingBinding.imageFurniture.setVisibility(View.GONE);
            }else {
                bookingBinding.checkout.setVisibility(View.GONE);
                bookingBinding.imageCheckout.setVisibility(View.GONE);
                bookingBinding.booking.setVisibility(View.VISIBLE);
                bookingBinding.booking.setVisibility(View.VISIBLE);
            }
            bookingBinding.rvCardview.setOnClickListener(view -> {
                Intent i = new Intent(context, Roomdetails.class);
                i.putExtra("ROOM_ID",roomBookModelsList.get(position).getRoomID());
                i.putExtra("ROOM_NO",roomBookModelsList.get(position).getRoomNumber());
                startActivity(i);
                finish();
            });
        }

        @Override
        public int getItemCount() {
            return roomBookModelsList.size();
        }

        public class BookRoomView extends RecyclerView.ViewHolder {
            public BookRoomView(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
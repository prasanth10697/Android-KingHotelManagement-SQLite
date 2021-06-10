package com.spider.bookmyroom.Model;

public class RoomBookModel {
    String roomNumber, roomStatus,roomID;

    public RoomBookModel(String room_id, String room_numner, String room_key) {
        this.roomID = room_id;
        this.roomNumber = room_numner;
        this.roomStatus = room_key;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
}

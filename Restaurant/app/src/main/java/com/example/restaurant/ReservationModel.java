package com.example.restaurant;

public class ReservationModel {
    public int id;
    public String date;
    public String time;
    public int people;
    public String status;

    public ReservationModel(int id, String date, String time, int people, String status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.people = people;
        this.status = status;
    }
}

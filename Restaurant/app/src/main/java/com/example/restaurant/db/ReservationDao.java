package com.example.restaurant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.ReservationModel;

import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    private final AppDbHelper helper;

    public ReservationDao(Context context) {
        helper = new AppDbHelper(context);
    }

    // CREATE
    public long insertReservation(String date, String time, int people, String status) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("date", date);
        cv.put("time", time);
        cv.put("people", people);
        cv.put("status", status);

        return db.insert("reservation", null, cv);
    }

    // READ - All reservations (staff)
    public List<ReservationModel> getAllReservations() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ReservationModel> list = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT id, date, time, people, status FROM reservation ORDER BY id DESC",
                null
        );

        while (c.moveToNext()) {
            list.add(new ReservationModel(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4)
            ));
        }

        c.close();
        return list;
    }

    // UPDATE status
    public int updateStatus(int id, String newStatus) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("status", newStatus);

        return db.update("reservation", cv, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<ReservationModel> getReservationsPendingFirst() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<ReservationModel> list = new ArrayList<>();

        Cursor c = db.rawQuery(
                "SELECT id, date, time, people, status FROM reservation " +
                        "ORDER BY CASE status " +
                        "WHEN 'Pending' THEN 0 " +
                        "WHEN 'Confirmed' THEN 1 " +
                        "WHEN 'Cancelled' THEN 2 " +
                        "ELSE 3 END, id DESC",
                null
        );

        while (c.moveToNext()) {
            list.add(new ReservationModel(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getString(4)
            ));
        }
        c.close();
        return list;
    }

    // DELETE (guest cancel can also be done as status = Cancelled; we’ll use updateStatus)
}

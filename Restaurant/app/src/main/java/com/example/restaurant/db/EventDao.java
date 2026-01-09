package com.example.restaurant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EventDao {

    private final AppDbHelper helper;

    public static class Event {
        public int id;
        public String targetRole;
        public String type;
        public String title;
        public String body;
    }

    public EventDao(Context ctx) {
        helper = new AppDbHelper(ctx);
    }

    // Insert a new event (unseen by default)
    public void insert(String targetRole, String type, String title, String body) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AppDbHelper.COL_EV_TARGET, targetRole);
        cv.put(AppDbHelper.COL_EV_TYPE, type);
        cv.put(AppDbHelper.COL_EV_TITLE, title);
        cv.put(AppDbHelper.COL_EV_BODY, body);
        cv.put(AppDbHelper.COL_EV_SEEN, 0);
        cv.put(AppDbHelper.COL_EV_TS, System.currentTimeMillis());
        db.insert(AppDbHelper.TABLE_EVENTS, null, cv);
    }

    // Get unseen events for a role (staff/guest)
    public List<Event> getUnseenForRole(String role) {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<Event> out = new ArrayList<>();

        Cursor c = db.query(
                AppDbHelper.TABLE_EVENTS,
                null,
                AppDbHelper.COL_EV_TARGET + "=? AND " + AppDbHelper.COL_EV_SEEN + "=0",
                new String[]{role},
                null, null,
                AppDbHelper.COL_EV_TS + " ASC"
        );

        while (c.moveToNext()) {
            Event e = new Event();
            e.id = c.getInt(c.getColumnIndexOrThrow(AppDbHelper.COL_EV_ID));
            e.targetRole = c.getString(c.getColumnIndexOrThrow(AppDbHelper.COL_EV_TARGET));
            e.type = c.getString(c.getColumnIndexOrThrow(AppDbHelper.COL_EV_TYPE));
            e.title = c.getString(c.getColumnIndexOrThrow(AppDbHelper.COL_EV_TITLE));
            e.body = c.getString(c.getColumnIndexOrThrow(AppDbHelper.COL_EV_BODY));
            out.add(e);
        }
        c.close();
        return out;
    }

    // Mark event as seen
    public void markSeen(int eventId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(AppDbHelper.COL_EV_SEEN, 1);
        db.update(AppDbHelper.TABLE_EVENTS, cv,
                AppDbHelper.COL_EV_ID + "=?",
                new String[]{String.valueOf(eventId)});
    }
}

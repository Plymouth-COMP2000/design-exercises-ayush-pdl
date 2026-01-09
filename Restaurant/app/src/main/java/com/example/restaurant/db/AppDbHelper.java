package com.example.restaurant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite database helper for the Restaurant app.
 * Handles creation and upgrade of local database.
 */
public class AppDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "restaurant.db";
    private static final int DB_VERSION = 2; // IMPORTANT: version bumped

    public AppDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // MENU TABLE
        db.execSQL(
                "CREATE TABLE menu (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "price REAL NOT NULL, " +
                        "imageUri TEXT" +
                        ")"
        );

        // RESERVATION TABLE (for later exercises)
        db.execSQL(
                "CREATE TABLE reservation (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "date TEXT, " +
                        "time TEXT, " +
                        "people INTEGER, " +
                        "status TEXT" +
                        ")"
        );
        db.execSQL(CREATE_EVENTS_TABLE);

    }



    // ===== Events table (for in-app notifications) =====
    public static final String TABLE_EVENTS = "events";
    public static final String COL_EV_ID = "id";
    public static final String COL_EV_TARGET = "targetRole"; // "staff" or "guest"
    public static final String COL_EV_TYPE = "type";         // e.g., NEW_BOOKING, MENU_CHANGED
    public static final String COL_EV_TITLE = "title";
    public static final String COL_EV_BODY = "body";
    public static final String COL_EV_SEEN = "seen";         // 0 = unseen, 1 = seen
    public static final String COL_EV_TS = "createdAt";      // timestamp (ms)


    private static final String CREATE_EVENTS_TABLE =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    COL_EV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_EV_TARGET + " TEXT NOT NULL, " +
                    COL_EV_TYPE + " TEXT NOT NULL, " +
                    COL_EV_TITLE + " TEXT NOT NULL, " +
                    COL_EV_BODY + " TEXT NOT NULL, " +
                    COL_EV_SEEN + " INTEGER NOT NULL DEFAULT 0, " +
                    COL_EV_TS + " INTEGER NOT NULL" +
                    ");";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables and recreate
        db.execSQL("DROP TABLE IF EXISTS menu");
        db.execSQL("DROP TABLE IF EXISTS reservation");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);

    }
}

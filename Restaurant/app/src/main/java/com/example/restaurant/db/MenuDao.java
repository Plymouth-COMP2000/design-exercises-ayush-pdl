package com.example.restaurant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.restaurant.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Menu table.
 * Handles all CRUD operations for menu items.
 */
public class MenuDao {

    private final AppDbHelper helper;

    public MenuDao(Context context) {
        helper = new AppDbHelper(context);
    }

    /**
     * INSERT a new menu item
     */
    public long insertMenuItem(String name, double price, String imageUri) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);

        // imageUri is optional
        if (imageUri != null) values.put("imageUri", imageUri);

        return db.insert("menu", null, values);
    }

    /**
     * READ all menu items (SAFE even if imageUri column is missing in older DB)
     */
    public List<MenuItemModel> getAllMenuModels() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<MenuItemModel> list = new ArrayList<>();

        Cursor cursor = null;
        try {
            // Try to read imageUri (new schema)
            cursor = db.rawQuery(
                    "SELECT id, name, price, imageUri FROM menu ORDER BY id DESC",
                    null
            );

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    double price = cursor.getDouble(2);
                    String imageUri = cursor.isNull(3) ? null : cursor.getString(3);

                    list.add(new MenuItemModel(id, name, price, imageUri));
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            // Fallback for older DB where imageUri doesn't exist
            if (cursor != null) cursor.close();

            cursor = db.rawQuery(
                    "SELECT id, name, price FROM menu ORDER BY id DESC",
                    null
            );

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    double price = cursor.getDouble(2);

                    list.add(new MenuItemModel(id, name, price, null));
                } while (cursor.moveToNext());
            }

        } finally {
            if (cursor != null) cursor.close();
        }

        return list;
    }

    /**
     * DELETE a menu item by ID
     */
    public int deleteMenuItem(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.delete("menu", "id = ?", new String[]{String.valueOf(id)});
    }

    /**
     * UPDATE a menu item
     */
    public int updateMenuItem(int id, String name, double price, String imageUri) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);

        // optional
        values.put("imageUri", imageUri);

        return db.update("menu", values, "id = ?", new String[]{String.valueOf(id)});
    }
}

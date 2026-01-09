package com.example.restaurant;

/**
 * Model class representing a menu item in the restaurant.
 * Stored locally in SQLite and displayed in RecyclerView.
 */
public class MenuItemModel {

    public int id;
    public String name;
    public double price;
    public String imageUri;   // optional image (gallery URI)

    public MenuItemModel(int id, String name, double price, String imageUri) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUri = imageUri;
    }

    public MenuItemModel(int id, String name, double price) {
    }
}

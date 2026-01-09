package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.db.MenuDao;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MenuManagerActivity extends AppCompatActivity {

    private MenuDao dao;
    private MenuAdapter adapter;
    private RecyclerView rvMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_manager);

        rvMenu = findViewById(R.id.rvMenu);
        MaterialButton btnAdd = findViewById(R.id.btnAddMenuItem);

        if (rvMenu == null || btnAdd == null) {
            Toast.makeText(this, "MenuManager UI IDs missing (check activity_menu_manager.xml)", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        rvMenu.setLayoutManager(new LinearLayoutManager(this));

        // ✅ SAFELY init DAO + list
        List<MenuItemModel> items = new ArrayList<>();
        try {
            dao = new MenuDao(this);
            items = dao.getAllMenuModels();
        } catch (Exception e) {
            Toast.makeText(this,
                    "Menu DB error: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            // Keep app alive with empty list
        }

        // ✅ SAFELY init adapter
        try {
            adapter = new MenuAdapter(items, new MenuAdapter.OnMenuActionListener() {

                @Override
                public void onDelete(MenuItemModel item) {
                    try {
                        dao.deleteMenuItem(item.id);
                        refresh();
                        Toast.makeText(MenuManagerActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MenuManagerActivity.this,
                                "Delete failed: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onEdit(MenuItemModel item) {
                    Intent i = new Intent(MenuManagerActivity.this, AddMenuItemActivity.class);
                    i.putExtra("MODE", "EDIT");
                    i.putExtra("ID", item.id);
                    i.putExtra("NAME", item.name);
                    i.putExtra("PRICE", item.price);
                    i.putExtra("IMAGE_URI", item.imageUri);
                    startActivity(i);
                }
            });

            rvMenu.setAdapter(adapter);

        } catch (Exception e) {
            Toast.makeText(this,
                    "Menu UI error (adapter/layout): " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        // Open Add Menu Item screen
        btnAdd.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, AddMenuItemActivity.class));
            } catch (Exception e) {
                Toast.makeText(this,
                        "Cannot open AddMenuItemActivity: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void refresh() {
        if (dao == null || adapter == null) return;

        try {
            List<MenuItemModel> updated = dao.getAllMenuModels();
            adapter.setData(updated);
        } catch (Exception e) {
            Toast.makeText(this,
                    "Refresh failed: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}

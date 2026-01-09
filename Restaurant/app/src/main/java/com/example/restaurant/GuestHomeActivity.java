package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.db.MenuDao;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class GuestHomeActivity extends AppCompatActivity {

    private MenuDao menuDao;
    private MenuGuestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        // Buttons
        MaterialButton btnMakeReservation = findViewById(R.id.btnMakeReservation);
        MaterialButton btnMyReservations = findViewById(R.id.btnMyReservations);
        MaterialButton btnNotificationSettings = findViewById(R.id.btnNotificationSettings);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        if (btnMakeReservation == null || btnMyReservations == null || btnNotificationSettings == null) {
            Toast.makeText(this, "GuestHome: ID mismatch in XML", Toast.LENGTH_LONG).show();
            return;
        }

        btnMakeReservation.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationFormActivity.class)));

        btnMyReservations.setOnClickListener(v ->
                startActivity(new Intent(this, MyReservationsActivity.class)));

        btnNotificationSettings.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationSettingsActivity.class)));

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Intent i = new Intent(this, RoleSelectActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });
        }

        // Menu list
        RecyclerView rv = findViewById(R.id.rvGuestMenu);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuGuestAdapter();
        rv.setAdapter(adapter);

        menuDao = new MenuDao(this);
        loadMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // keep your menu refresh if you already have it
        // loadMenu();

        try {
            com.example.restaurant.db.EventDao dao = new com.example.restaurant.db.EventDao(this);
            java.util.List<com.example.restaurant.db.EventDao.Event> events = dao.getUnseenForRole("guest");

            for (com.example.restaurant.db.EventDao.Event e : events) {
                android.widget.Toast.makeText(this, e.title + ": " + e.body, android.widget.Toast.LENGTH_LONG).show();
                dao.markSeen(e.id);
            }
        } catch (Exception ex) {
            android.util.Log.e("EVENTS", "Failed to load events", ex);
        }
    }


    private void loadMenu() {
        List<MenuItemModel> menu = menuDao.getAllMenuModels(); // use your actual method name
        adapter.setItems(menu);
    }


}

package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.example.restaurant.db.EventDao;
import com.example.restaurant.notifications.NotifyHelper;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        MaterialButton btnManageMenu = findViewById(R.id.btnManageMenu);
        MaterialButton btnReservations = findViewById(R.id.btnReservations);
        MaterialButton btnNotificationSettings = findViewById(R.id.btnNotificationSettings);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        // If any of these are null, IDs do not match XML
        if (btnManageMenu == null || btnReservations == null || btnNotificationSettings == null) {
            Toast.makeText(this, "ID mismatch in activity_staff_dashboard.xml", Toast.LENGTH_LONG).show();
            return;
        }

        btnManageMenu.setOnClickListener(v -> {
            Toast.makeText(this, "Manage Menu clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MenuManagerActivity.class));
        });

        btnReservations.setOnClickListener(v -> {
            Toast.makeText(this, "Reservations clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, ReservationListActivity.class));
        });

        btnNotificationSettings.setOnClickListener(v -> {
            Toast.makeText(this, "Notification Settings clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NotificationSettingsActivity.class));
        });

        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, RoleSelectActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });
        } else {
            Toast.makeText(this, "btnLogout not found in XML (add it)", Toast.LENGTH_LONG).show();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        showPendingStaffNotifications();

        try {
            com.example.restaurant.db.EventDao dao = new com.example.restaurant.db.EventDao(this);
            java.util.List<com.example.restaurant.db.EventDao.Event> events = dao.getUnseenForRole("staff");

            for (com.example.restaurant.db.EventDao.Event e : events) {
                android.widget.Toast.makeText(this, e.title + ": " + e.body, android.widget.Toast.LENGTH_LONG).show();
                dao.markSeen(e.id);
            }
        } catch (Exception ex) {
            android.util.Log.e("EVENTS", "Failed to load events", ex);
        }
    }

    private void showPendingStaffNotifications() {
        try {
            // Ensure notification channel exists
            com.example.restaurant.notifications.NotifyHelper.ensureChannel(this);

            EventDao eventDao = new EventDao(this);

            // NOTE: Your Event class is EventDao.Event
            java.util.List<EventDao.Event> events = eventDao.getUnseenForRole("staff");

            for (EventDao.Event e : events) {
                com.example.restaurant.notifications.NotifyHelper.show(
                        this,
                        e.id,
                        e.title,
                        e.body
                );
                eventDao.markSeen(e.id);
            }
        } catch (Exception ignored) {
            // ignore to prevent crashing
        }
    }

}



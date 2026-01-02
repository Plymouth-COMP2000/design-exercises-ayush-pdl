package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        Button btnMenu = findViewById(R.id.btnManageMenu);
        Button btnRes = findViewById(R.id.btnReservations);
        Button btnNotif = findViewById(R.id.btnNotificationSettings);

        btnMenu.setOnClickListener(v ->
                startActivity(new Intent(this, MenuManagerActivity.class)));

        btnRes.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationListActivity.class)));

        btnNotif.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationSettingsActivity.class)));
    }
}

package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager session = new SessionManager(this);

        // ✅ Auto-route if already logged in
        if (session.isLoggedIn()) {
            String role = session.getUsertype();

            if (role.equalsIgnoreCase("staff") || role.equalsIgnoreCase("admin")) {
                startActivity(new Intent(this, StaffDashboardActivity.class));
            } else {
                startActivity(new Intent(this, GuestHomeActivity.class));
            }
            finish();
            return;
        }

        // If NOT logged in -> show normal home screen
        setContentView(R.layout.activity_main);
    }
}

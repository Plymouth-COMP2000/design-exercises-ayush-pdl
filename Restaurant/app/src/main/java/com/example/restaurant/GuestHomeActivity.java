package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_home);

        Button btnMake = findViewById(R.id.btnMakeReservation);
        Button btnMy = findViewById(R.id.btnMyReservations);
        Button btnNotif = findViewById(R.id.btnGuestNotifSettings);

        btnMake.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationFormActivity.class)));

        btnMy.setOnClickListener(v ->
                startActivity(new Intent(this, MyReservationsActivity.class)));

        btnNotif.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationSettingsActivity.class)));
    }
}

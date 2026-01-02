package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RoleSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        Button btnStaff = findViewById(R.id.btnStaffLogin);
        Button btnGuest = findViewById(R.id.btnGuestLogin);

        btnStaff.setOnClickListener(v ->
                startActivity(new Intent(this, StaffLoginActivity.class)));

        btnGuest.setOnClickListener(v ->
                startActivity(new Intent(this, GuestLoginActivity.class)));
    }
}

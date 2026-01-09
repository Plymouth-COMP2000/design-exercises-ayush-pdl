package com.example.restaurant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.restaurant.notifications.NotifyHelper;   // ✅ import the right helper
import com.google.android.material.button.MaterialButton;

public class RoleSelectActivity extends AppCompatActivity {

    private static final int REQ_NOTIF = 501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        // Create channel once (safe)
        NotifyHelper.ensureChannel(this);                  // ✅ correct class name

        // Android 13+ permission
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQ_NOTIF
                );
            }
        }

        MaterialButton btnStaffLogin = findViewById(R.id.btnStaffLogin);
        MaterialButton btnGuestLogin = findViewById(R.id.btnGuestLogin);

        if (btnStaffLogin == null || btnGuestLogin == null) {
            Toast.makeText(this, "RoleSelect: button id mismatch in XML", Toast.LENGTH_LONG).show();
            return;
        }

        btnStaffLogin.setOnClickListener(v ->
                startActivity(new Intent(this, StaffLoginActivity.class)));

        btnGuestLogin.setOnClickListener(v ->
                startActivity(new Intent(this, GuestLoginActivity.class)));
    }
}

package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GuestLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        EditText etUser = findViewById(R.id.etGuestUsername);
        EditText etPass = findViewById(R.id.etGuestPassword);
        Button btnLogin = findViewById(R.id.btnGuestLogin);
        Button btnRegister = findViewById(R.id.btnGoRegister);

        // If any view is null, show which one is missing and stop
        if (etUser == null) { Toast.makeText(this, "Missing ID: etGuestUsername", Toast.LENGTH_LONG).show(); return; }
        if (etPass == null) { Toast.makeText(this, "Missing ID: etGuestPassword", Toast.LENGTH_LONG).show(); return; }
        if (btnLogin == null) { Toast.makeText(this, "Missing ID: btnGuestLogin", Toast.LENGTH_LONG).show(); return; }
        if (btnRegister == null) { Toast.makeText(this, "Missing ID: btnGoRegister", Toast.LENGTH_LONG).show(); return; }

        btnLogin.setOnClickListener(v -> {
            String u = etUser.getText().toString().trim();
            String p = etPass.getText().toString().trim();

            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(this, GuestHomeActivity.class));
        });

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }
}

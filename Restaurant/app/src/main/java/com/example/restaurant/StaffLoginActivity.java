package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.api.ApiCallback;
import com.example.restaurant.api.CourseworkApi;
import com.example.restaurant.api.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class StaffLoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        if (etUsername == null || etPassword == null || btnLogin == null) {
            Toast.makeText(this, "ID mismatch in activity_staff_login.xml", Toast.LENGTH_LONG).show();
            return;
        }

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = etUsername.getText() == null ? "" : etUsername.getText().toString().trim();
        String password = etPassword.getText() == null ? "" : etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);

        CourseworkApi.getUser(this, username, new ApiCallback<UserModel>() {
            @Override
            public void onSuccess(UserModel user) {
                btnLogin.setEnabled(true);

                // Password check
                if (!password.equals(user.password)) {
                    Toast.makeText(StaffLoginActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                    return;
                }

                // Role check (staff/admin/manager allowed)
                String role = user.usertype == null ? "" : user.usertype.trim().toLowerCase();
                if (!(role.equals("staff") || role.equals("admin") || role.equals("manager"))) {
                    Toast.makeText(StaffLoginActivity.this,
                            "This account is not staff. Use Guest Login.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(StaffLoginActivity.this, "Staff login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StaffLoginActivity.this, StaffDashboardActivity.class));
                finish();
            }

            @Override
            public void onError(String message) {
                btnLogin.setEnabled(true);
                Toast.makeText(StaffLoginActivity.this, "Login failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

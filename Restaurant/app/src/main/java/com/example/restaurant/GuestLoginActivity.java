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

public class GuestLoginActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin, btnGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        if (etUsername == null || etPassword == null || btnLogin == null) {
            Toast.makeText(this, "ID mismatch in activity_guest_login.xml", Toast.LENGTH_LONG).show();
            return;
        }

        if (btnGoRegister != null) {
            btnGoRegister.setOnClickListener(v ->
                    startActivity(new Intent(GuestLoginActivity.this, RegisterActivity.class)));
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

                if (!password.equals(user.password)) {
                    Toast.makeText(GuestLoginActivity.this, "Incorrect password", Toast.LENGTH_LONG).show();
                    return;
                }

                // Role check (guest/student allowed)
                String role = user.usertype == null ? "" : user.usertype.trim().toLowerCase();
                if (!(role.equals("guest") || role.equals("student"))) {
                    Toast.makeText(GuestLoginActivity.this,
                            "This account is staff. Use Staff Login.",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(GuestLoginActivity.this, "Guest login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GuestLoginActivity.this, GuestHomeActivity.class));
                finish();
            }

            @Override
            public void onError(String message) {
                btnLogin.setEnabled(true);
                Toast.makeText(GuestLoginActivity.this, "Login failed: " + message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

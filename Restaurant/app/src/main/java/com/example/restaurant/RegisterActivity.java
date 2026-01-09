package com.example.restaurant;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.api.ApiCallback;
import com.example.restaurant.api.CourseworkApi;
import com.example.restaurant.api.UserModel;

public class RegisterActivity extends AppCompatActivity {

    private EditText etU, etP, etE;
    private Button btn;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etU = findViewById(R.id.etRegUsername);
        etP = findViewById(R.id.etRegPassword);
        etE = findViewById(R.id.etRegEmail);

        btn = findViewById(R.id.btnRegister);
        progress = findViewById(R.id.progressRegister);

        btn.setOnClickListener(v -> doRegister());
    }

    private void setLoading(boolean loading) {
        if (progress != null) {
            progress.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
        btn.setEnabled(!loading);
        etU.setEnabled(!loading);
        etP.setEnabled(!loading);
        etE.setEnabled(!loading);
    }

    private void doRegister() {

        String username = etU.getText().toString().trim();
        String password = etP.getText().toString().trim();
        String email = etE.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // (Optional) simple email check (not strict)
        if (!email.contains("@") || !email.contains(".")) {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Build user model for API
        UserModel user = new UserModel();
        user.username = username;
        user.password = password;
        user.email = email;

        // Missing fields in your UI -> safe prototype placeholders
        user.firstname = "Guest";
        user.lastname = "User";
        user.contact = "0000000000";
        user.usertype = "student";

        setLoading(true);

        // 1) Ensure student DB exists
        CourseworkApi.createStudentDb(this, new ApiCallback<String>() {
            @Override
            public void onSuccess(String msg) {
                // 2) Create user
                createUser(user);
            }

            @Override
            public void onError(String message) {
                // If DB already exists, some servers respond with an error.
                // Still try to create user anyway.
                createUser(user);
            }
        });
    }

    private void createUser(UserModel user) {
        CourseworkApi.createUser(this, user, new ApiCallback<String>() {
            @Override
            public void onSuccess(String message) {
                setLoading(false);
                Toast.makeText(RegisterActivity.this,
                        "Registered successfully. You can login now.",
                        Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(RegisterActivity.this,
                        "Register failed: " + message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

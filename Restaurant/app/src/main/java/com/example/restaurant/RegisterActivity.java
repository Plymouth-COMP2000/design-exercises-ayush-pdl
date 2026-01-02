package com.example.restaurant;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText etU = findViewById(R.id.etRegUsername);
        EditText etP = findViewById(R.id.etRegPassword);
        EditText etE = findViewById(R.id.etRegEmail);

        Button btn = findViewById(R.id.btnRegister);

        btn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etU.getText()) || TextUtils.isEmpty(etP.getText()) || TextUtils.isEmpty(etE.getText())) {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Registered (prototype)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

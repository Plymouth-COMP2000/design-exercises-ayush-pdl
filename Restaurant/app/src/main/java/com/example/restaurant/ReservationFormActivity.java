package com.example.restaurant;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReservationFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        EditText etDate = findViewById(R.id.etDate);
        EditText etTime = findViewById(R.id.etTime);
        EditText etPeople = findViewById(R.id.etPeople);

        Button btn = findViewById(R.id.btnSubmitReservation);

        btn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etDate.getText()) || TextUtils.isEmpty(etTime.getText()) || TextUtils.isEmpty(etPeople.getText())) {
                Toast.makeText(this, "Please fill all required fields (*)", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Reservation submitted (prototype)", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

package com.example.restaurant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.db.ReservationDao;
import com.example.restaurant.db.EventDao;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ReservationFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_form);

        TextInputEditText etDate = findViewById(R.id.etDate);
        TextInputEditText etTime = findViewById(R.id.etTime);
        TextInputEditText etPeople = findViewById(R.id.etPeople);
        MaterialButton btnSubmit = findViewById(R.id.btnSubmitReservation);

        ReservationDao dao = new ReservationDao(this);

        btnSubmit.setOnClickListener(v -> {
            String date = etDate.getText() == null ? "" : etDate.getText().toString().trim();
            String time = etTime.getText() == null ? "" : etTime.getText().toString().trim();
            String peopleText = etPeople.getText() == null ? "" : etPeople.getText().toString().trim();

            if (date.isEmpty() || time.isEmpty() || peopleText.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            int people;
            try {
                people = Integer.parseInt(peopleText);
            } catch (Exception ex) {
                Toast.makeText(this, "Enter a valid number of people", Toast.LENGTH_SHORT).show();
                return;
            }

            if (people <= 0) {
                Toast.makeText(this, "People must be at least 1", Toast.LENGTH_SHORT).show();
                return;
            }

            dao.insertReservation(date, time, people, "Pending");
            Toast.makeText(this, "Reservation created (Pending)", Toast.LENGTH_SHORT).show();

            // Create an event for staff (stored locally)
            new EventDao(this).insert(
                    "staff",
                    "NEW_BOOKING",
                    "New booking received",
                    "A guest created a reservation."
            );

            finish();
        });
    }
}

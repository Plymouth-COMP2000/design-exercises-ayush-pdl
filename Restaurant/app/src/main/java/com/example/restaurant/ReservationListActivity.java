package com.example.restaurant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.db.ReservationDao;

public class ReservationListActivity extends AppCompatActivity {

    private ReservationDao dao;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_list);

        dao = new ReservationDao(this);

        RecyclerView rv = findViewById(R.id.rvReservations);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReservationAdapter(dao.getReservationsPendingFirst(), true, new ReservationAdapter.OnReservationActionListener() {
            @Override
            public void onConfirm(ReservationModel r) {
                dao.updateStatus(r.id, "Confirmed");

                Toast.makeText(ReservationListActivity.this, "Confirmed", Toast.LENGTH_SHORT).show();

                new com.example.restaurant.db.EventDao(ReservationListActivity.this).insert(
                        "guest",
                        "CONFIRMED",
                        "Reservation confirmed",
                        "Your reservation has been confirmed by staff."
                );

                refresh();
            }

            @Override
            public void onCancel(ReservationModel r) {
                dao.updateStatus(r.id, "Cancelled");
                Toast.makeText(ReservationListActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

                new com.example.restaurant.db.EventDao(ReservationListActivity.this).insert(
                        "guest",
                        "CANCELLED",
                        "Reservation cancelled",
                        "Your reservation was cancelled by staff."
                );
                refresh();
            }
        });

        rv.setAdapter(adapter);
    }

    private void refresh() {
        adapter.setData(dao.getReservationsPendingFirst());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}

package com.example.restaurant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurant.db.ReservationDao;

public class MyReservationsActivity extends AppCompatActivity {

    private ReservationDao dao;
    private ReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        dao = new ReservationDao(this);

        RecyclerView rv = findViewById(R.id.rvMyReservations);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReservationAdapter(dao.getAllReservations(), false, new ReservationAdapter.OnReservationActionListener() {
            @Override
            public void onConfirm(ReservationModel r) {
                // guest does not confirm
            }

            @Override
            public void onCancel(ReservationModel r) {
                dao.updateStatus(r.id, "Cancelled");
                refresh();
            }
        });

        rv.setAdapter(adapter);
    }

    private void refresh() {
        adapter.setData(dao.getAllReservations());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}

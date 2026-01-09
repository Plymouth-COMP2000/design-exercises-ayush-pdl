package com.example.restaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.VH> {

    public interface OnReservationActionListener {
        void onConfirm(ReservationModel r);
        void onCancel(ReservationModel r);
    }

    private List<ReservationModel> data;
    private final OnReservationActionListener listener;
    private final boolean staffMode;

    public ReservationAdapter(List<ReservationModel> data, boolean staffMode, OnReservationActionListener listener) {
        this.data = data;
        this.listener = listener;
        this.staffMode = staffMode;
    }

    public void setData(List<ReservationModel> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvMain, tvStatus;
        MaterialButton btnConfirm, btnCancel;

        VH(@NonNull View itemView) {
            super(itemView);
            tvMain = itemView.findViewById(R.id.tvResMain);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ReservationModel r = data.get(position);

        holder.tvMain.setText(r.date + " • " + r.time + " • " + r.people + " people");
        holder.tvStatus.setText("Status: " + r.status);


        // guest mode: hide confirm button
        holder.btnConfirm.setVisibility(staffMode ? View.VISIBLE : View.GONE);

        holder.btnConfirm.setOnClickListener(v -> {
            if (listener != null) listener.onConfirm(r);
        });

        holder.btnCancel.setOnClickListener(v -> {
            if (listener != null) listener.onCancel(r);
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

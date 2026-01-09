package com.example.restaurant;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.VH> {

    public interface OnMenuActionListener {
        void onDelete(MenuItemModel item);
        void onEdit(MenuItemModel item);
    }

    private List<MenuItemModel> data;
    private final OnMenuActionListener listener;

    public MenuAdapter(List<MenuItemModel> data, OnMenuActionListener listener) {
        this.data = data;
        this.listener = listener;
    }

    public void setData(List<MenuItemModel> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        MaterialButton btnEdit, btnDelete;
        ImageView imgDish;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvDishPrice);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            imgDish = itemView.findViewById(R.id.imgDish); // optional but should exist
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        MenuItemModel item = data.get(position);

        // ✅ Null-safe text binding
        if (holder.tvName != null) holder.tvName.setText(item.name == null ? "" : item.name);
        if (holder.tvPrice != null) {
            holder.tvPrice.setText(String.format(Locale.UK, "£%.2f", item.price));
        }

        // ✅ SUPER SAFE image binding (prevents RecyclerView crash)
        if (holder.imgDish != null) {
            try {
                if (item.imageUri != null && !item.imageUri.trim().isEmpty()) {
                    holder.imgDish.setImageURI(Uri.parse(item.imageUri));
                    // If URI fails to load, setImageURI may leave it empty -> fallback:
                    if (holder.imgDish.getDrawable() == null) {
                        holder.imgDish.setImageResource(android.R.drawable.ic_menu_gallery);
                    }
                } else {
                    holder.imgDish.setImageResource(android.R.drawable.ic_menu_gallery);
                }
            } catch (Exception ex) {
                holder.imgDish.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        // ✅ Click listeners safe
        if (holder.btnDelete != null) {
            holder.btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onDelete(item);
            });
        }

        if (holder.btnEdit != null) {
            holder.btnEdit.setOnClickListener(v -> {
                if (listener != null) listener.onEdit(item);
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}

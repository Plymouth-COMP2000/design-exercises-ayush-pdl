package com.example.restaurant;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.db.MenuDao;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddMenuItemActivity extends AppCompatActivity {

    private Uri selectedImageUri = null;

    private boolean isEditMode = false;
    private int editId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);

        ImageView imgPreview = findViewById(R.id.imgPreview);
        MaterialButton btnPick = findViewById(R.id.btnPickImage);
        MaterialButton btnSave = findViewById(R.id.btnSaveMenuItem);

        TextInputEditText etName = findViewById(R.id.etDishName);
        TextInputEditText etPrice = findViewById(R.id.etDishPrice);

        MenuDao dao = new MenuDao(this);

        // ===== Check if EDIT mode =====
        String mode = getIntent().getStringExtra("MODE");
        if (mode != null && mode.equals("EDIT")) {
            isEditMode = true;
            editId = getIntent().getIntExtra("ID", -1);

            String name = getIntent().getStringExtra("NAME");
            double price = getIntent().getDoubleExtra("PRICE", 0.0);
            String imageUriStr = getIntent().getStringExtra("IMAGE_URI");

            if (name != null) etName.setText(name);
            etPrice.setText(String.valueOf(price));

            if (imageUriStr != null && !imageUriStr.trim().isEmpty()) {
                selectedImageUri = Uri.parse(imageUriStr);
                imgPreview.setImageURI(selectedImageUri);
            }

            btnSave.setText("Update");
        }

        // ===== Gallery Picker =====
        ActivityResultLauncher<String> pickImageLauncher =
                registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;
                        imgPreview.setImageURI(uri);
                    }
                });

        btnPick.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

        // ===== Save / Update =====
        btnSave.setOnClickListener(v -> {
            String name = etName.getText() == null ? "" : etName.getText().toString().trim();
            String priceText = etPrice.getText() == null ? "" : etPrice.getText().toString().trim();

            if (name.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "Please enter dish name and price", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceText);
            } catch (Exception e) {
                Toast.makeText(this, "Enter a valid price", Toast.LENGTH_SHORT).show();
                return;
            }

            String uriString = (selectedImageUri == null) ? null : selectedImageUri.toString();

            if (isEditMode) {
                if (editId == -1) {
                    Toast.makeText(this, "Edit error: missing ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                int rows = dao.updateMenuItem(editId, name, price, uriString);
                Toast.makeText(this, rows > 0 ? "Updated" : "Update failed", Toast.LENGTH_SHORT).show();
            } else {
                dao.insertMenuItem(name, price, uriString);
                Toast.makeText(this, "Menu item saved", Toast.LENGTH_SHORT).show();
            }

            finish();
        });
    }
}

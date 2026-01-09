package com.example.restaurant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.restaurant.util.Prefs;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class NotificationSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        SwitchMaterial swNew = findViewById(R.id.swNewBooking);
        SwitchMaterial swCancel = findViewById(R.id.swCancelled);
        SwitchMaterial swMenu = findViewById(R.id.swMenuChange);

        // Load saved values
        swNew.setChecked(Prefs.load(this, Prefs.KEY_NEW_BOOKING));
        swCancel.setChecked(Prefs.load(this, Prefs.KEY_CANCELLED));
        swMenu.setChecked(Prefs.load(this, Prefs.KEY_MENU));

        // Save when user changes
        swNew.setOnCheckedChangeListener((b, v) ->
                Prefs.save(this, Prefs.KEY_NEW_BOOKING, v));

        swCancel.setOnCheckedChangeListener((b, v) ->
                Prefs.save(this, Prefs.KEY_CANCELLED, v));

        swMenu.setOnCheckedChangeListener((b, v) ->
                Prefs.save(this, Prefs.KEY_MENU, v));
    }
}

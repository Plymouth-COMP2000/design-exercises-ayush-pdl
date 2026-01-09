package com.example.restaurant.notifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotifyHelper {

    public static final String CHANNEL_ID = "restaurant_events";

    public static void ensureChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm =
                    (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (nm == null) return;

            if (nm.getNotificationChannel(CHANNEL_ID) != null) return;

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Restaurant Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Bookings, cancellations, and menu updates");
            nm.createNotificationChannel(channel);
        }
    }

    public static void show(Context ctx, int id, String title, String body) {
        ensureChannel(ctx);

        if (Build.VERSION.SDK_INT >= 33) {
            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                return; // no crash if permission not granted
            }
        }

        NotificationCompat.Builder b = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // built-in, safe
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        int notifId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        NotificationManagerCompat.from(ctx).notify(notifId, b.build());
    }
}

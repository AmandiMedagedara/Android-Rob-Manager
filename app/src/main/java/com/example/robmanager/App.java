package com.example.robmanager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String ch1 = "adoo";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotification();
    }

    public void createNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(
                    ch1,
                    "channel 1",
            NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Hello");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}

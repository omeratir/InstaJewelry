package com.example.instajewelry;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    public static Context context;

    public void OnCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}

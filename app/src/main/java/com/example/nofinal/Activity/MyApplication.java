package com.example.nofinal.Activity;

import android.app.Application;
import android.content.Context;

/**
 * Created by YUZHENG on 2021/9/1
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}


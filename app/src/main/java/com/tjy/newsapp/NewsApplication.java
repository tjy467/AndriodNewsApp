package com.tjy.newsapp;

import android.app.Application;
import android.content.Context;

import com.orm.SugarApp;

public class NewsApplication extends SugarApp {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}

package com.example.mydialog;

import android.app.Application;

public class CYUtils extends Application {
    
    private static CYUtils instance;
    
    public static CYUtils getInstance() {
        return instance;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

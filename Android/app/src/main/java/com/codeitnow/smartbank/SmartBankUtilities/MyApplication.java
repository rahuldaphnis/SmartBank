package com.codeitnow.smartbank.SmartBankUtilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rahul Malhotra on 8/19/2017.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

}

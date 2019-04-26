package com.test.axxess.is;

import android.app.Application;

import com.test.axxess.is.ui.database.ImageTableManager;
import com.test.axxess.is.ui.database.Preference;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        ImageTableManager.initRoom(this);
        Preference.init(this);
    }
}

package com.guet.zigbee;

import android.app.Application;
import android.content.Context;

/**
 * Created by 尹文强 on 2017/5/28.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }
    public static Context getContext()
    {
        return context;
    }
}

package com.xinzy.qsbk;

import android.app.Application;

/**
 * Created by Xinzy on 2016/4/28.
 */
public class QsbkApplication extends Application
{
    private static QsbkApplication instance;

    public static QsbkApplication getInstance()
    {
        return instance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        instance = this;
    }
}

package com.xinzy.okhttp3.utils;

import android.util.Log;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class L
{
    private static boolean debug = false;

    public static void e(String msg)
    {
        if (debug)
        {
            Log.e("OkHttp", msg);
        }
    }

}


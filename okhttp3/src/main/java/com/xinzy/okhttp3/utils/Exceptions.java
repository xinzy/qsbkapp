package com.xinzy.okhttp3.utils;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Exceptions
{
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }


}

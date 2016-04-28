package com.xinzy.qsbk.common.api;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Apis
{
    private static final String DOMAIN = ".qiushibaike.com";

    public static final String BASE_API = "http://m2" + DOMAIN;

    public static final String PIC_SERVER = "http://pic" + DOMAIN;


    private static int COUNTER = 1;
    private static final String R = "cc7b51d7";

    public static final int getCounter()
    {
        int counter = COUNTER;
        COUNTER ++;

        return counter;
    }

    public static final String getR()
    {
        long time = System.currentTimeMillis() / 1000;
        return R + time;
    }
}

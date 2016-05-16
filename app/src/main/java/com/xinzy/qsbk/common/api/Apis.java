package com.xinzy.qsbk.common.api;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Apis
{
    private static final String DOMAIN = ".qiushibaike.com";

    public static final String BASE_API = "http://m2" + DOMAIN;

    public static final String PIC_SERVER = "http://pic" + DOMAIN;


    private static       AtomicInteger COUNTER = new AtomicInteger(1);
    private static final String        R       = "cc7b51d7";

    public static final String getR()
    {
        long time = System.currentTimeMillis() / 1000;
        return R + time;
    }

    private static final String CONTENT_URL = BASE_API + "/article/list/%1$s?page=%2$s&count=30&rqcnt=%3$s&r=%4$s";
    public static final String getContentListApi(String type, int page)
    {
        return String.format(CONTENT_URL, type, page, COUNTER.getAndIncrement(), getR());
    }

    private static final String COMMENT_URL = BASE_API + "/article/%1$s/comments?page=%2$s&count=50&rqcnt=%3$s&r=%4$s";
    public static final String getCommentListApi(int artic, int page)
    {
        return String.format(COMMENT_URL, artic, page, COUNTER.getAndIncrement(), getR());
    }

}

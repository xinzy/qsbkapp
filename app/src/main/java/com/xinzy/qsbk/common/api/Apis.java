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

    private static final String AWKWARD_URL = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=%1$d&min_time=%1$d&mpic=1&essence=1&message_cursor=-1&bd_longitude=0.000000&bd_latitude=0.000000&bd_city=&count=30&screen_width=768&iid=4462919942&device_id=17235163508&ac=wifi&channel=NHSQH5AN&aid=7&app_name=joke_essay&version_code=431&device_platform=android&ssmix=a&device_type=Nexus5&os_api=19&os_version=6.0.1&openudid=cec3474cb01d91c5&manifest_version_code=431";

    public static final String getAwkwardUrl(int catid, int time)
    {
        return String.format(AWKWARD_URL, catid, time);
    }
}

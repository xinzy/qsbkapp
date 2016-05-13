package com.xinzy.okhttp3.builder;

import com.xinzy.okhttp3.OkHttpUtils;
import com.xinzy.okhttp3.request.OtherRequest;
import com.xinzy.okhttp3.request.RequestCall;

/**
 * Created by Xinzy on 2016/4/27..
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}

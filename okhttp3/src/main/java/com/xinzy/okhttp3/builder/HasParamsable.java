package com.xinzy.okhttp3.builder;

import java.util.Map;

/**
 * Created by Xinzy on 2016/4/27..
 */
public interface HasParamsable
{
    public abstract OkHttpRequestBuilder params(Map<String, String> params);

    public abstract OkHttpRequestBuilder addParams(String key, String val);

}

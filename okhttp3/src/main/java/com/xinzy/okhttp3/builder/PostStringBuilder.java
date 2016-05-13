package com.xinzy.okhttp3.builder;

import com.xinzy.okhttp3.request.PostStringRequest;
import com.xinzy.okhttp3.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by Xinzy on 2016/4/27..
 */
public class PostStringBuilder extends OkHttpRequestBuilder
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType).build();
    }

    @Override
    public PostStringBuilder url(String url)
    {
        this.url = url;
        return this;
    }

    @Override
    public PostStringBuilder tag(Object tag)
    {
        this.tag = tag;
        return this;
    }


    @Override
    public PostStringBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }

    @Override
    public PostStringBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}

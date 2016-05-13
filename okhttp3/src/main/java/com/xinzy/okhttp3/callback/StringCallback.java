package com.xinzy.okhttp3.callback;

import okhttp3.Response;

import java.io.IOException;

/**
 * Created by Xinzy on 2016/4/27..
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }

}

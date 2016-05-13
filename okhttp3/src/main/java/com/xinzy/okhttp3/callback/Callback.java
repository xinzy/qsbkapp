package com.xinzy.okhttp3.callback;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T>
{
    /**
     * UI Thread
     *
     * @param request
     */
    public void onStart(Request request)
    {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onEnd()
    {
    }

    /**
     * UI Thread
     *
     * @param total
     * @param current
     */
    public void onLoading(long total, long current)
    {

    }
    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws Exception;

    public abstract void onError(Call call, Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response) throws Exception
        {
            return null;
        }

        @Override
        public void onError(Call call, Exception e)
        {

        }

        @Override
        public void onResponse(Object response)
        {

        }
    };

}
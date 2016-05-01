package com.xinzy.qsbk.logic.main.presenter;

import android.support.annotation.NonNull;

import com.xinzy.qsbk.common.util.Logger;
import com.xinzy.qsbk.logic.main.view.IContentView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Xinzy on 2016/4/28.
 */
public class ContentPresenter implements IContentPresenter
{
    @NonNull
    private IContentView mContentView;

    public ContentPresenter(@NonNull IContentView view)
    {
        mContentView = view;
        mContentView.setPresenter(this);
    }

    @Override
    public void start()
    {
    }

    @Override
    public void loading(int page)
    {
        mContentView.showLoading(true);

        OkHttpClient client  = new OkHttpClient();
        Request      request = new Request.Builder().get().url(mContentView.contentListApi()).build();
        Call         call    = client.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                if (response.cacheResponse() != null)
                {
                    Logger.e(response.cacheResponse().toString());
                } else
                {
                    Logger.e(response.body().string() + "    " + response.networkResponse().toString());
                }
            }
        });

    }
}

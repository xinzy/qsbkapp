package com.xinzy.qsbk.logic.main.presenter;

import android.support.annotation.NonNull;

import com.xinzy.qsbk.logic.main.view.IContentView;

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

    }
}

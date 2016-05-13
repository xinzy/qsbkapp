package com.xinzy.qsbk.logic.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.adapter.ContentAdapter;
import com.xinzy.qsbk.logic.main.presenter.IContentPresenter;

import java.util.List;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class ContentFragment extends AbsBaseFragment implements SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, IContentView
{
    public static final String TYPE_SUGGEST = "suggest";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "imgrank";
    public static final String TYPE_DAY = "day";

    private static final String PARAM_TYPE = "Type";

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private ContentAdapter mContentAdapter;

    private IContentPresenter mContentPresenter;

    private String type;
    private int page = 1;

    public ContentFragment()
    {
    }

    public static ContentFragment newInstance(String action)
    {
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PARAM_TYPE, action);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        type = getArguments().getString(PARAM_TYPE);
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_content;
    }

    @Override
    protected void initializeView()
    {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_view);
        mRefreshLayout.setOnRefreshListener(this);
        mListView = (ListView) findViewById(R.id.content_listview);
        mListView.setOnScrollListener(this);
        mContentAdapter = new ContentAdapter();
        mListView.setAdapter(mContentAdapter);

        mContentPresenter.loading(1);
    }

    @Override
    public void onRefresh()
    {
        page = 1;
        mContentPresenter.loading(page);
    }

    @Override
    public void showLoading(boolean isShow)
    {
        mRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public void loadResult(boolean success)
    {
        showLoading(false);
        if (success)
        {
            page++;
        }
    }

    @Override
    public void showDataAfterLoad(List<Content> contents, boolean refresh)
    {
        if (refresh)
        {
            mContentAdapter.replace(contents);
        } else
        {
            mContentAdapter.addAll(contents);
        }
    }

    @Override
    public String getType()
    {
        return type;
    }

    @Override
    public void setPresenter(@NonNull IContentPresenter presenter)
    {
        mContentPresenter = presenter;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {

    }
}

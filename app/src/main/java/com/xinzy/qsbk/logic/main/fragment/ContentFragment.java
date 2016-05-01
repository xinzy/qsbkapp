package com.xinzy.qsbk.logic.main.fragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.adapter.ContentAdapter;
import com.xinzy.qsbk.logic.main.presenter.IContentPresenter;
import com.xinzy.qsbk.logic.main.view.IContentView;

import java.util.List;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class ContentFragment extends AbsBaseFragment implements SwipeRefreshLayout.OnRefreshListener, IContentView
{
    public static final String TYPE_SUGGEST = "suggest";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "imgrank";
    public static final String TYPE_DAY = "day";

    public static final String PARAM_TYPE = "Type";

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private ContentAdapter mContentAdapter;

    private IContentPresenter mContentPresenter;

    private int page = 1;

    public ContentFragment()
    {
    }

    public static ContentFragment newInstance()
    {
        return new ContentFragment();
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
        mContentAdapter = new ContentAdapter();
        mListView.setAdapter(mContentAdapter);

        mContentPresenter.loading(1);
    }

    @Override
    public void onRefresh()
    {
        mContentPresenter.loading(page);
    }

    @Override
    public void showLoading(boolean isShow)
    {
        mRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public void showDataAfterLoad(List<Content> contents)
    {

    }

    @Override
    public String contentListApi()
    {
        return "http://m2.qiushibaike.com/article/list/suggest?page=1&type=refresh&count=30&rqcnt=66&r=cc7b51d71461740419960";
    }

    @Override
    public void setPresenter(@NonNull IContentPresenter presenter)
    {
        mContentPresenter = presenter;
    }
}

package com.xinzy.qsbk.logic.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;
import android.widget.ListView;

import com.xinzy.qsbk.ContentActivity;
import com.xinzy.qsbk.MainActivity;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.adapter.ContentAdapter;
import com.xinzy.qsbk.logic.main.presenter.IContentPresenter;
import com.xinzy.qsbk.logic.main.view.ContentItemView;

import java.util.List;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class ContentFragment extends AbsBaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, IContentView,
        MainActivity.ContentFragmentCallback, ContentItemView.OnItemViewListener
{
    public static final String TYPE_SUGGEST = "suggest";
    public static final String TYPE_VIDEO   = "video";
    public static final String TYPE_TEXT    = "text";
    public static final String TYPE_IMAGE   = "imgrank";
    public static final String TYPE_DAY     = "day";

    private static final String PARAM_TYPE = "Type";

    private SwipeRefreshLayout mRefreshLayout;
    private ListView           mListView;
    private ContentAdapter     mContentAdapter;

    private IContentPresenter mContentPresenter;

    private String type;
    private int     page      = 1;
    private boolean isLoading = false;

    public ContentFragment()
    {
    }

    public static ContentFragment newInstance(String action)
    {
        ContentFragment fragment = new ContentFragment();
        Bundle          bundle   = new Bundle();
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
        mListView.setOnScrollListener(this);
        mContentAdapter = new ContentAdapter();
        mContentAdapter.setOnItemViewListener(this);
        mListView.setAdapter(mContentAdapter);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mContentPresenter.start();
            }
        }, 150);
    }

    private void loading(boolean refresh)
    {
        if (!isLoading)
        {
            isLoading = true;
            if (refresh)
            {
                page = 1;
            }
            mContentPresenter.loading(page);
        }
    }

    @Override
    public void onRefresh()
    {
        loading(true);
    }

    @Override
    public void showLoading(boolean isShow)
    {
        mRefreshLayout.setRefreshing(isShow);
    }

    @Override
    public void loadResult(boolean success)
    {
        if (success)
        {
            page++;
        }
        isLoading = false;
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
        switch (scrollState)
        {
        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 滚动停止
            if (view.getLastVisiblePosition() == (view.getCount() - 1))
            {
                loading(false);
            } else if (view.getFirstVisiblePosition() == 0)
            {
                // 滚动到顶部
            }
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:  // 开始滚动
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 正在滚动
            break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
    }

    @Override
    public void onActionBarClick()
    {
        if (mContentAdapter.getCount() > 0)
        {
            mListView.smoothScrollToPosition(0);//滚到倒顶部
        }
    }

    @Override
    public void onFloatActionButtonClick()
    {
        loading(true);
    }

    @Override
    public void onItemClick(ContentItemView itemView, Content content, int position)
    {
        ContentActivity.start(getContext(), content);
    }

    @Override
    public void onAvatarClick(ContentItemView itemView, Content content, int position)
    {

    }

    @Override
    public void onImageClick(ContentItemView itemView, Content content, int position)
    {

    }

    @Override
    public void onSupportClick(ContentItemView itemView, Content content, int position)
    {
        mContentPresenter.onSupportClick(itemView, content);
    }

    @Override
    public void onUnsupportClick(ContentItemView itemView, Content content, int position)
    {
        mContentPresenter.onUnsupportClick(itemView, content);
    }

    @Override
    public void onCommentClick(ContentItemView itemView, Content content, int position)
    {

    }

    @Override
    public void onShareClick(ContentItemView itemView, Content content, int position)
    {

    }
}

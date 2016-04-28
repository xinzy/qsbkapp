package com.xinzy.qsbk.logic.main.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.logic.main.presenter.IContentPresenter;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class ContentFragment extends AbsBaseFragment
{
    public static final String TYPE_SUGGEST = "suggest";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_IMAGE = "imgrank";
    public static final String TYPE_DAY = "day";

    public static final String PARAM_TYPE = "Type";

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;

    private IContentPresenter mContentPresenter;

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
        mListView = (ListView) findViewById(R.id.content_listview);
    }


}

package com.xinzy.qsbk.logic.content.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.adapter.CommentAdapter;
import com.xinzy.qsbk.logic.content.presenter.IDetailPresenter;
import com.xinzy.qsbk.logic.content.view.ContentDetailView;

import java.util.List;

/**
 * Created by gaodun on 2016/5/16.
 */
public class DetailFragment extends AbsBaseFragment implements IDetailView, SwipeRefreshLayout.OnRefreshListener, ContentDetailView.OnItemViewListener
{
    private static final String KEY_CONTENT = "CONTENT";

    private ContentDetailView mHeaderView;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView;
    private CommentAdapter mCommentAdapter;

    private Content mContent;
    private IDetailPresenter mPresenter;
    private int page = 1;
    private boolean hasMore = true;
    private boolean isLoading = false;

    public static final DetailFragment newInstance(Content content)
    {
        DetailFragment fragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CONTENT, content);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_detail;
    }

    @Override
    protected void initializeView()
    {
        mContent = getArguments().getParcelable(KEY_CONTENT);
        mPresenter.setContent(mContent);

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.detail_refresh_layout);
        mRefreshLayout.setOnRefreshListener(this);

        mListView = (ListView) findViewById(R.id.comment_listview);
        mHeaderView = new ContentDetailView(getContext());
        mHeaderView.setData(mContent);
        mHeaderView.setOnItemViewListener(this);
        mListView.addHeaderView(mHeaderView);
        mCommentAdapter = new CommentAdapter();
        mListView.setAdapter(mCommentAdapter);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                mPresenter.start();
            }
        }, 150);
    }

    @Override
    public void onAvatarClick(ContentDetailView itemView, Content content)
    {

    }

    @Override
    public void onImageClick(ContentDetailView itemView, Content content)
    {

    }

    @Override
    public void onSupportClick(ContentDetailView itemView, Content content)
    {

    }

    @Override
    public void onUnsupportClick(ContentDetailView itemView, Content content)
    {

    }

    @Override
    public void onShareClick(ContentDetailView itemView, Content content)
    {

    }

    @Override
    public void setPresenter(IDetailPresenter presenter)
    {
        mPresenter = presenter;
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
            page ++;
        } else
        {
            hasMore = false;
        }
    }

    @Override
    public void showDataAfterLoad(List<Comment> comments, boolean refresh)
    {
        if (refresh)
        {
            showLoading(false);
            mCommentAdapter.replace(comments);
        } else
        {
            mCommentAdapter.addAll(comments);
        }
    }

    @Override
    public void onRefresh()
    {
        hasMore = true;
        loading(true);
    }

    private void loading(boolean refresh)
    {
        if (!isLoading && hasMore)
        {
            isLoading = true;

            if (refresh)
            {
                page = 1;
            }
            if (mPresenter != null)
            {
                mPresenter.loading(page);
            }
        }
    }
}

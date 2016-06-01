package com.xinzy.qsbk.logic.awkward.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.logic.awkward.adapter.SectionsPagerAdapter;
import com.xinzy.qsbk.logic.awkward.presenter.AwkwardPresenter;
import com.xinzy.qsbk.logic.awkward.presenter.IAwkwardPresenter;

/**
 * Created by Xinzy on 2016/5/31.
 */
public class AwkwardFragment extends AbsBaseFragment implements IAwkwardView, SwipeRefreshLayout.OnRefreshListener
{

	private static final String KEY_CATEGORY = "CATEGORY";

	private SwipeRefreshLayout mRefreshLayout;
	private ListView mListView;

	private SectionsPagerAdapter.Category mCategory;
	private IAwkwardPresenter mPresenter;
	private int mLoadingTime;

	public static final AwkwardFragment newInstance(SectionsPagerAdapter.Category category)
	{
		AwkwardFragment fragment = new AwkwardFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_CATEGORY, category);
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	protected int getLayout()
	{
		return R.layout.fragment_awkward;
	}

	@Override
	protected void initializeView()
	{
		mCategory = getArguments().getParcelable(KEY_CATEGORY);

		mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.awkward_refresh_layout);
		mRefreshLayout.setOnRefreshListener(this);
		mListView = (ListView) findViewById(R.id.awkward_listview);

		new AwkwardPresenter(this);
		mPresenter.setCategory(mCategory);
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				mPresenter.start();
			}
		}, 200);
	}

	@Override
	public void onRefresh()
	{
		loading(true);
	}

	private void loading(boolean refresh)
	{
		mPresenter.loading(refresh, mLoadingTime);
	}

	@Override
	public void showLoading(boolean isShow)
	{
		mRefreshLayout.setRefreshing(isShow);
	}

	@Override
	public void loadResult(int mintime)
	{
		if (mintime > 0)
		{
			mLoadingTime = mintime;
		}
	}

	@Override
	public void setPresenter(IAwkwardPresenter presenter)
	{
		mPresenter = presenter;
	}
}

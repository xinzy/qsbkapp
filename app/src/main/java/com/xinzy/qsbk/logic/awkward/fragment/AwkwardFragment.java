package com.xinzy.qsbk.logic.awkward.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.common.util.Logger;
import com.xinzy.qsbk.logic.awkward.adapter.AwkwardAdapter;
import com.xinzy.qsbk.logic.awkward.adapter.SectionsPagerAdapter;
import com.xinzy.qsbk.logic.awkward.presenter.AwkwardPresenter;
import com.xinzy.qsbk.logic.awkward.presenter.IAwkwardPresenter;

import java.util.List;

/**
 * Created by Xinzy on 2016/5/31.
 */
public class AwkwardFragment extends AbsBaseFragment implements IAwkwardView, SwipeRefreshLayout.OnRefreshListener
{

	private static final String KEY_CATEGORY = "CATEGORY";

	private SwipeRefreshLayout mRefreshLayout;
	private ListView           mListView;
	private AwkwardAdapter     mAdapter;

	private SectionsPagerAdapter.Category mCategory;
	private IAwkwardPresenter             mPresenter;
	private int                           mLoadingMinTime;
	private int                           mLoadingMaxTime;
	private boolean                       hasLoaded;
	private boolean                       isVisible;
	private boolean                       isInited;

	public static final AwkwardFragment newInstance(SectionsPagerAdapter.Category category)
	{
		AwkwardFragment fragment = new AwkwardFragment();
		Bundle          bundle   = new Bundle();
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
	public void onAttach(Context context)
	{
		super.onAttach(context);
		mCategory = getArguments().getParcelable(KEY_CATEGORY);
	}

	@Override
	protected void initializeView()
	{
		mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.awkward_refresh_layout);
		mRefreshLayout.setOnRefreshListener(this);
		mListView = (ListView) findViewById(R.id.awkward_listview);
		mAdapter = new AwkwardAdapter();
		mListView.setAdapter(mAdapter);

		new AwkwardPresenter(this);
		mPresenter.setCategory(mCategory);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		isInited = true;

		if (!hasLoaded && isVisible)
		{
			new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					loading(true);
				}
			}, 200);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser)
	{
		super.setUserVisibleHint(isVisibleToUser);

		if (isVisibleToUser)
		{
			isVisible = true;
			if (!hasLoaded && isInited)
			{
				loading(true);
			} else if (!hasLoaded)
			{
				isVisible = true;
			}
		}
	}

	@Override
	public void onRefresh()
	{
		loading(true);
	}

	private void loading(boolean refresh)
	{
		Logger.v("loading data; refresh = " + refresh);
		hasLoaded = true;
		mPresenter.loading(refresh, mLoadingMinTime);
	}

	@Override
	public void showLoading(boolean isShow)
	{
		mRefreshLayout.setRefreshing(isShow);
	}

	@Override
	public void loadResult(int mintime, int maxtime)
	{
	}

	@Override
	public void showData(List<Awkward> data)
	{
		mAdapter.addAll(data);
	}

	@Override
	public void setPresenter(IAwkwardPresenter presenter)
	{
		mPresenter = presenter;
	}
}

package com.xinzy.qsbk.logic.awkward.adapter;

import android.view.View;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsListAdapter;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.logic.awkward.view.AwkwardItemView;

/**
 * Created by Xinzy on 2016/6/2.
 */
public class AwkwardAdapter extends AbsListAdapter<Awkward>
{
	@Override
	protected int getLayout(int position)
	{
		return R.layout.item_awkward;
	}

	@Override
	protected void onSetData(View view, int position)
	{
		AwkwardItemView itemView = (AwkwardItemView) view;
		itemView.onSetData(mData.get(position), position);
	}
}

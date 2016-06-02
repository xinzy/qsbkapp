package com.xinzy.qsbk.logic.main.adapter;

import android.view.View;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsListAdapter;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.view.ContentItemView;

/**
 * Created by Xinzy on 2016/4/29.
 */
public class ContentAdapter extends AbsListAdapter<Content>
{

	private ContentItemView.OnItemViewListener mOnItemViewListener;

	public void setOnItemViewListener(ContentItemView.OnItemViewListener onItemViewListener)
	{
		this.mOnItemViewListener = onItemViewListener;
	}

	@Override
	protected int getLayout(int position)
	{
		return R.layout.item_content;
	}

	@Override
	protected void onSetData(View view, int position)
	{
		ContentItemView itemView = (ContentItemView) view;
		itemView.setOnItemViewListener(mOnItemViewListener);
		itemView.onSetData(mData.get(position), position);
	}
}

package com.xinzy.qsbk.logic.content.adapter;

import android.view.View;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsListAdapter;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.logic.content.view.DetailItemView;

/**
 * Created by gaodun on 2016/5/16.
 */
public class CommentAdapter extends AbsListAdapter<Comment>
{
    @Override
    protected int getLayout(int position)
    {
        return R.layout.item_comment;
    }

    @Override
    protected void onSetData(View view, int position)
    {
        DetailItemView itemView = (DetailItemView) view;
        itemView.onSetData(mData.get(position), position);
    }
}

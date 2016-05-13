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


    @Override
    protected int getLayout(int position)
    {
        return R.layout.item_content;
    }

    @Override
    protected void onSetData(View view, int position)
    {
        ContentItemView itemView = (ContentItemView) view;
        itemView.setData(mData.get(position), position);
    }
}

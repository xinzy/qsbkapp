package com.xinzy.qsbk.common.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xinzy.qsbk.common.ui.ItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinzy on 2016/4/28.
 */
public abstract class AbsListAdapter<T> extends BaseAdapter
{
    protected List<T>        mData;
    private   LayoutInflater mInflater;

    public AbsListAdapter()
    {
        mData = new ArrayList<>(0);
    }

    public AbsListAdapter(@Nullable List<T> lists)
    {
        if (lists == null)
        {
            mData = new ArrayList<>(0);
        } else
        {
            mData = lists;
        }
    }

    public List<T> getList()
    {
        return mData;
    }

    public void setData(@NonNull List<T> lists)
    {
        mData = lists;
        refresh();
    }

    public void clear()
    {
        mData.clear();
        refresh();
    }

    public void replace(@NonNull List<T> lists)
    {
        mData.clear();
        mData.addAll(lists);
        refresh();
    }

    public void add(@NonNull T t)
    {
        mData.add(t);
        refresh();
    }

    public void addAll(@NonNull List<T> lists)
    {
        mData.addAll(lists);
        refresh();
    }

    public void remove(int index)
    {
        if (index >= 0 && index < mData.size())
        {
            mData.remove(index);
            refresh();
        }
    }

    private synchronized void refresh()
    {
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position)
    {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            if (mInflater == null)
            {
                mInflater = LayoutInflater.from(parent.getContext());
            }
            convertView = mInflater.inflate(getLayout(position), parent, false);

            ItemView itemView = (ItemView) convertView;
            itemView.onInit();
        }
        onSetData(convertView, position);

        return convertView;
    }

    protected abstract int getLayout(int position);

    protected abstract void onSetData(View view, int position);
}

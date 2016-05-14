package com.xinzy.qsbk.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Xinzy on 2016/4/28.
 */
public abstract class AbsBaseFragment extends Fragment
{

    protected View rootView;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        rootView = inflater.inflate(getLayout(), container, false);

        return rootView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    @Nullable
    protected View findViewById(int id)
    {
        if (rootView != null)
        {
            return rootView.findViewById(id);
        }
        return null;
    }

    protected abstract int getLayout();

    protected abstract void initializeView();
}

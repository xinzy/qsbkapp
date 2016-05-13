package com.xinzy.qsbk.logic.main.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.model.Content;

/**
 * Created by Xinzy on 2016/5/9.
 */
public class ContentItemView extends LinearLayout
{
    protected Context mContext;

    private TextView usernameTextView;

    public ContentItemView(Context context)
    {
        super(context);
    }

    public ContentItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ContentItemView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context)
    {
        mContext = context;

        usernameTextView = (TextView) findViewById(R.id.username_text);
    }

    public void setData(Content content, int position)
    {
//        usernameTextView.setText(content.getUser().getUsername());

    }

}

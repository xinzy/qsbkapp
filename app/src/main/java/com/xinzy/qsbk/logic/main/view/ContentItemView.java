package com.xinzy.qsbk.logic.main.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.common.model.User;
import com.xinzy.qsbk.common.ui.ItemView;

/**
 * Created by Xinzy on 2016/5/9.
 */
public class ContentItemView extends LinearLayout implements ItemView
{
    protected Context mContext;

    private ImageView mAvatarImageView;
    private TextView usernameTextView;
    private TextView stateTextView;
    private TextView contentTextView;
    private TextView dataTextView;

    public ContentItemView(Context context)
    {
        super(context);

        init(context);
    }

    public ContentItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init(context);
    }

    public ContentItemView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
    }

    @Override
    public void onInit()
    {
        mAvatarImageView = (ImageView) findViewById(R.id.avatar_img);
        usernameTextView = (TextView) findViewById(R.id.username_text);
        stateTextView = (TextView) findViewById(R.id.state_text);
        contentTextView = (TextView) findViewById(R.id.content_text);
        dataTextView = (TextView) findViewById(R.id.data_text);
    }

    @Override
    public void onSetData(Object object, int position)
    {
        Content content = (Content) object;

        User user = content.getUser();
        usernameTextView.setText(user != null ? user.getUsername() : getResources().getString(R.string.anonymous));
        if (content.getType() == Content.Type.Hot)
        {
            Drawable drawable = getResources().getDrawable(R.mipmap.state_hot);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            stateTextView.setCompoundDrawables(null, null, drawable, null);
            stateTextView.setText(R.string.type_hot);
        } else if (content.getType() == Content.Type.Refresh)
        {
            Drawable drawable = getResources().getDrawable(R.mipmap.state_fresh);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            stateTextView.setCompoundDrawables(null, null, drawable, null);
            stateTextView.setText(R.string.type_flush);
        } else
        {
            stateTextView.setVisibility(View.GONE);
        }
        contentTextView.setText(content.getContent());
        dataTextView.setText(getResources().getString(R.string.content_data, content.getVote().getUp(), content.getVote().getDown(), content.getCommentCount(), content.getShareCount()));
    }

    @Override
    public void onClose()
    {

    }
}

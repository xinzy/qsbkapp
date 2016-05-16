package com.xinzy.qsbk.logic.content.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.User;
import com.xinzy.qsbk.common.ui.ItemView;

/**
 * Created by gaodun on 2016/5/16.
 */
public class DetailItemView extends LinearLayout implements ItemView
{
    protected Context mContext;

    private SimpleDraweeView mAvatarView;
    private TextView usernameTextView;
    private TextView floorTextView;
    private TextView contentTextView;
    private TextView timeTextView;
    private TextView praiseTextView;

    private Comment mComment;

    public DetailItemView(Context context)
    {
        super(context);
        init(context);
    }

    public DetailItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public DetailItemView(Context context, AttributeSet attrs, int defStyleAttr)
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
        mAvatarView = (SimpleDraweeView) findViewById(R.id.detail_avatar_view);
        usernameTextView = (TextView) findViewById(R.id.detail_username_text);
        floorTextView = (TextView) findViewById(R.id.detail_floor_text);
        contentTextView = (TextView) findViewById(R.id.detail_content_tet);
        timeTextView = (TextView) findViewById(R.id.detail_time_text);
        praiseTextView = (TextView) findViewById(R.id.detail_praise_text);
    }

    @Override
    public void onSetData(Object object, int position)
    {
        mComment = (Comment) object;
        final User user = mComment.getUser();

        if (user != null)
        {
            mAvatarView.setImageURI(Uri.parse(user.getAvatar()));
        }
        usernameTextView.setText(user == null ? "匿名用户" : user.getUsername());
        floorTextView.setText(mComment.getFloor() + "");
        contentTextView.setText(mComment.getContent());
        timeTextView.setText(mComment.getTime());
        praiseTextView.setText(mComment.getLikeCount() + "");
    }

    @Override
    public void onClose()
    {

    }

    public interface ItemViewListener
    {
        void onAvatarClick(DetailItemView itemView, Comment comment);
    }
}

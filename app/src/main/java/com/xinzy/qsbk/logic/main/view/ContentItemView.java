package com.xinzy.qsbk.logic.main.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.common.model.ImageSize;
import com.xinzy.qsbk.common.model.User;
import com.xinzy.qsbk.common.ui.ItemView;

/**
 * Created by Xinzy on 2016/5/9.
 */
public class ContentItemView extends LinearLayout implements ItemView, View.OnClickListener
{
    protected Context mContext;

    private SimpleDraweeView mAvatarImageView;
    private TextView         usernameTextView;
    private TextView         stateTextView;
    private TextView         contentTextView;
    private SimpleDraweeView contentImageView;
    private ImageView        supportImageView;
    private ImageView        unsupportImageView;

    private TextView           dataTextView;
    private OnItemViewListener mOnItemViewListener;
    private int                mPosition;
    private Content            mContent;

    private static final int[] IDS = {R.id.content_item_layout, R.id.share_image, R.id.comment_image,};

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

    public void setOnItemViewListener(OnItemViewListener mOnItemViewListener)
    {
        this.mOnItemViewListener = mOnItemViewListener;
    }

    @Override
    public void onInit()
    {
        mAvatarImageView = (SimpleDraweeView) findViewById(R.id.avatar_img);
        mAvatarImageView.setOnClickListener(this);
        usernameTextView = (TextView) findViewById(R.id.username_text);
        stateTextView = (TextView) findViewById(R.id.state_text);
        contentTextView = (TextView) findViewById(R.id.content_text);
        dataTextView = (TextView) findViewById(R.id.data_text);

        contentImageView = (SimpleDraweeView) findViewById(R.id.content_img);
        contentImageView.setOnClickListener(this);

        supportImageView = (ImageView) findViewById(R.id.support_image);
        supportImageView.setOnClickListener(this);
        unsupportImageView = (ImageView) findViewById(R.id.unsupport_image);
        unsupportImageView.setOnClickListener(this);

        for (int id : IDS)
        {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onSetData(Object object, int position)
    {
        Content content = (Content) object;
        mContent = content;
        mPosition = position;

        User user = content.getUser();
        usernameTextView.setText(user != null ? user.getUsername() : getResources().getString(R.string.anonymous));
        if (user != null)
        {
            mAvatarImageView.setImageURI(Uri.parse(user.getAvatar()));
        }

        if (content.getType() == Content.Type.Hot)
        {
            Drawable drawable = getResources().getDrawable(R.drawable.state_hot);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            stateTextView.setCompoundDrawables(null, null, drawable, null);
            stateTextView.setText(R.string.type_hot);
        } else if (content.getType() == Content.Type.Refresh)
        {
            Drawable drawable = getResources().getDrawable(R.drawable.state_fresh);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            stateTextView.setCompoundDrawables(null, null, drawable, null);
            stateTextView.setText(R.string.type_flush);
        } else
        {
            stateTextView.setVisibility(View.GONE);
        }
        contentTextView.setText(content.getContent());
        setDataText(content);

        supportImageView.setSelected(false);
        unsupportImageView.setSelected(false);
        if (content.getUserState() == Content.STATE_SUPPORT)
        {
            supportImageView.setSelected(true);
        } else if (content.getUserState() == Content.STATE_UNSUPPORT)
        {
            unsupportImageView.setSelected(true);
        }

        if (content.getFormat() == Content.Format.Image)
        {
            ImageSize imageSize = content.getMediumSize();
            String    imgurl    = content.getMediumImage();
            if (imageSize == null)
            {
                imageSize = content.getSmallSize();
                imgurl = content.getSmallImage();
            }
            if (imageSize == null || imageSize.getHeight() <= 0)
            {
                contentImageView.setVisibility(View.GONE);
            } else
            {
                contentImageView.setVisibility(View.VISIBLE);
                float ratio = imageSize.getWidth() * 1.0f / imageSize.getHeight();
                contentImageView.setAspectRatio(ratio);
                contentImageView.setImageURI(Uri.parse(imgurl));
            }
        } else
        {
            contentImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClose()
    {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.content_item_layout:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onItemClick(this, mContent, mPosition);
            }
            break;

        case R.id.avatar_img:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onAvatarClick(this, mContent, mPosition);
            }
            break;

        case R.id.content_img:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onImageClick(this, mContent, mPosition);
            }
            break;

        case R.id.support_image:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onSupportClick(ContentItemView.this, mContent, mPosition);
            }
            break;

        case R.id.unsupport_image:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onUnsupportClick(ContentItemView.this, mContent, mPosition);
            }
            break;

        case R.id.comment_image:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onCommentClick(ContentItemView.this, mContent, mPosition);
            }
            break;

        case R.id.share_image:

            if (mOnItemViewListener != null)
            {
                mOnItemViewListener.onShareClick(ContentItemView.this, mContent, mPosition);
            }
            break;
        }
    }

    public void setDataText(Content content)
    {
        dataTextView.setText(getResources().getString(R.string.content_data, content.getVote().getUp(), content.getVote().getDown(), content.getCommentCount(), content.getShareCount()));
    }

    public TextView getDataTextView()
    {
        return dataTextView;
    }

    public ImageView getSupportImageView()
    {
        return supportImageView;
    }

    public ImageView getUnsupportImageView()
    {
        return unsupportImageView;
    }

    public SimpleDraweeView getContentImageView()
    {
        return contentImageView;
    }

    public void startAnim(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.5f, 1.0f);
        animator.setDuration(800);
        animator.setRepeatCount(1);
        animator.start();
    }

    public interface OnItemViewListener
    {
        void onItemClick(ContentItemView itemView, Content content, int position);

        void onAvatarClick(ContentItemView itemView, Content content, int position);

        void onImageClick(ContentItemView itemView, Content content, int position);

        void onSupportClick(ContentItemView itemView, Content content, int position);

        void onUnsupportClick(ContentItemView itemView, Content content, int position);

        void onCommentClick(ContentItemView itemView, Content content, int position);

        void onShareClick(ContentItemView itemView, Content content, int position);
    }
}

package com.xinzy.qsbk.logic.content.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.media.CircleTransformation;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.User;
import com.xinzy.qsbk.common.ui.ItemView;
import com.xinzy.qsbk.common.widget.PraiseView;

/**
 * Created by gaodun on 2016/5/16.
 */
public class DetailItemView extends LinearLayout implements ItemView, View.OnClickListener
{
	private Context mContext;

	private ImageView mAvatarView;
	private TextView  usernameTextView;
	private TextView  floorTextView;
	private TextView  contentTextView;
	private TextView  timeTextView;
	private TextView  praiseTextView;

	private Comment          mComment;
	private ItemViewListener mItemViewListener;

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

	public void setItemViewListener(ItemViewListener mItemViewListener)
	{
		this.mItemViewListener = mItemViewListener;
	}

	@Override
	public void onInit()
	{
		mAvatarView = (ImageView) findViewById(R.id.detail_avatar_view);
		usernameTextView = (TextView) findViewById(R.id.detail_username_text);
		floorTextView = (TextView) findViewById(R.id.detail_floor_text);
		contentTextView = (TextView) findViewById(R.id.detail_content_text);
		timeTextView = (TextView) findViewById(R.id.detail_time_text);
		praiseTextView = (TextView) findViewById(R.id.detail_praise_text);

		mAvatarView.setOnClickListener(this);
		contentTextView.setOnClickListener(this);
		praiseTextView.setOnClickListener(this);
		setOnClickListener(this);
	}

	@Override
	public void onSetData(Object object, int position)
	{
		mComment = (Comment) object;
		final User user = mComment.getUser();

		if (user != null)
		{
			Glide.with(getContext()).load(user.getAvatar()).bitmapTransform(new CircleTransformation(mContext))
					.placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(mAvatarView);
		}
		usernameTextView.setText(user == null ? "匿名用户" : user.getUsername());
		floorTextView.setText(mComment.getFloor() + "");
		timeTextView.setText(mComment.getTime());
		praiseTextView.setText(mComment.getLikeCount() > 0 ? mComment.getLikeCount() + "" : "");
		praiseTextView.setSelected(mComment.isLiked());
		contentTextView.setText(mComment.parseContent());
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
		case R.id.detail_avatar_view:
			if (mItemViewListener != null)
			{
				mItemViewListener.onAvatarClick(this, mComment);
			}
			break;

		case R.id.detail_content_text:
			if (mItemViewListener != null)
			{
				mItemViewListener.onContentClick(this, mComment);
			}
			break;

		case R.id.detail_praise_text:
			if (mItemViewListener != null)
			{
				mItemViewListener.onPraiseClick(this, mComment);
			}
			break;

		default:
			if (mItemViewListener != null)
			{
				mItemViewListener.onItemClick(this, mComment);
			}
			break;
		}
	}

	public void showPraise()
	{
		praiseTextView.setSelected(true);
		praiseTextView.setText(mComment.getLikeCount() + "");
		PraiseView view = new PraiseView(getContext());
		view.setText("+1", Color.RED, 14);
		view.show(praiseTextView);
	}

	public interface ItemViewListener
	{
		void onAvatarClick(DetailItemView itemView, Comment comment);

		void onItemClick(DetailItemView itemView, Comment comment);

		void onContentClick(DetailItemView itemView, Comment comment);

		void onPraiseClick(DetailItemView view, Comment comment);
	}
}

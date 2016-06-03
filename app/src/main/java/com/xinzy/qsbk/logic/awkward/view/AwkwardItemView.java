package com.xinzy.qsbk.logic.awkward.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.media.CircleTransformation;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.common.model.Awkward.Image;
import com.xinzy.qsbk.common.model.Awkward.User;
import com.xinzy.qsbk.common.ui.ItemView;
import com.xinzy.qsbk.common.util.Utils;

/**
 * Created by Xinzy on 2016/6/2.
 */
public class AwkwardItemView extends LinearLayout implements ItemView, View.OnClickListener
{
	private static final int[] IDS = {R.id.content_item_layout, R.id.share_image, R.id.comment_image,};

	private ImageView mAvatarImageView;
	private TextView  usernameTextView;
	private TextView  contentTextView;
	private ImageView contentImageView;
	private ImageView videoTagImageView;
	private ImageView supportImageView;
	private ImageView unsupportImageView;
	private TextView  dataTextView;

	private Context mContext;
	private int     mPosition;
	private Awkward mAwkward;


	public AwkwardItemView(Context context)
	{
		super(context);
		init(context);
	}

	public AwkwardItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public AwkwardItemView(Context context, AttributeSet attrs, int defStyleAttr)
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
		mAvatarImageView.setOnClickListener(this);
		usernameTextView = (TextView) findViewById(R.id.username_text);
		contentTextView = (TextView) findViewById(R.id.content_text);
		dataTextView = (TextView) findViewById(R.id.data_text);

		videoTagImageView = (ImageView) findViewById(R.id.content_img_tag);
		contentImageView = (ImageView) findViewById(R.id.content_img);
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
		Awkward awkward = (Awkward) object;
		mAwkward = awkward;
		mPosition = position;

		User user = awkward.getAuthor();
		usernameTextView.setText(user != null ? user.getUsername() : getResources().getString(R.string.anonymous));
		if (user != null)
		{
			Glide.with(getContext()).load(user.getAvatar()).bitmapTransform(new CircleTransformation(mContext))
					.placeholder(R.drawable.default_avatar).error(R.drawable.default_avatar).into(mAvatarImageView);
		}

		contentTextView.setText(awkward.getContent());
		setDataText(awkward);

		supportImageView.setSelected(false);
		unsupportImageView.setSelected(false);
		if (awkward.getHobby() == Awkward.HOBBY_UP)
		{
			supportImageView.setSelected(true);
		} else if (awkward.getHobby() == Awkward.HOBBY_DOWN)
		{
			unsupportImageView.setSelected(true);
		}

		contentImageView.setVisibility(View.GONE);
		videoTagImageView.setVisibility(View.GONE);
		final int mediaType = awkward.getMediaType();
		if (mediaType == Awkward.TYPE_IMG || mediaType == Awkward.TYPE_GIF)
		{
			final Image image = awkward.getImage();
			if (image != null)
			{
				contentImageView.setVisibility(View.VISIBLE);
				Utils.adjustImageView(contentImageView, image.getWidth(), image.getHeight());
				if (image.isGif())
				{
					Glide.with(getContext()).load(image.getUrl()).asGif().placeholder(R.drawable.image_loading_placeholder)
							.error(R.drawable.image_loading_failure).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(contentImageView);
				} else
				{
					Glide.with(getContext()).load(image.getUrl()).placeholder(R.drawable.image_loading_placeholder)
							.error(R.drawable.image_loading_failure).into(contentImageView);
				}
			}
		} else if (mediaType == Awkward.TYPE_VIDEO)
		{
			Awkward.Video video = awkward.getVideo();
			if (video != null)
			{
				videoTagImageView.setVisibility(View.VISIBLE);
				contentImageView.setVisibility(View.VISIBLE);
				Utils.adjustImageView(contentImageView, video.getWidth(), video.getHeight());
				Glide.with(getContext()).load(video.getCover()).placeholder(R.drawable.image_loading_placeholder)
						.error(R.drawable.image_loading_failure).into(contentImageView);
			}
		}
	}

	public void setDataText(Awkward awkward)
	{
		dataTextView.setText(getResources().getString(R.string.awkward_data, awkward.getViews(), awkward.getFavors(), awkward.getComments(), awkward.getShares(), awkward.getUp(), awkward.getDown()));
	}

	@Override
	public void onClose()
	{
	}

	@Override
	public void onClick(View v)
	{

	}
}

package com.xinzy.qsbk.logic.awkward.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.common.model.Awkward.Image;
import com.xinzy.qsbk.common.model.Awkward.User;
import com.xinzy.qsbk.common.ui.ItemView;
import com.xinzy.qsbk.common.util.Logger;

/**
 * Created by Xinzy on 2016/6/2.
 */
public class AwkwardItemView extends LinearLayout implements ItemView, View.OnClickListener
{
	private static final int[] IDS = {R.id.content_item_layout, R.id.share_image, R.id.comment_image,};

	private SimpleDraweeView mAvatarImageView;
	private TextView         usernameTextView;
	private TextView         contentTextView;
	private SimpleDraweeView contentImageView;
	private ImageView		 videoTagImageView;
	private ImageView        supportImageView;
	private ImageView        unsupportImageView;
	private TextView         dataTextView;

	private int     mPosition;
	private Awkward mAwkward;


	public AwkwardItemView(Context context)
	{
		super(context);
	}

	public AwkwardItemView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public AwkwardItemView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void onInit()
	{
		mAvatarImageView = (SimpleDraweeView) findViewById(R.id.avatar_img);
		mAvatarImageView.setOnClickListener(this);
		usernameTextView = (TextView) findViewById(R.id.username_text);
		contentTextView = (TextView) findViewById(R.id.content_text);
		dataTextView = (TextView) findViewById(R.id.data_text);

		videoTagImageView = (ImageView) findViewById(R.id.content_img_tag);
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
		Awkward awkward = (Awkward) object;
		mAwkward = awkward;
		mPosition = position;

		User user = awkward.getAuthor();
		usernameTextView.setText(user != null ? user.getUsername() : getResources().getString(R.string.anonymous));
		if (user != null)
		{
			mAvatarImageView.setImageURI(Uri.parse(user.getAvatar()));
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
				float ratio = image.getWidth() * 1.0f / image.getHeight();
				contentImageView.setAspectRatio(ratio);
				Uri uri = Uri.parse(image.getUrl() + ".gif");
				if (image.isGif())
				{
					Logger.e("gif image = " + image.getUrl() + ".gif");
					DraweeController controller = Fresco.newDraweeControllerBuilder().setUri(uri).setTapToRetryEnabled(true)
							.setOldController(contentImageView.getController()).setAutoPlayAnimations(true).setControllerListener(l).build();
					contentImageView.setController(controller);
				} else
				{
					contentImageView.setImageURI(uri);
				}
			}
		} else if (mediaType == Awkward.TYPE_VIDEO)
		{
			Awkward.Video video = awkward.getVideo();
			if (video != null)
			{
				videoTagImageView.setVisibility(View.VISIBLE);
				contentImageView.setVisibility(View.VISIBLE);
				float ratio = video.getWidth() * 1.0f / video.getHeight();
				contentImageView.setAspectRatio(ratio);
				contentImageView.setImageURI(Uri.parse(video.getCover()));
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

	private ControllerListener l = new BaseControllerListener<ImageInfo>()
	{
		@Override
		public void onRelease(String id)
		{
			super.onRelease(id);
			Logger.e("onRelease " + id);
		}

		@Override
		public void onSubmit(String id, Object callerContext)
		{
			super.onSubmit(id, callerContext);
			Logger.e("onSubmit " + id);
		}

		@Override
		public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable)
		{
			super.onFinalImageSet(id, imageInfo, animatable);
			Logger.e("onFinalImageSet " + id);
		}

		@Override
		public void onIntermediateImageSet(String id, ImageInfo imageInfo)
		{
			super.onIntermediateImageSet(id, imageInfo);
			Logger.e("onIntermediateImageSet " + id);
		}

		@Override
		public void onIntermediateImageFailed(String id, Throwable throwable)
		{
			super.onIntermediateImageFailed(id, throwable);
			Logger.e("onIntermediateImageFailed " + id);
		}

		@Override
		public void onFailure(String id, Throwable throwable)
		{
			super.onFailure(id, throwable);
			Logger.e("onFailure " + id, throwable);

		}
	};
}

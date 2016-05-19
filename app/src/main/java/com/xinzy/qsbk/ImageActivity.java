package com.xinzy.qsbk;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.xinzy.qsbk.common.widget.PhotoDraweeView;
import com.xinzy.qsbk.common.widget.photo.OnViewTapListener;

public class ImageActivity extends AppCompatActivity implements OnViewTapListener
{
	private static final String KEY_IMAGE_URL = "IMAGE_URL";

	private PhotoDraweeView mImageView;
	private Toolbar         mToolbar;

	public static void start(Activity activity, View view, String url)
	{
		ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);

		Intent starter = new Intent(activity, ImageActivity.class);
		starter.putExtra(KEY_IMAGE_URL, url);
		ActivityCompat.startActivity(activity, starter, compat.toBundle());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		String imgUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
		if (TextUtils.isEmpty(imgUrl))
		{
			finish();
			return;
		}

		mToolbar = (Toolbar) findViewById(R.id.img_toolbar);
		setSupportActionBar(mToolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null)
		{
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		mImageView = (PhotoDraweeView) findViewById(R.id.scale_image_view);
		assert mImageView != null;
		mImageView.setOnViewTapListener(this);

		PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
		controller.setUri(imgUrl).setOldController(mImageView.getController()).setControllerListener(new BaseControllerListener<ImageInfo>()
		{
			@Override
			public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable)
			{
				super.onFinalImageSet(id, imageInfo, animatable);
				if (imageInfo != null)
				{
					mImageView.update(imageInfo.getWidth(), imageInfo.getHeight());
				}
			}
		});
		mImageView.setController(controller.build());
	}

	@Override
	public void onViewTap(View view, float x, float y)
	{
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}

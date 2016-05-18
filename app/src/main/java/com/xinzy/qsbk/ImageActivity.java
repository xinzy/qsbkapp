package com.xinzy.qsbk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.xinzy.qsbk.logic.image.widget.ScaleImageView;

public class ImageActivity extends AppCompatActivity
{
	private static final String KEY_IMAGE_URL = "IMAGE_URL";

	private ScaleImageView mImageView;

	public static void start(Context context, String url)
	{
		Intent starter = new Intent(context, ImageActivity.class);
		starter.putExtra(KEY_IMAGE_URL, url);
		context.startActivity(starter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		mImageView = (ScaleImageView) findViewById(R.id.scale_image_view);
		String imgUrl = getIntent().getStringExtra(KEY_IMAGE_URL);
		if (TextUtils.isEmpty(imgUrl))
		{
			finish();
		} else
		{
			mImageView.setImageURI(Uri.parse(imgUrl));
		}
	}
}

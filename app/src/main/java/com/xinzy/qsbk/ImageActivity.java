package com.xinzy.qsbk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;


public class ImageActivity extends AppCompatActivity
{
	private static final String KEY_IMAGE_URL = "IMAGE_URL";

	private Toolbar mToolbar;

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

	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			ActivityCompat.finishAfterTransition(this);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}

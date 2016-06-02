package com.xinzy.qsbk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.xinzy.qsbk.common.util.Logger;

public class SplashActivity extends AppCompatActivity
{
	public static final int PERMISSION_CODE = 0x100;

	private View container;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		container = findViewById(R.id.splash_container);

		boolean hasnoPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED);

		if (hasnoPermission)
		{
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, PERMISSION_CODE);
		} else
		{
			Logger.e("has permission");
			enter(true);
		}
	}

	private void enter(boolean delay)
	{
		if (delay)
		{
			new Handler().postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					MainActivity.start(SplashActivity.this);
					finish();
				}
			}, 2000);
		} else
		{
			MainActivity.start(SplashActivity.this);
			finish();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		switch (requestCode)
		{
		case PERMISSION_CODE:
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
			{
				enter(false);
			} else
			{
				Snackbar.make(container, "使用此app一定要同意访问外部存储哦", Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, PERMISSION_CODE);
					}
				}).show();
			}
			break;
		}
	}

	@Override
	public void onBackPressed()
	{
	}
}

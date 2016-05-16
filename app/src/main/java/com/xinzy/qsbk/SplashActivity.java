package com.xinzy.qsbk;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                MainActivity.start(SplashActivity.this);
                finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed()
    {

    }
}

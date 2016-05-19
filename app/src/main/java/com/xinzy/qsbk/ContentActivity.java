package com.xinzy.qsbk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.fragment.DetailFragment;
import com.xinzy.qsbk.logic.content.presenter.DetailPresenter;
import com.xinzy.qsbk.logic.content.presenter.IDetailPresenter;

public class ContentActivity extends AppCompatActivity
{

    private static final String KEY_CONTENT = "CONTENT";
    private static final String KEY_POSITION = "POSITION";

    private IDetailPresenter mDetailPresenter;
    private Content          mContent;

    public static void start(Activity activity, View view, Content content)
    {
        ActivityOptionsCompat compat  = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        Intent                starter = new Intent(activity, ContentActivity.class);
        starter.putExtra(KEY_CONTENT, content);
        ActivityCompat.startActivity(activity, starter, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        mContent = getIntent().getParcelableExtra(KEY_CONTENT);
        DetailFragment fragment = DetailFragment.newInstance(mContent);
        mDetailPresenter = new DetailPresenter(fragment);

        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case android.R.id.home:
            ActivityCompat.finishAfterTransition(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

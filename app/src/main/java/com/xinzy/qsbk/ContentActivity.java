package com.xinzy.qsbk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.fragment.DetailFragment;
import com.xinzy.qsbk.logic.content.presenter.DetailPresenter;
import com.xinzy.qsbk.logic.content.presenter.IDetailPresenter;

public class ContentActivity extends AppCompatActivity
{

    private static final String KEY_CONTENT = "CONTENT";

    private IDetailPresenter mDetailPresenter;
    private Content          mContent;

    public static void start(Context context, Content content)
    {
        Intent starter = new Intent(context, ContentActivity.class);
        starter.putExtra(KEY_CONTENT, content);
        context.startActivity(starter);
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

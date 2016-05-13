package com.xinzy.qsbk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xinzy.qsbk.logic.main.fragment.ContentFragment;
import com.xinzy.qsbk.logic.main.presenter.ContentPresenter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Toolbar              mToolBar;
    private FloatingActionButton mFloatingActionButton;
    private DrawerLayout         mDrawerLayout;
    private NavigationView       mNavigationView;

    private ContentPresenter mPresenter;

    public static void start(Context context)
    {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        showFragment(ContentFragment.TYPE_SUGGEST);
    }

    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        switch (id)
        {
        case R.id.nav_suggest:      //专享
            break;
        case R.id.nav_video:        //视频
            break;

        case R.id.nav_image:        //图片
            break;

        case R.id.nav_text:         //文字
            break;

        case R.id.nav_day:          //精华
            break;

        case R.id.nav_setting:      //设置
            break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(String action)
    {
        ContentFragment contentFragment = ContentFragment.newInstance(action);
        mPresenter = new ContentPresenter(contentFragment);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, contentFragment, action).commit();
    }
}

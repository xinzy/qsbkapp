package com.xinzy.qsbk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xinzy.qsbk.common.util.Logger;
import com.xinzy.qsbk.logic.main.fragment.ContentFragment;
import com.xinzy.qsbk.logic.main.presenter.ContentPresenter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

	private Toolbar              mToolBar;
	private FloatingActionButton mFloatingActionButton;
	private DrawerLayout         mDrawerLayout;
	private NavigationView       mNavigationView;
	private ContentPresenter     mPresenter;

	private ContentFragmentCallback mContentFragmentCallback;
	private String                  mCurrentDisplayType;
	private long                    lastBackPressedTime;
	private Fragment                currentDisplayFragment;

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
		Logger.e("MainActivity start");

		mToolBar = (Toolbar) findViewById(R.id.toolbar);
		mToolBar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mContentFragmentCallback != null)
				{
					mContentFragmentCallback.onActionBarClick();
				}
			}
		});
		setSupportActionBar(mToolBar);

		mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
		mFloatingActionButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				if (mContentFragmentCallback != null)
				{
					mContentFragmentCallback.onFloatActionButtonClick();
				}
			}
		});

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		mDrawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		mNavigationView = (NavigationView) findViewById(R.id.nav_view);
		mNavigationView.setNavigationItemSelectedListener(this);
		mNavigationView.setCheckedItem(R.id.nav_suggest);

		mCurrentDisplayType = ContentFragment.TYPE_SUGGEST;
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
			final long time = SystemClock.uptimeMillis();
			if (time - lastBackPressedTime <= 2000)
			{
				super.onBackPressed();
			} else
			{
				lastBackPressedTime = time;
				Snackbar.make(mDrawerLayout, "再按一次返回键退出程序~", Snackbar.LENGTH_LONG).show();
			}
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

			if (!ContentFragment.TYPE_SUGGEST.equals(mCurrentDisplayType))
			{
				mCurrentDisplayType = ContentFragment.TYPE_SUGGEST;
				showFragment(mCurrentDisplayType);
			}
			break;

		case R.id.nav_video:        //视频

			if (!ContentFragment.TYPE_VIDEO.equals(mCurrentDisplayType))
			{
				mCurrentDisplayType = ContentFragment.TYPE_VIDEO;
				showFragment(mCurrentDisplayType);
			}
			break;

		case R.id.nav_image:        //图片

			if (!ContentFragment.TYPE_IMAGE.equals(mCurrentDisplayType))
			{
				mCurrentDisplayType = ContentFragment.TYPE_IMAGE;
				showFragment(mCurrentDisplayType);
			}
			break;

		case R.id.nav_text:         //文字

			if (!ContentFragment.TYPE_TEXT.equals(mCurrentDisplayType))
			{
				mCurrentDisplayType = ContentFragment.TYPE_TEXT;
				showFragment(mCurrentDisplayType);
			}
			break;

		case R.id.nav_day:          //精华

			if (!ContentFragment.TYPE_DAY.equals(mCurrentDisplayType))
			{
				mCurrentDisplayType = ContentFragment.TYPE_DAY;
				showFragment(mCurrentDisplayType);
			}
			break;

		case R.id.nav_awkward:
			AwkwardActivity.start(this);
			break;

		case R.id.nav_setting:      //设置
			break;
		}

		mDrawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}

	private void showFragment(String action)
	{
		FragmentManager     manager     = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		final Fragment      fragment    = manager.findFragmentByTag(action);

		if (currentDisplayFragment != null) transaction.hide(currentDisplayFragment);

		if (fragment != null)
		{
			currentDisplayFragment = fragment;
			transaction.show(fragment);
		} else
		{
			ContentFragment contentFragment = ContentFragment.newInstance(action);
			mContentFragmentCallback = contentFragment;
			mPresenter = new ContentPresenter(contentFragment);
			transaction.add(R.id.content, contentFragment, action);

			currentDisplayFragment = contentFragment;
		}
		transaction.commit();
	}

	public interface ContentFragmentCallback
	{
		void onActionBarClick();

		void onFloatActionButtonClick();
	}
}

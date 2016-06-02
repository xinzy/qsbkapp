package com.xinzy.qsbk.logic.awkward.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.xinzy.qsbk.logic.awkward.fragment.AwkwardFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinzy on 2016/5/31.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter
{

	private List<Category> mCats;

	public SectionsPagerAdapter(FragmentManager fm)
	{
		super(fm);
		mCats = Category.list();
	}

	@Override
	public Fragment getItem(int position)
	{
		return AwkwardFragment.newInstance(mCats.get(position));
	}

	@Override
	public int getCount()
	{
		return mCats.size();
	}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return mCats.get(position).title;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
	}

	public static class Category implements Parcelable
	{
		public int    id;
		public String title;

		public Category(Parcel source)
		{
			id = source.readInt();
			title = source.readString();
		}

		public Category(int id, String title)
		{
			this.id = id;
			this.title = title;
		}

		@Override
		public String toString()
		{
			return title;
		}

		public static final List<Category> list()
		{
			List<Category> list = new ArrayList<>();
			list.add(new Category(-101, "推荐"));
			list.add(new Category(-104, "视频"));
			list.add(new Category(-103, "图片"));
			list.add(new Category(-102, "段子"));

			return list;
		}

		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeInt(id);
			dest.writeString(title);
		}

		public static final Creator<Category> CREATOR = new Creator<Category>()
		{
			@Override
			public Category createFromParcel(Parcel source) {return new Category(source);}

			@Override
			public Category[] newArray(int size) {return new Category[size];}
		};
	}
}

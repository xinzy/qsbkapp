package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Vote implements Parcelable
{
	private int up;
	private int down;

	public Vote()
	{
	}

	public Vote(JSONObject json)
	{
		up = json.optInt("up");
		down = json.optInt("down");
	}

	public static final Vote parse(String text)
	{
		try
		{
			return new Vote(new JSONObject(text));
		} catch (JSONException e)
		{
			return null;
		}
	}

	public int getUp()
	{
		return up;
	}

	public void setUp(int up)
	{
		this.up = up;
	}

	public int getDown()
	{
		return down;
	}

	public void setDown(int down)
	{
		this.down = down;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(this.up);
		dest.writeInt(this.down);
	}

	protected Vote(Parcel in)
	{
		this.up = in.readInt();
		this.down = in.readInt();
	}

	public static final Parcelable.Creator<Vote> CREATOR = new Parcelable.Creator<Vote>()
	{
		@Override
		public Vote createFromParcel(Parcel source)
		{
			return new Vote(source);
		}

		@Override
		public Vote[] newArray(int size)
		{
			return new Vote[size];
		}
	};
}

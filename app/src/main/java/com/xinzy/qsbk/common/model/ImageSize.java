package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

/**
 * Created by Xinzy on 2016/4/29.
 */
public class ImageSize implements Parcelable
{

    private int  width;
    private int  height;
    private long size;

    public ImageSize() {}

    public static ImageSize parse(JSONArray array)
    {
        if (array != null)
        {
            if (array.length() == 3)
            {
                ImageSize size = new ImageSize();
                size.width = array.optInt(0);
                size.height = array.optInt(1);
                size.size = array.optInt(2);
                return size;
            } else if (array.length() == 2)
            {
                ImageSize size = new ImageSize();
                size.width = array.optInt(0);
                size.height = array.optInt(1);
                size.size = 0;
                return size;
            }
        }

        return null;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    @Override
    public String toString()
    {
        return "ImageSize{" + "width=" + width + ", height=" + height + ", size=" + size + '}';
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeLong(this.size);
    }

    protected ImageSize(Parcel in)
    {
        this.width = in.readInt();
        this.height = in.readInt();
        this.size = in.readLong();
    }

    public static final Creator<ImageSize> CREATOR = new Creator<ImageSize>()
    {
        @Override
        public ImageSize createFromParcel(Parcel source) {return new ImageSize(source);}

        @Override
        public ImageSize[] newArray(int size) {return new ImageSize[size];}
    };
}

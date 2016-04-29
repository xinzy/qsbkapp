package com.xinzy.qsbk.common.model;

import org.json.JSONArray;

/**
 * Created by Xinzy on 2016/4/29.
 */
public class ImageSize
{

    private int width;
    private int height;
    private long size;

    public ImageSize() {}

    public static ImageSize parse(JSONArray array)
    {
        if (array != null && array.length() == 3)
        {
            ImageSize size = new ImageSize();
            size.width = array.optInt(0);
            size.height = array.optInt(1);
            size.size = array.optInt(2);
            return size;
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
}

package com.xinzy.qsbk.common.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Vote
{
    private int up;
    private int down;

    public Vote() {}

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
}

package com.xinzy.qsbk.common.model;

import com.xinzy.qsbk.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Comment
{
    private int id;
    private String content;
    private int parentId;
    private boolean liked;
    private int likeCount;
    private int floor;
    private long created;
    private User user;
    private Comment refer;

    public Comment() {}

    public Comment(JSONObject json)
    {
        id = json.optInt("id");
        content = json.optString("content");
        parentId = json.optInt("parent_id");
        liked = json.optBoolean("liked");
        likeCount = json.optInt("like_count");
        floor = json.optInt("floor");
        created = json.optInt("created_at") * 1000;
        user = User.parse(json.optString("user"));

        String ref = json.optString("refer");
        if (parentId != 0 && !Utils.isEmpty(ref))
        {
            refer = Comment.parse(ref);
        }
    }

    public static Comment parse(String text)
    {
        try
        {
            return new Comment(new JSONObject(text));
        } catch (JSONException e)
        {
            return null;
        }
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public void setParentId(int parentId)
    {
        this.parentId = parentId;
    }

    public void setLiked(boolean liked)
    {
        this.liked = liked;
    }

    public void setLikeCount(int likeCount)
    {
        this.likeCount = likeCount;
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    public void setCreated(long created)
    {
        this.created = created;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public void setRefer(Comment refer)
    {
        this.refer = refer;
    }
}

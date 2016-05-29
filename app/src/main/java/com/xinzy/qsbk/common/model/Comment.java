package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xinzy.qsbk.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Comment implements Parcelable
{
    private int     id;
    private String  content;
    private int     parentId;
    private boolean liked;
    private int     likeCount;
    private int     floor;
    private long    created;
    private User    user;
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

    public int getId()
    {
        return id;
    }

    public String getContent()
    {
        return content;
    }

    public int getParentId()
    {
        return parentId;
    }

    public boolean isLiked()
    {
        return liked;
    }

    public int getLikeCount()
    {
        return likeCount;
    }

    public int getFloor()
    {
        return floor;
    }

    public long getCreated()
    {
        return created;
    }

    public User getUser()
    {
        return user;
    }

    public Comment getRefer()
    {
        return refer;
    }

    public String getTime()
    {
        return Utils.timeFormat(created, "MM-dd HH:mm");
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

    public void addLikeCount()
    {
        likeCount++;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.content);
        dest.writeInt(this.parentId);
        dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.likeCount);
        dest.writeInt(this.floor);
        dest.writeLong(this.created);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.refer, flags);
    }

    protected Comment(Parcel in)
    {
        this.id = in.readInt();
        this.content = in.readString();
        this.parentId = in.readInt();
        this.liked = in.readByte() != 0;
        this.likeCount = in.readInt();
        this.floor = in.readInt();
        this.created = in.readLong();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.refer = in.readParcelable(Comment.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>()
    {
        @Override
        public Comment createFromParcel(Parcel source) {return new Comment(source);}

        @Override
        public Comment[] newArray(int size) {return new Comment[size];}
    };
}

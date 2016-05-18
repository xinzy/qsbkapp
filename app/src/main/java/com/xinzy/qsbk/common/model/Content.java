package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.util.Utils;

import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Content implements Parcelable
{
    public static final int STATE_NONE      = 0;
    public static final int STATE_SUPPORT   = 1;
    public static final int STATE_UNSUPPORT = 2;

    private Format  format;
    private String  image;
    private long    published;
    private String  tag;
    private User    user;
    private int     id;
    private Vote    vote;
    private long    created;
    private String  content;
    private String  state;
    private boolean allowComment;
    private int     commentCount;
    private int     shareCount;
    private Type    type;

    private ImageSize smallSize;
    private ImageSize mediumSize;

    // 以下是视频类型的数据
    private String highUrl;
    private String lowUrl;
    private String picUrl;
    private int    loop;

    private int userState;

    private int positionInList;

    public Content() {}

    public Content(JSONObject json)
    {
        format = Format.from(json.optString("format"));
        id = json.optInt("id");
        image = json.optString("image");
        published = json.optInt("published_at") * 1000;
        tag = json.optString("tag");
        user = User.parse(json.optString("user"));
        vote = Vote.parse(json.optString("votes"));
        created = json.optInt("created_at") * 1000;
        content = json.optString("content");
        state = json.optString("state");
        allowComment = json.optBoolean("allow_comment");
        commentCount = json.optInt("comments_count");
        shareCount = json.optInt("share_count");
        type = Type.from(json.optString("type"));

        if (!Utils.isEmpty(image))
        {
            JSONObject imgJson = json.optJSONObject("image_size");
            if (imgJson != null)
            {
                mediumSize = ImageSize.parse(imgJson.optJSONArray("m"));
                smallSize = ImageSize.parse(imgJson.optJSONArray("s"));
            }
        }

        if (format == Format.Video)
        {
            highUrl = json.optString("high_url");
            lowUrl = json.optString("low_url");
            picUrl = json.optString("pic_url");
            loop = json.optInt("loop");
        }
    }

    public static Content parse(JSONObject json)
    {
        return new Content(json);
    }

    private String getImgUrl(String type)
    {
        if (Utils.isEmpty(image))
        {
            return null;
        }

        return Apis.PIC_SERVER + "/system/pictures/" + (id / 10000) + "/" + id + "/" + type + "/" + image;
    }

    public String getMediumImage()
    {
        return getImgUrl("medium");
    }

    public String getSmallImage()
    {
        return getImgUrl("small");
    }

    public Format getFormat()
    {
        return format;
    }

    public void setFormat(Format format)
    {
        this.format = format;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public long getPublished()
    {
        return published;
    }

    public void setPublished(long published)
    {
        this.published = published;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Vote getVote()
    {
        return vote;
    }

    public void setVote(Vote vote)
    {
        this.vote = vote;
    }

    public long getCreated()
    {
        return created;
    }

    public void setCreated(long created)
    {
        this.created = created;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public boolean isAllowComment()
    {
        return allowComment;
    }

    public void setAllowComment(boolean allowComment)
    {
        this.allowComment = allowComment;
    }

    public int getCommentCount()
    {
        return commentCount;
    }

    public void setCommentCount(int commentCount)
    {
        this.commentCount = commentCount;
    }

    public int getShareCount()
    {
        return shareCount;
    }

    public void setShareCount(int shareCount)
    {
        this.shareCount = shareCount;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getHighUrl()
    {
        return highUrl;
    }

    public void setHighUrl(String highUrl)
    {
        this.highUrl = highUrl;
    }

    public String getLowUrl()
    {
        return lowUrl;
    }

    public void setLowUrl(String lowUrl)
    {
        this.lowUrl = lowUrl;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public int getLoop()
    {
        return loop;
    }

    public void setLoop(int loop)
    {
        this.loop = loop;
    }

    public ImageSize getSmallSize()
    {
        return smallSize;
    }

    public void setSmallSize(ImageSize smallSize)
    {
        this.smallSize = smallSize;
    }

    public ImageSize getMediumSize()
    {
        return mediumSize;
    }

    public void setMediumSize(ImageSize mediumSize)
    {
        this.mediumSize = mediumSize;
    }

    public int getUserState()
    {
        return userState;
    }

    public void setUserState(int userState)
    {
        this.userState = userState;
    }

    public int getPositionInList()
    {
        return positionInList;
    }

    public void setPositionInList(int positionInList)
    {
        this.positionInList = positionInList;
    }

    public static enum Type
    {
        Null(""),
        Hot("hot"),
        Refresh("refresh");

        private String value;

        private Type(String v)
        {
            value = v;
        }

        public String value()
        {
            return value;
        }

        public static final Type from(String v)
        {
            for (Type t : Type.values())
            {
                if (t.value.equals(v))
                {
                    return t;
                }
            }
            return Null;
        }
    }

    public static enum Format
    {
        Text("text"),
        Image("image"),
        Word("word"),
        Video("video");

        private String value;

        private Format(String v)
        {
            value = v;
        }

        public String value()
        {
            return value;
        }

        public static final Format from(String v)
        {
            for (Format f : Format.values())
            {
                if (f.value.equals(v))
                {
                    return f;
                }
            }

            return Word;
        }
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.format == null ? -1 : this.format.ordinal());
        dest.writeString(this.image);
        dest.writeLong(this.published);
        dest.writeString(this.tag);
        dest.writeParcelable(this.user, flags);
        dest.writeInt(this.id);
        dest.writeParcelable(this.vote, flags);
        dest.writeLong(this.created);
        dest.writeString(this.content);
        dest.writeString(this.state);
        dest.writeByte(this.allowComment ? (byte) 1 : (byte) 0);
        dest.writeInt(this.commentCount);
        dest.writeInt(this.shareCount);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeParcelable(this.smallSize, flags);
        dest.writeParcelable(this.mediumSize, flags);
        dest.writeString(this.highUrl);
        dest.writeString(this.lowUrl);
        dest.writeString(this.picUrl);
        dest.writeInt(this.loop);
        dest.writeInt(this.userState);
        dest.writeInt(positionInList);
    }

    protected Content(Parcel in)
    {
        int tmpFormat = in.readInt();
        this.format = tmpFormat == -1 ? null : Format.values()[tmpFormat];
        this.image = in.readString();
        this.published = in.readLong();
        this.tag = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.id = in.readInt();
        this.vote = in.readParcelable(Vote.class.getClassLoader());
        this.created = in.readLong();
        this.content = in.readString();
        this.state = in.readString();
        this.allowComment = in.readByte() != 0;
        this.commentCount = in.readInt();
        this.shareCount = in.readInt();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : Type.values()[tmpType];
        this.smallSize = in.readParcelable(ImageSize.class.getClassLoader());
        this.mediumSize = in.readParcelable(ImageSize.class.getClassLoader());
        this.highUrl = in.readString();
        this.lowUrl = in.readString();
        this.picUrl = in.readString();
        this.loop = in.readInt();
        this.userState = in.readInt();
        this.positionInList = in.readInt();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>()
    {
        @Override
        public Content createFromParcel(Parcel source) {return new Content(source);}

        @Override
        public Content[] newArray(int size) {return new Content[size];}
    };
}

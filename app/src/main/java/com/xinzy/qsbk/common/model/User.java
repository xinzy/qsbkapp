package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class User implements Parcelable
{
    private int    id;
    private String username;
    private String icon;
    private String state;
    private String lastDevice;
    private String role;

    public User() {}

    public User(JSONObject json)
    {
        if (json.has("uid"))
        {
            id = json.optInt("uid");
        } else if (json.has("id"))
        {
            id = json.optInt("id");
        }
        username = json.optString("login");
        icon = json.optString("icon");
        state = json.optString("state");
        role = json.optString("role");
        lastDevice = json.optString("last_device");
    }

    public static final User parse(String json)
    {
        if (Utils.isEmpty(json))
        {
            return null;
        }

        try
        {
            return new User(new JSONObject(json));
        } catch (JSONException e)
        {
            return null;
        }
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getLastDevice()
    {
        return lastDevice;
    }

    public void setLastDevice(String lastDevice)
    {
        this.lastDevice = lastDevice;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getAvatar()
    {
        if (Utils.isEmpty(icon))
        {
            return "";
        }

        return Apis.PIC_SERVER + "/system/avtnew/" + (id / 10000) + "/" + id + "/thumb/" + icon;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.icon);
        dest.writeString(this.state);
        dest.writeString(this.lastDevice);
        dest.writeString(this.role);
    }

    protected User(Parcel in)
    {
        this.id = in.readInt();
        this.username = in.readString();
        this.icon = in.readString();
        this.state = in.readString();
        this.lastDevice = in.readString();
        this.role = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
    {
        @Override
        public User createFromParcel(Parcel source) {return new User(source);}

        @Override
        public User[] newArray(int size) {return new User[size];}
    };
}

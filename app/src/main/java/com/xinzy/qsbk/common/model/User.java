package com.xinzy.qsbk.common.model;

import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class User
{
    private int id;
    private String username;
    private String icon;
    private String state;
    private String lastDevice;
    private String role;

    public User() {}

    public User(JSONObject json)
    {
        id = json.optInt("uid");
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
}

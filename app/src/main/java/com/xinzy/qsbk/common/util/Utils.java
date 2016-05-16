package com.xinzy.qsbk.common.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Utils
{

    public static final boolean isEmpty(CharSequence str)
    {
        return TextUtils.isEmpty(str) || "null".equals(str);
    }

    public static final String timeFormat(long time, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }
}

package com.xinzy.qsbk.common.util;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;

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

    public static final Point getScreenSize(Context context)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return new Point(dm.widthPixels, dm.heightPixels);
    }

    public static final int dp2px(Context context, int dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }
}

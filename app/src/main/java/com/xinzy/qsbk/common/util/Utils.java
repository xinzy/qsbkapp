package com.xinzy.qsbk.common.util;

import android.text.TextUtils;

/**
 * Created by Xinzy on 2016/4/27.
 */
public class Utils
{

    public static final boolean isEmpty(CharSequence str)
    {
        return TextUtils.isEmpty(str) || "null".equals(str);
    }

}

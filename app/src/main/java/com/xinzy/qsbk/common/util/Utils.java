package com.xinzy.qsbk.common.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

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

	public static final String formatTime(int ms)
	{
		int sec    = ms / 1000;
		int second = sec % 60;
		int min    = sec / 60;
		int minute = min % 60;
		int hor    = min / 60;

		return hor + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
	}

	public static final void setFullScreen(Activity activity, boolean isFull)
	{
		if (isFull)
		{
			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else
		{
			final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attrs);
		}
	}

	private static int IMG_MAX_WIDTH;
	public static final void adjustImageView(ImageView view, int width, int height)
	{
		if (IMG_MAX_WIDTH == 0)
		{
			IMG_MAX_WIDTH = getScreenSize(view.getContext()).x - dp2px(view.getContext(), 32);
		}
		ViewGroup.LayoutParams lp = view.getLayoutParams();
		if (width > IMG_MAX_WIDTH)
		{
			lp.width = IMG_MAX_WIDTH;
			lp.height = height * IMG_MAX_WIDTH / width;
		} else
		{
			lp.width = width;
			lp.height = height;
		}
		view.setLayoutParams(lp);
	}
}

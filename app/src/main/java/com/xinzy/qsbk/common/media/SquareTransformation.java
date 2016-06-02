package com.xinzy.qsbk.common.media;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Xinzy on 2016/6/2.
 */
public class SquareTransformation implements Transformation<Bitmap>
{

	private BitmapPool mBitmapPool;
	private int        mWidth;
	private int        mHeight;

	public SquareTransformation(Context context)
	{
		this(Glide.get(context).getBitmapPool());
	}

	public SquareTransformation(BitmapPool pool)
	{
		this.mBitmapPool = pool;
	}

	@Override
	public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight)
	{
		Bitmap source = resource.get();
		int    size   = Math.min(source.getWidth(), source.getHeight());

		mWidth = (source.getWidth() - size) / 2;
		mHeight = (source.getHeight() - size) / 2;

		Bitmap.Config config = source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
		Bitmap bitmap = mBitmapPool.get(mWidth, mHeight, config);
		if (bitmap == null)
		{
			bitmap = Bitmap.createBitmap(source, mWidth, mHeight, size, size);
		}

		return BitmapResource.obtain(bitmap, mBitmapPool);
	}

	@Override
	public String getId()
	{
		return "CropSquareTransformation(width=" + mWidth + ", height=" + mHeight + ")";
	}
}

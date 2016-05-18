package com.xinzy.qsbk.logic.image.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by gaodun on 2016/5/18.
 */
public class ScaleImageView extends SimpleDraweeView
{
	private ScaleGestureDetector mScaleDetector;
	private GestureDetector      mGestureDetector;

	private float mCurrentScale = 1.0f;
	private Matrix              mCurrentMatrix;
	private float               mMidX;
	private float               mMidY;
	private OnClickListener     mClickListener;
	private OnLongPressListener mLongPressListener;

	public ScaleImageView(Context context)
	{
		this(context, null);
	}

	public ScaleImageView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public ScaleImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);

		init();
	}

	private void init()
	{
		setScaleType(ScaleType.CENTER_INSIDE);
		ScaleGestureDetector.OnScaleGestureListener mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener()
		{
			@Override
			public boolean onScale(ScaleGestureDetector detector)
			{
				float scaleFactor = detector.getScaleFactor();
				float newScale    = mCurrentScale * scaleFactor;
				// Prevent from zooming out more than original
				if (newScale > 1.0f)
				{
					// We initialize this lazily so that we don't have to register (and force the user
					// to unregister) a global layout listener on the view.
					if (mMidX == 0.0f)
					{
						mMidX = getWidth() / 2.0f;
					}
					if (mMidY == 0.0f)
					{
						mMidY = getHeight() / 2.0f;
					}
					mCurrentScale = newScale;
					// support pinch zoom
					mCurrentMatrix.postScale(scaleFactor, scaleFactor, mMidX, mMidY);
					invalidate();
				} else
				{
					scaleFactor = 1.0f / mCurrentScale;
					reset();
				}

				return true;
			}
		};
		mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
		mCurrentMatrix = new Matrix();

		GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener()
		{
			public void onLongPress(MotionEvent e)
			{
				// support long press listener
				if (mLongPressListener != null)
				{
					mLongPressListener.onLongPress();
				}
			}

			public boolean onSingleTapConfirmed(MotionEvent e)
			{
				// support single tap listener
				if (mClickListener != null)
				{
					mClickListener.onClick();
				}
				return true;
			}

			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
			{
				// support drag
				// disable drag when normal scale
				if (mCurrentScale > 1.0f)
				{
					mCurrentMatrix.postTranslate(-distanceX, -distanceY);
					invalidate();
				}
				return true;
			}
		};
		mGestureDetector = new GestureDetector(getContext(), mGestureListener);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
	}

	@Override
	protected void onDraw(@NonNull Canvas canvas)
	{
		int saveCount = canvas.save();
		canvas.concat(mCurrentMatrix);
		super.onDraw(canvas);
		canvas.restoreToCount(saveCount);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		mScaleDetector.onTouchEvent(event);
		if (!mScaleDetector.isInProgress())
		{
			mGestureDetector.onTouchEvent(event);
		}

		return true;
	}

	/**
	 * Resets the zoom of the attached image.
	 * This has no effect if the image has been destroyed
	 */
	public void reset()
	{
		mCurrentMatrix.reset();
		mCurrentScale = 1.0f;
		invalidate();
	}

	public void setOnLongPressListener(OnLongPressListener listener)
	{
		mLongPressListener = listener;
	}

	public interface OnLongPressListener
	{
		void onLongPress();
	}

	public void setOnClickListener(OnClickListener listener)
	{
		mClickListener = listener;
	}

	public interface OnClickListener
	{
		void onClick();
	}
}

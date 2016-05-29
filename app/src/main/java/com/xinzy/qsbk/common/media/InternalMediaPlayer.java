package com.xinzy.qsbk.common.media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

/**
 * Created by gaodun on 2016/5/25.
 */
public class InternalMediaPlayer extends MediaPlayer
{
	public static final int STATUS_IDEL             = 0x0;
	public static final int STATUS_PLAYING          = 0x1;
	public static final int STATUS_PAUSE            = 0x2;
	public static final int STATUS_PLAY_INTERRUPT   = 0x4;
	public static final int STATUS_PREPARE_FOR_PLAY = 0x8;

	private int mStatus = STATUS_IDEL;

	private OnStatusChangeListener mStatusChangeListener;
	private Handler mHandler;

	public InternalMediaPlayer()
	{
		setAudioStreamType(AudioManager.STREAM_MUSIC);
		setLooping(false);
		mHandler = new MediaHanlder();
	}

	public void setStatus(int status)
	{
		this.mStatus = status;
	}

	public int getStatus()
	{
		return mStatus;
	}

	public boolean isIdel()
	{
		return mStatus == STATUS_IDEL;
	}

	public boolean isPause()
	{
		return mStatus == STATUS_PAUSE;
	}

	public boolean isInterrupt()
	{
		return mStatus == STATUS_PLAY_INTERRUPT;
	}

	public boolean isPrepareForPlay()
	{
		return mStatus == STATUS_PREPARE_FOR_PLAY;
	}

	public void setOnStatusChangeListener(OnStatusChangeListener l)
	{
		mStatusChangeListener = l;
	}

	public void preparing()
	{
		prepareAsync();
		final int status = mStatus;
		mStatus = STATUS_PREPARE_FOR_PLAY;

		if (status != mStatus && mStatusChangeListener != null)
		{
			mStatusChangeListener.onStatusChanged(mStatus);
		}
	}

	public void interrupt()
	{
		pause();
		final int status = mStatus;
		mStatus = STATUS_PLAY_INTERRUPT;

		if (status != mStatus && mStatusChangeListener != null)
		{
			mStatusChangeListener.onStatusChanged(mStatus);
		}
	}

	@Override
	public void start() throws IllegalStateException
	{
		super.start();
		mHandler.sendEmptyMessageDelayed(MediaHanlder.MSG_PROGRESS_CHANGE, 1000);
		final int status = mStatus;
		mStatus = STATUS_PLAYING;

		if (status != mStatus && mStatusChangeListener != null)
		{
			mStatusChangeListener.onStatusChanged(mStatus);
		}
	}

	@Override
	public void pause() throws IllegalStateException
	{
		super.pause();
		mHandler.removeMessages(MediaHanlder.MSG_PROGRESS_CHANGE);
		final int status = mStatus;
		mStatus = STATUS_PAUSE;

		if (status != mStatus && mStatusChangeListener != null)
		{
			mStatusChangeListener.onStatusChanged(mStatus);
		}
	}

	@Override
	public void reset()
	{
		super.reset();
		mHandler.removeMessages(MediaHanlder.MSG_PROGRESS_CHANGE);
		final int status = mStatus;
		mStatus = STATUS_IDEL;

		if (status != mStatus && mStatusChangeListener != null)
		{
			mStatusChangeListener.onStatusChanged(mStatus);
		}
	}

	public interface OnStatusChangeListener
	{
		void onProgressChanged(int current, int total);

		void onStatusChanged(int status);
	}

	class MediaHanlder extends Handler
	{
		public static final int MSG_PROGRESS_CHANGE = 100;

		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_PROGRESS_CHANGE:
				final int current = getCurrentPosition();
				final int duration = getDuration();
				if (mStatusChangeListener != null)
				{
					mStatusChangeListener.onProgressChanged(current, duration);
				}
				if (current >= duration || current + 1000 > duration)
				{

				} else
				{
					sendEmptyMessageDelayed(MSG_PROGRESS_CHANGE, 1000);
				}
				break;
			}
		}
	}
}

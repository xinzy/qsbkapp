package com.xinzy.qsbk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.xinzy.qsbk.common.media.InternalMediaPlayer;
import com.xinzy.qsbk.common.util.Utils;

import java.io.IOException;

public class MediaActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnCompletionListener,
        View.OnClickListener, InternalMediaPlayer.OnStatusChangeListener, SeekBar.OnSeekBarChangeListener
{
    private static final String TAG = "MediaActivity";
    private static final String KEY_MEDIA_URL = "MEDIA_URL";
    
    private FrameLayout         mVideoFrame;
    private SurfaceView         mSurfaceView;
    private SurfaceHolder       mHolder;
    private InternalMediaPlayer mMediaPlayer;

    private LinearLayout controlBar;
    private ImageButton  mPlayBtn;
    private SeekBar      mSeekbar;
    private TextView     currentText;
    private TextView     totalText;

    private boolean isSurfaceCreated;
    private boolean isControlBarShow = true;
    private String  mediaUrl;

    private TranslateAnimation showAnim;
    private TranslateAnimation hideAnim;

    public static void start(Activity activity, View view, String url)
    {
        ActivityOptionsCompat compat  = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        Intent                starter = new Intent(activity, MediaActivity.class);
        starter.putExtra(KEY_MEDIA_URL, url);
        ActivityCompat.startActivity(activity, starter, compat.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mediaUrl = getIntent().getStringExtra(KEY_MEDIA_URL);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);

        mVideoFrame = (FrameLayout) findViewById(R.id.videoFrame);
        mVideoFrame.setOnClickListener(this);
        controlBar = (LinearLayout) findViewById(R.id.control_bar);
        mPlayBtn = (ImageButton) findViewById(R.id.play);
        setPlayBtnStatus(false);
        mPlayBtn.setOnClickListener(this);
        mSeekbar = (SeekBar) findViewById(R.id.seekBar);
        mSeekbar.setOnSeekBarChangeListener(this);
        currentText = (TextView) findViewById(R.id.currentTime);
        currentText.setText(Utils.formatTime(0));
        totalText = (TextView) findViewById(R.id.totalTime);
        totalText.setText(Utils.formatTime(0));

        showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);
        hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        showAnim.setDuration(300);
        hideAnim.setDuration(300);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(TAG, "onResume: ");

        if (mMediaPlayer != null && mMediaPlayer.isInterrupt() && isSurfaceCreated)
        {
            mMediaPlayer.start();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d(TAG, "onPause: ");

        if (mMediaPlayer.isPlaying())
        {
            mMediaPlayer.interrupt();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            ActivityCompat.finishAfterTransition(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        if (mMediaPlayer != null)
        {
            if (mMediaPlayer.isPlaying())
            {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.play:
            if (isSurfaceCreated)
            {
                if (mMediaPlayer.isPause())
                {
                    mMediaPlayer.start();
                } else if (mMediaPlayer.isIdel())
                {
                    try
                    {
                        mMediaPlayer.setDataSource(mediaUrl);
                        mMediaPlayer.preparing();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                } else if (mMediaPlayer.isPlaying())
                {
                    mMediaPlayer.pause();
                }
            }
            break;

        case R.id.videoFrame:
            toggleControlBar();
            break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d(TAG, "surfaceCreated: ");
        isSurfaceCreated = true;
        initMediaPlayer();

        if (mMediaPlayer.isInterrupt())
        {
            mMediaPlayer.start();
        }
    }

    private void initMediaPlayer()
    {
        if (mMediaPlayer == null)
        {
            mMediaPlayer = new InternalMediaPlayer();
        }
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnVideoSizeChangedListener(this);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(mHolder);
        mMediaPlayer.setOnStatusChangeListener(this);
        mMediaPlayer.setScreenOnWhilePlaying(true);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getSupportActionBar().hide();
            ViewGroup.LayoutParams lp = mVideoFrame.getLayoutParams();
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoFrame.setLayoutParams(lp);

            //全屏
            Utils.setFullScreen(this, true);

            mSurfaceView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    setSurfaceSize();
                }
            }, 300);
        } else
        {
            getSupportActionBar().show();
            ViewGroup.LayoutParams lp = mVideoFrame.getLayoutParams();
            lp.height = Utils.dp2px(this, 320);
            mVideoFrame.setLayoutParams(lp);

            // 取消全屏
            Utils.setFullScreen(this, false);

            mSurfaceView.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    setSurfaceSize();
                }
            }, 300);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
    {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.d(TAG, "surfaceDestroyed: ");
        isSurfaceCreated = false;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        mMediaPlayer.reset();
        mSeekbar.setProgress(0);
        currentText.setText(Utils.formatTime(0));
        totalText.setText(Utils.formatTime(0));
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
    {
        setSurfaceSize();
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d(TAG, "onPrepared: (" + mp.getVideoWidth() + ", " + mp.getVideoHeight() + ")");
        if (mMediaPlayer.isPrepareForPlay())
        {
            mMediaPlayer.start();
            mSeekbar.setMax(mp.getDuration());
            totalText.setText(Utils.formatTime(mp.getDuration()));
        }
    }

    private void setSurfaceSize()
    {
        if (mMediaPlayer == null || mMediaPlayer.getVideoHeight() == 0 || mMediaPlayer.getVideoWidth() == 0) return ;

        int width = mMediaPlayer.getVideoWidth();
        int height = mMediaPlayer.getVideoHeight();
        int viewWidth = mVideoFrame.getWidth();
        int viewHeight = mVideoFrame.getHeight();

        float scale = 0f;
        int scaleWidth = 0;
        int scaleHeight = 0;
        if (width * 1f / viewWidth > height * 1f / viewHeight)
        {
            scale = width * 1f / viewWidth;
            scaleWidth = viewWidth;
            scaleHeight = (int) (height / scale);
        } else
        {
            scale = height * 1f / viewHeight;
            scaleHeight = viewHeight;
            scaleWidth = (int) (width / scale);
        }

        Log.d(TAG, "onVideoSizeChanged: (" + scaleWidth + ", " + scaleHeight + ")");
        ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();
        lp.width = scaleWidth;
        lp.height = scaleHeight;
        mSurfaceView.setLayoutParams(lp);
    }

    private void setPlayBtnStatus(boolean isPlaying)
    {
        mPlayBtn.setImageResource(isPlaying ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
    }

    private void toggleControlBar()
    {
        if (isControlBarShow)
        {
            controlBar.startAnimation(hideAnim);
            controlBar.setVisibility(View.GONE);
            isControlBarShow = false;
        } else
        {
            controlBar.startAnimation(showAnim);
            controlBar.setVisibility(View.VISIBLE);
            isControlBarShow = true;
        }
    }

    private void toggleControlBarDelay(final boolean show)
    {
        if (show != isControlBarShow)
        {
            controlBar.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    toggleControlBar();
                }
            }, 3000);
        }
    }

    @Override
    public void onProgressChanged(int current, int total)
    {
        if (total != mSeekbar.getMax())
        {
            mSeekbar.setMax(total);
            totalText.setText(Utils.formatTime(total));
        }
        mSeekbar.setProgress(current);
        currentText.setText(Utils.formatTime(current));
    }

    @Override
    public void onStatusChanged(int status)
    {
        Log.d(TAG, "onStatusChanged: " + status);
        switch (status)
        {
        case InternalMediaPlayer.STATUS_IDEL:
            setPlayBtnStatus(false);
            break;

        case InternalMediaPlayer.STATUS_PAUSE:
        case InternalMediaPlayer.STATUS_PLAY_INTERRUPT:
            setPlayBtnStatus(false);
            break;

        case InternalMediaPlayer.STATUS_PLAYING:
        case InternalMediaPlayer.STATUS_PREPARE_FOR_PLAY:
            setPlayBtnStatus(true);
            break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if (fromUser)
        {
            mMediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {
    }

}

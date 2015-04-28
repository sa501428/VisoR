package com.visor.theater3d;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private static final String TAG = "MediaPlayerSurfaceStubActivity";

	protected Resources mResources;

	private VideoSurfaceView mVideoView = null;
	private MediaPlayer mMediaPlayer = null;
	private boolean allowSystemExiting = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


		mResources = getResources();
		mMediaPlayer = new MediaPlayer();

		try {
			AssetFileDescriptor afd = mResources.openRawResourceFd(R.raw.testvideo);
			mMediaPlayer.setDataSource(
			        afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			afd.close();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

		mVideoView = new VideoSurfaceView(this, mMediaPlayer);
		setContentView(mVideoView);
		
		

	}

	@Override
	protected void onResume() {
		super.onResume();
		mVideoView.onResume();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if(allowSystemExiting)
			System.exit(0);
		allowSystemExiting = true;
	}
	
	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		super.onKeyLongPress(keyCode, event);
		return processAndroidButton( keyCode,  event);	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		return processAndroidButton( keyCode,  event);
	}
	
	private boolean processAndroidButton(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
}

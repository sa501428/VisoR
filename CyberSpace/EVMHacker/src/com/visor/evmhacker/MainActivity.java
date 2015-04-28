package com.visor.evmhacker;

import com.visor.streaming.MyGLSurfaceView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements SurfaceTexture.OnFrameAvailableListener{

	private MyGLSurfaceView glSurfaceView;
	private CameraAdapter cameraAdapter = new CameraAdapter(this);

	/* start of all overriding activity methods */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		glSurfaceView = new MyGLSurfaceView(this, cameraAdapter);
		setContentView(glSurfaceView);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		cameraAdapter.destroyCamera();
	}

	@Override
	public void onResume(){
		super.onResume();
		cameraAdapter.restartCamera();
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		super.onKeyLongPress(keyCode, event);
		return processAndroidButton(keyCode, event);	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		return processAndroidButton( keyCode,  event);
	}

	private boolean processAndroidButton(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			System.exit(0);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void onFrameAvailable(SurfaceTexture surfaceTexture){
		glSurfaceView.requestRender();
	}

	public void setCameraSurface(SurfaceTexture cameraSurface) {
		glSurfaceView.setCameraSurface(cameraSurface);
	}
}

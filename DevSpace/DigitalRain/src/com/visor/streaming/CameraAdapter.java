package com.visor.streaming;

import java.io.IOException;

import com.visor.digitalrain.MainActivity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class CameraAdapter{

	private Camera mCamera = null;
	private SurfaceTexture cameraSurface;
	private MainActivity mainActivity;
	
	public CameraAdapter(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	public void startCamera(int cameraTexture) {
		cameraSurface = new SurfaceTexture(cameraTexture);
		cameraSurface.setOnFrameAvailableListener(mainActivity);
		mainActivity.setCameraSurface(cameraSurface);
		setupCamera(cameraSurface);
	
	}

	public void restartCamera() {
		mainActivity.setCameraSurface(cameraSurface);
		setupCamera(cameraSurface);
	}

	private void setupCamera(SurfaceTexture surface) {
		destroyCamera();
		mCamera = Camera.open();
		Camera.Parameters parameters = mCamera.getParameters();
		//parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
		mCamera.setParameters(parameters);

		try
		{
			mCamera.setPreviewTexture(surface);
			mCamera.startPreview();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void destroyCamera() {
		if(mCamera != null){
			mCamera.stopPreview();
			mCamera.release();
		}
		mCamera = null;
	}	
}

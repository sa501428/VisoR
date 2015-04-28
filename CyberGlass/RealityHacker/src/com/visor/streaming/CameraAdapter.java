package com.visor.streaming;

import java.io.IOException;

import com.visor.visionhacker.MainActivity;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class CameraAdapter{

	private Camera mCamera = null;
	private SurfaceTexture cameraSurface;
	private MainActivity delegate;
	
	public CameraAdapter(MainActivity delegate){
		this.delegate = delegate;
	}

	public void setupCamera(SurfaceTexture surface) {
		destroyCamera();
		mCamera = Camera.open();
		//mCamera.setDisplayOrientation(0);
		Camera.Parameters parameters = mCamera.getParameters();
		//parameters.setPreviewFpsRange(40000,120000);
		//parameters.setPreviewFpsRange( 30000, 120000 );
		;
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
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
			mCamera = null;
		}
	}

	public void restartCamera() {
		delegate.setCameraSurface(cameraSurface);
		setupCamera(cameraSurface);
	}

	public void startCamera(int cameraTexture) {
			cameraSurface = new SurfaceTexture(cameraTexture);
			cameraSurface.setOnFrameAvailableListener(delegate);
			delegate.setCameraSurface(cameraSurface);
			setupCamera(cameraSurface);
		
	}
}

package com.visor.cartoonhacker;

import java.io.IOException;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;

public class CameraAdapter{
	
	private Camera mCamera = null;

	public void destroyCamera() {
		if(mCamera != null){
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	public void setupCamera(SurfaceTexture surface) {
		destroyCamera();
		mCamera = Camera.open();
		Camera.Parameters parameters = mCamera.getParameters();
		parameters.setPreviewFpsRange( 30000, 120000 );
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

}

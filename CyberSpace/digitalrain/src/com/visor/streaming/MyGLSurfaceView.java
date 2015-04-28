package com.visor.streaming;

import com.visor.renderingpipeline.MyRenderer;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
	private MyRenderer renderer;

	public MyGLSurfaceView(Context context, CameraAdapter cameraAdapter, int[] images)
	{
		super(context);

		setEGLContextClientVersion(2);
		//this.setEGLConfigChooser(8,8,8,8,16,0);

		renderer = new MyRenderer(images, cameraAdapter, context);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		getHolder().setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);

	}
	public MyRenderer getRenderer()
	{
		return renderer;
	}

	public void setCameraSurface(SurfaceTexture surface) {
		renderer.setCameraSurface(surface);
	}
	public void changeRecordingState(boolean status) {
		renderer.changeRecordingState(status);
	}
	public boolean isRecording() {
		return renderer.isRecording();
	}
	public boolean isInHeadsetMode() {
		return renderer.isInHeadsetMode();
	}
	public void setHeadsetMode(boolean status) {
		renderer.setHeadsetMode(status);
	}
}

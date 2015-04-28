package com.visor.streaming;

import com.visor.evmhacker.CameraAdapter;
import com.visor.renderingpipeline.MyRenderer;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
	private MyRenderer renderer;
	
	public MyGLSurfaceView(Context context, CameraAdapter cameraAdapter)
	{
		super(context);

		setEGLContextClientVersion(2);
		//this.setEGLConfigChooser(8,8,8,8,16,0);

		renderer = new MyRenderer(cameraAdapter);
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
}

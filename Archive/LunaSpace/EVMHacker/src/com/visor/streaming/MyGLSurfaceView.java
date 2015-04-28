package com.visor.streaming;

import com.visor.evmhacker.MainActivity;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
	MyRenderer renderer;
	public MyGLSurfaceView(Context context)
	{
		super(context);

		setEGLContextClientVersion(2);
		//this.setEGLConfigChooser(8,8,8,8,16,0);

		renderer = new MyRenderer((MainActivity)context);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		getHolder().setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);

	}
	public MyRenderer getRenderer()
	{
		return renderer;
	}

	@Override
	public boolean performClick(){
		System.out.println("Hardware "+this.isHardwareAccelerated());

		return super.performClick();
	}
}

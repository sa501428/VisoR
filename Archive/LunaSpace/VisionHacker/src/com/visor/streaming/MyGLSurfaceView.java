package com.visor.streaming;

import com.visor.visionhacker.MainActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView
{
	MyNewRenderer renderer;
	public MyGLSurfaceView(Context context)
	{
		super(context);

		setEGLContextClientVersion(2);

		renderer = new MyNewRenderer((MainActivity)context);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		

	}
	public MyNewRenderer getRenderer()
	{
		return renderer;
	}

	@Override
	public boolean performClick(){
		System.out.println("Hardware "+this.isHardwareAccelerated());
		
		return super.performClick();
	}
}

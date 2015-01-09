package com.visor.streaming;

import android.content.Context;
import android.opengl.GLSurfaceView;
import com.visor.visionhacker.MainActivity;

public class MyGLSurfaceView extends GLSurfaceView {
    final MyRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        renderer = new MyRenderer((MainActivity) context);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }

    public MyRenderer getRenderer() {
        return renderer;
    }

    @Override
    public boolean performClick() {
        System.out.println("Hardware " + this.isHardwareAccelerated());

        return super.performClick();
    }
}

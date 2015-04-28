package com.visor.theater3d;


import android.content.Context;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;

class VideoSurfaceView extends GLSurfaceView {

    EyeRenderer mRenderer;
    private MediaPlayer mMediaPlayer = null;

    public VideoSurfaceView(Context context, MediaPlayer mp) {
        super(context);

        setEGLContextClientVersion(2);
        mMediaPlayer = mp;
        mRenderer = new EyeRenderer(context);
        setRenderer(mRenderer);
    }

    @Override
    public void onResume() {
        queueEvent(new Runnable(){
                public void run() {
                    mRenderer.setMediaPlayer(mMediaPlayer);
                }});

        super.onResume();
    }

}  // End of class VideoSurfaceView.

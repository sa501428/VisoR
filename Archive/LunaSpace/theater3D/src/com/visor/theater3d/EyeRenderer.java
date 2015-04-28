package com.visor.theater3d;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;

public class EyeRenderer 
    implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {
    private static String TAG = "VideoRender";
    private SingleEyeScreen leftEye, rightEye;

    
    private final float[] leftTriangleVerticesData = {
        // X, Y, Z, U, V
        -1.0f, -1.0f, 0, 0.f, 0.f,
        0.0f, -1.0f, 0, 1.f, 0.f,
        -1.0f,  1.0f, 0, 0.f, 1.f,
        0.0f,  1.0f, 0, 1.f, 1.f,
    };
    private final float[] rightTriangleVerticesData = {
            // X, Y, Z, U, V
            0.0f, -1.0f, 0, 0.f, 0.f,
            1.0f, -1.0f, 0, 1.f, 0.f,
            0.0f,  1.0f, 0, 0.f, 1.f,
            1.0f,  1.0f, 0, 1.f, 1.f,
        };
    

    private float[] mSTMatrix = new float[16];

    

    private final String leftFragmentShader =
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            "varying vec2 vTextureCoord;\n" +
            "uniform samplerExternalOES sTexture;\n" +
            "void main() {\n" +
            "  vec4 init_color = texture2D(sTexture, vTextureCoord);\n" +
            "  gl_FragColor = vec4(init_color.r, 0.0, 0.0 ,init_color.a);\n" + 
            "}\n";
    
    private final String rightFragmentShader =
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            "varying vec2 vTextureCoord;\n" +
            "uniform samplerExternalOES sTexture;\n" +
            "void main() {\n" +
            "  vec4 init_color = texture2D(sTexture, vTextureCoord);\n" +
            "  gl_FragColor = vec4(0.0,init_color.gba);\n" +
            "}\n";
    
    

    

    private int mTextureID;

    private SurfaceTexture mSurface;
    private boolean updateSurface = false;

    private static int GL_TEXTURE_EXTERNAL_OES = 0x8D65;

    private MediaPlayer mMediaPlayer;

    public EyeRenderer(Context context) {
        
    }

    public void setMediaPlayer(MediaPlayer player) {
        mMediaPlayer = player;
    }

    public void onDrawFrame(GL10 glUnused) {
        synchronized(this) {
            if (updateSurface) {
                mSurface.updateTexImage();
                mSurface.getTransformMatrix(mSTMatrix);
                updateSurface = false;
            }
        }

        GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        
        leftEye.draw(glUnused, mTextureID, mSTMatrix);
        rightEye.draw(glUnused, mTextureID, mSTMatrix);

        
        GLES20.glFinish();

    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
    	leftEye = new SingleEyeScreen(leftTriangleVerticesData, leftFragmentShader);
    	rightEye = new SingleEyeScreen(rightTriangleVerticesData, rightFragmentShader);
    	
    	Matrix.setIdentityM(mSTMatrix, 0);
    	
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);

        mTextureID = textures[0];
        GLES20.glBindTexture(GL_TEXTURE_EXTERNAL_OES, mTextureID);
        checkGlError("glBindTexture mTextureID");

        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MIN_FILTER,
                               GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GL_TEXTURE_EXTERNAL_OES, GLES20.GL_TEXTURE_MAG_FILTER,
                               GLES20.GL_LINEAR);

        /*
         * Create the SurfaceTexture that will feed this textureID,
         * and pass it to the MediaPlayer
         */
        mSurface = new SurfaceTexture(mTextureID);
        mSurface.setOnFrameAvailableListener(this);

        Surface surface = new Surface(mSurface);
        mMediaPlayer.setSurface(surface);
        surface.release();

        try {
            mMediaPlayer.prepare();
        } catch (IOException t) {
            Log.e(TAG, "media player prepare failed");
        }

        synchronized(this) {
            updateSurface = false;
        }

        mMediaPlayer.start();
    }

    synchronized public void onFrameAvailable(SurfaceTexture surface) {
        updateSurface = true;
    }

    public static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader " + shaderType + ":");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

}  // End of class VideoRender.

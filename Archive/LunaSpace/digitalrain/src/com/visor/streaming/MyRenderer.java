package com.visor.streaming;

import java.util.Calendar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.visor.digitalrain.MainActivity;
import com.visor.photorender.GLESBackground;
import com.visor.photorender.Square;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class MyRenderer implements GLSurfaceView.Renderer
{

	CameraStreaming cameraStreamLeft, cameraStreamRight;
	int texture;
	private SurfaceTexture surface;
	private MainActivity delegate;
	private Square pictureSquare;
	private GLESBackground background;

	// left side view
	static float triangleVertices0[] = { // in counterclockwise order:
		-1.0f,  1.0f,
		0.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f, -1.0f
	};

	// right side view
	static float triangleVertices1[] = { // in counterclockwise order:
		0.0f,  1.0f,
		1.0f,  1.0f,
		1.0f,  -1.0f,
		0.0f,  1.0f,
		1.0f,  -1.0f,
		0.0f, -1.0f
	};

	// mapping of texture to subview
	static float textureVertices[] = { // in counterclockwise order:
		0.0f,  0.0f,
		1.0f,  0.0f,
		1.0f,  1.0f,
		0.0f,  0.0f,
		1.0f,  1.0f,
		0.0f,  1.0f
	};

	static float l = 0.25f, r = 0.75f, d = 0.0f;	
	static float ll = l-d, lr = r-d, rl = l+d, rr = r+d;
	static float t = 0.0f, b = 1.0f, d2 = 0.0f;	
	static float lt = t+d2, lb = b-d2, rt = t+d2, rb = b-d2;

	static float textureVerticesLeft[] = { // in counterclockwise order:
		ll,  lt,
		lr,  lt,
		lr,  lb,
		ll,  lt,
		lr,  lb,
		ll,  lb
	};

	static float textureVerticesRight[] = { // in counterclockwise order:
		rl,  rt,
		rr,  rt,
		rr,  rb,
		rl,  rt,
		rr,  rb,
		rl,  rb
	};

	public MyRenderer(MainActivity _delegate)
	{
		delegate = _delegate;
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		texture = createTexture();
		cameraStreamLeft = new CameraStreaming(texture, triangleVertices0, textureVerticesLeft);
		cameraStreamRight = new CameraStreaming(texture, triangleVertices1, textureVerticesRight);
		GLES20.glClearColor(0f, 0f, 0f, 1.0f);
		
		
		background = new GLESBackground(delegate);
	
		delegate.startCamera(texture);
	}

	final Calendar c=Calendar.getInstance();


	public void onDrawFrame(GL10 gl)
	{
		float[] mtx = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		//surface.updateTexImage();
		surface.getTransformMatrix(mtx); 

	//	GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		
		background.Draw(mtx);
		
		//cameraStreamLeft.draw(mtx);
		//cameraStreamRight.draw(mtx);

	}

	public void onSurfaceChanged(GL10 unused, int width, int height)
	{
		GLES20.glViewport(0, 0, width, height);
	}

	static public int loadShader(int type, String shaderCode)
	{
		int shader = GLES20.glCreateShader(type);

		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	static public int createTexture()
	{
		int[] texture = new int[1];

		GLES20.glGenTextures(1,texture, 0);
		GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture[0]);
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);        
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

		return texture[0];
	}

	public void setSurface(SurfaceTexture _surface)
	{
		surface = _surface;
	}

	public void updateVariables(){
		cameraStreamLeft.updateVariables();
		cameraStreamRight.updateVariables();
	}

}
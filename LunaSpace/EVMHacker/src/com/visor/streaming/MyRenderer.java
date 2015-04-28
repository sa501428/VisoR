package com.visor.streaming;


import com.visor.evmhacker.MainActivity;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

public class MyRenderer implements GLSurfaceView.Renderer
{

	CameraStreaming cameraStreamLeft, cameraStreamRight;
	int texture;
	private SurfaceTexture surface;
	private MainActivity delegate;
	private Integer leftFilterIndex = 0, rightFilterIndex = 0;
	private int[] updatedVars = {0,0,0,0,0};
	//private int screenWidth, screenHeight;

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

	public void onSurfaceCreated(GL10 unused, EGLConfig config)
	{
		texture = createTexture();
		cameraStreamLeft = new CameraStreaming(texture, triangleVertices0, textureVerticesLeft);
		cameraStreamRight = new CameraStreaming(texture, triangleVertices1, textureVerticesRight);
		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

		cameraStreamLeft.updateFilterSelection(leftFilterIndex);
		cameraStreamRight.updateFilterSelection(rightFilterIndex);

		delegate.startCamera(texture);
	}
	
	
	public void onDrawFrame(GL10 unused)
	{
		conductFrameDrawing();
	}

	private void conductFrameDrawing() {
		float[] mtx = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		surface.updateTexImage();
		//surface.getTransformMatrix(mtx); 


		Matrix.rotateM(mtx,0,updatedVars[1],1f,0f,0f);
		Matrix.rotateM(mtx,0,updatedVars[2],0f,1f,0f);
		Matrix.rotateM(mtx,0,updatedVars[3],0f,0f,1f);
		//Matrix.translateM(mtx,0,.5f,0f,0f);
		//Matrix.scaleM(mtx, 0, 0.5f, 0.5f, 0.5f);

		cameraStreamLeft.draw(mtx);
		cameraStreamRight.draw(mtx);
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
		int[] newTexture = new int[1];

		GLES20.glGenTextures(1,newTexture, 0);
		GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, newTexture[0]);
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);        
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
				GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

		return newTexture[0];
	}

	public void setSurface(SurfaceTexture _surface)
	{
		surface = _surface;
	}

	public void setFilters(int[] filterIndices) {
		this.leftFilterIndex = filterIndices[0];
		this.rightFilterIndex = filterIndices[1];
	}

	public void updateFilters(int[] filterIndices) {
		this.leftFilterIndex = filterIndices[0];
		this.rightFilterIndex = filterIndices[1];

		cameraStreamLeft.updateFilterSelection(filterIndices[0]);
		cameraStreamRight.updateFilterSelection(filterIndices[1]);
	}

	public void updateFilterVariables(int[] settingsOptions) {
		this.updatedVars  = settingsOptions;
	}

}
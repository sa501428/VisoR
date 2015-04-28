package com.visor.streaming;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Calendar;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.visor.visionhacker.DataRelay;
import com.visor.visionhacker.MainActivity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Environment;

public class MyNewRenderer implements GLSurfaceView.Renderer
{

	CameraStreaming cameraStreamLeft, cameraStreamRight;
	int texture;
	private SurfaceTexture surface;
	MainActivity delegate;
	private Integer leftFilterIndex = 0, rightFilterIndex = 0;
	private int[] updatedVars = {0,0,0,0,0};
	private DataRelay dataRelay;
	private int screenWidth, screenHeight;
	private int screenshotOptionEnable = 0;

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

	public MyNewRenderer(MainActivity _delegate)
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
	
	final Calendar c=Calendar.getInstance();
	

	public void onDrawFrame(GL10 unused)
	{
		float[] mtx = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		surface.updateTexImage();
		//surface.getTransformMatrix(mtx); 


		Matrix.rotateM(mtx,0,updatedVars[1],1f,0f,0f);
		Matrix.rotateM(mtx,0,updatedVars[2],0f,1f,0f);
		Matrix.rotateM(mtx,0,updatedVars[3],0f,0f,1f);
		//Matrix.translateM(mtx,0,.5f,0f,0f);
		//Matrix.scaleM(mtx, 0, 0.5f, 0.5f, 0.5f);

		cameraStreamLeft.draw(mtx, dataRelay);
		cameraStreamRight.draw(mtx, dataRelay);

		try {

			if ( screenshotOptionEnable > 0 ) 
			{
				screenshotOptionEnable-- ;
				int w = screenWidth ;
				int h = screenHeight  ;

				int b[]=new int[(int) (w*h)];
				int bt[]=new int[(int) (w*h)];
				IntBuffer buffer=IntBuffer.wrap(b);
				buffer.position(0);
				GLES20.glReadPixels(0, 0, w, h,GLES20.GL_RGBA,GLES20.GL_UNSIGNED_BYTE, buffer);
				for(int i=0; i<h; i++)
				{
					//remember, that OpenGL bitmap is incompatible with Android bitmap
					//and so, some correction need.        
					for(int j=0; j<w; j++)
					{
						int pix=b[i*w+j];
						int pb=(pix>>16)&0xff;
						int pr=(pix<<16)&0x00ff0000;
						int pix1=(pix&0xff00ff00) | pr | pb;
						bt[(h-i-1)*w+j]=pix1;
					}
				}           
				Bitmap inBitmap = null ;
				if (inBitmap == null || !inBitmap.isMutable()
						|| inBitmap.getWidth() != w || inBitmap.getHeight() != h) {
					inBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				}
				//Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
				inBitmap.copyPixelsFromBuffer(buffer);
				//return inBitmap ;
				// return Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
				inBitmap = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);

				ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
				inBitmap.compress(CompressFormat.JPEG, 90, bos); 
				byte[] bitmapdata = bos.toByteArray();
				ByteArrayInputStream fis = new ByteArrayInputStream(bitmapdata);

				long mytimestamp=c.getTimeInMillis();
				String timeStamp=String.valueOf(mytimestamp);
				String myfile=timeStamp+".jpeg";

				File dir_image=new File(Environment.getExternalStorageDirectory()+File.separator+
						"VisoR"+File.separator+"images");
				dir_image.mkdirs();

				try {
					File tmpFile = new File(dir_image,myfile); 
					FileOutputStream fos = new FileOutputStream(tmpFile);

					byte[] buf = new byte[1024];
					int len;
					while ((len = fis.read(buf)) > 0) {
						fos.write(buf, 0, len);
					}
					fis.close();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}


	}

	public void onSurfaceChanged(GL10 unused, int width, int height)
	{
		this.screenWidth = width;
		this.screenHeight = height;
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

	public void setSensorDataRelayer(DataRelay dataRelay) {
		this.dataRelay = dataRelay;
	}

	public void takeScreenShots(int numShots){
		this.screenshotOptionEnable = numShots;
	}

}
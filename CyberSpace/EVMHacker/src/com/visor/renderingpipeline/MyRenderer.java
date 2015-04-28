package com.visor.renderingpipeline;

import com.visor.evmhacker.CameraAdapter;
import com.visor.model.DimensionVault;
import com.visor.streaming.CameraStreaming;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

public class MyRenderer implements GLSurfaceView.Renderer{

	private CameraStreaming cameraStreamLeft, cameraStreamRight;
	private SurfaceTexture surface;
	private CameraAdapter delegate;
	private IntermediateProcessor intermediateProcessor;


	public MyRenderer(CameraAdapter delegate){
		this.delegate = delegate;
	}

	public void onSurfaceCreated(GL10 unused, EGLConfig config){
		int cameraTexture = GL_Toolbox.createOESTexture();
		
		intermediateProcessor = new IntermediateProcessor(cameraTexture, DimensionVault.drawOrder, DimensionVault.wholeTriangleVertices, DimensionVault.fullTextureVertices);
		
		cameraStreamLeft = new CameraStreaming(DimensionVault.drawOrder, DimensionVault.triangleVerticesLeft, DimensionVault.textureVerticesLeft);
		cameraStreamRight = new CameraStreaming(DimensionVault.drawOrder, DimensionVault.triangleVerticesRight, DimensionVault.textureVerticesRight);

		GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

		delegate.startCamera(cameraTexture);
	}

	public void onDrawFrame(GL10 unused){
		conductFrameDrawing();
	}

	private void conductFrameDrawing(){
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		
		surface.updateTexImage();

		float[] rotation = {180f,1f,0f,0f};
		float[] mtx = GL_Toolbox.getMatrix(rotation);

		int finalTexture = intermediateProcessor.process(mtx, DimensionVault.GREEN);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
		
		cameraStreamLeft.draw(finalTexture);
		cameraStreamRight.draw(finalTexture);
	}

	public void onSurfaceChanged(GL10 unused, int width, int height){
		GLES20.glViewport(0, 0, width, height);
		intermediateProcessor.setDimensions(width, height);
	}

	public void setCameraSurface(SurfaceTexture surface){
		this.surface = surface;
	}
}
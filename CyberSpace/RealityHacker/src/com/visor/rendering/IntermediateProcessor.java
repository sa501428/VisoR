package com.visor.rendering;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.visor.model.FilterVault;
import com.visor.renderingpipeline.DigitalRainAdapter;
import com.visor.renderingpipeline.GL_Toolbox;

public class IntermediateProcessor {

	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgramCameraTransfer, mProgramDigitalRain;

	private int cameraTexture;
	private DigitalRainAdapter digitalRainAdapter;
	private int transferredCameraTexture, cameraFrameBuffer;
	private int finalTexture, finalFrameBuffer;

	private int numElements;
	private float widthInverse, heightInverse;

	public IntermediateProcessor(int cameraTexture, short[] drawOrder, float[] triangleVertices, float[] textureVertices) {
		this.cameraTexture = cameraTexture;
		digitalRainAdapter = new DigitalRainAdapter();

		numElements = drawOrder.length;
		drawListBuffer = GL_Toolbox.setupShortBuffer(drawOrder);
		vertexBuffer = GL_Toolbox.setupFloatBuffer(triangleVertices);
		textureVerticesBuffer = GL_Toolbox.setupFloatBuffer(textureVertices);

		mProgramCameraTransfer = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.OESto2D);
		mProgramDigitalRain = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.digitalRain);
	}

	public int setDimensions(int width, int height){
		widthInverse = 1f/width;
		heightInverse = 1f/height;

		transferredCameraTexture = GL_Toolbox.createRegularTexture();
		cameraFrameBuffer = GL_Toolbox.createFrameBuffer(width, height);

		finalTexture = GL_Toolbox.createRegularTexture();
		finalFrameBuffer = GL_Toolbox.createFrameBuffer(width, height);
		return finalTexture;
	}
	
	public void process(float[] mtx, float[] color) {
		
		renderCameraToTexture(mtx);
		renderDigitalRain(mtx, digitalRainAdapter.getRandomImage());
	}

	private boolean renderCameraToTexture(float[] mtx) {

		GL_Toolbox.bindFrameBufferWithTexture(cameraFrameBuffer, transferredCameraTexture);

		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(mProgramCameraTransfer);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, cameraTexture);

		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(mProgramCameraTransfer, "uMVPMatrix"), 1, false, mtx, 0);

		int mPositionHandle = GL_Toolbox.handleProgramAttribute(mProgramCameraTransfer, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(mProgramCameraTransfer, "inputTextureCoordinate", textureVerticesBuffer);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

		return true;
	}



	private void renderDigitalRain(float[] mtx, int randomTexture) {
		GL_Toolbox.bindFrameBufferWithTexture(finalFrameBuffer, finalTexture);

		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(mProgramDigitalRain);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE_2D, transferredCameraTexture);	
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE1, GLES20.GL_TEXTURE_2D, randomTexture);	
		
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(mProgramDigitalRain, "uMVPMatrix"), 1, false, mtx, 0);

		int mPositionHandle = GL_Toolbox.handleProgramAttribute(mProgramDigitalRain, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(mProgramDigitalRain, "inputTextureCoordinate", textureVerticesBuffer);

		GL_Toolbox.handleProgramUniform1i(mProgramDigitalRain, "texture1", 0);
		GL_Toolbox.handleProgramUniform1i(mProgramDigitalRain, "texture2", 1);

		GL_Toolbox.handleProgramUniform1f(mProgramDigitalRain, "imageWidthFactor", widthInverse);
		GL_Toolbox.handleProgramUniform1f(mProgramDigitalRain, "imageHeightFactor", heightInverse);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

	}

	public DigitalRainAdapter getDigitalRainAdapter() {
		return digitalRainAdapter;
	}
}

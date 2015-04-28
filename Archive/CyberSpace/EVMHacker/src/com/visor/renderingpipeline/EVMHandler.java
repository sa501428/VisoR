package com.visor.renderingpipeline;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

public class EVMHandler {

	private final int numTextures = 4,
			numEVM = 3;
	private int[] textures = new int[numTextures];
	private int[] frameBuffers = new int[numTextures];
	private int[] activeOrderOldestToNewest = new int[numTextures];
	private int newestIndex = numTextures - 1;
	private int updateCounter = 0, samplingRate = 2;
	
	private int finalTexture, finalFrameBuffer;
	private float widthFactor, heightFactor;
	
	private int numElements;
	private ShortBuffer drawListBuffer;
	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private int mProgram0, mProgramEVM, cameraTexture;
	
	
	public void setGLESBasics(int numElements, ShortBuffer drawListBuffer,
			FloatBuffer vertexBuffer, FloatBuffer textureVerticesBuffer,
			int mProgram0, int mProgramEVM, int cameraTexture) {
		this.numElements = numElements;
		this.drawListBuffer = drawListBuffer;
		this.vertexBuffer = vertexBuffer;
		this.textureVerticesBuffer = textureVerticesBuffer;
		this.mProgram0 = mProgram0;
		this.mProgramEVM = mProgramEVM;	
		this.cameraTexture = cameraTexture;
	}

	public void setBasics(int width, int height, int finalTexture, int finalFrameBuffer, float widthFactor, float heightFactor) {
		for(int i = 0; i < numTextures; i++){
			textures[i] = GL_Toolbox.createRegularTexture();
			activeOrderOldestToNewest[i] = i;
			frameBuffers[i] = GL_Toolbox.createFrameBuffer(width, height);
		}
		
		this.finalTexture = finalTexture;
		this.finalFrameBuffer = finalFrameBuffer;
		this.widthFactor = widthFactor;
		this.heightFactor = heightFactor;
	}

	public void processEVM(float[] mtx){
		if(updateCounter++ % samplingRate == 0)
			updateActiveTextureIndices();
		renderCameraToEVMTexture(mProgram0, mtx);

		renderTextureToEVMTexture(mProgramEVM, mtx, numEVM);
		//renderFocusedTextureToTexture(mProgramEVM, mtx, numEVM);

		//System.out.println(" "+ activeOrderOldestToNewest[newestIndex]);
	}

	private void updateActiveTextureIndices() {

		for(int i = 0; i < numTextures; i++){
			activeOrderOldestToNewest[i]++;
			activeOrderOldestToNewest[i] %= numTextures;

		}
	}

	private boolean renderCameraToEVMTexture(int _program, float[] mtx) {

		GL_Toolbox.bindFrameBufferWithTexture(frameBuffers[activeOrderOldestToNewest[newestIndex]],
				textures[activeOrderOldestToNewest[newestIndex]]);

		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(_program);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES11Ext.GL_TEXTURE_EXTERNAL_OES, cameraTexture);

		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(_program, "uMVPMatrix"), 1, false, mtx, 0);

		int mPositionHandle = GL_Toolbox.handleProgramAttribute(_program, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(_program, "inputTextureCoordinate", textureVerticesBuffer);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

		return true;
	}
	
	public boolean renderTextureToEVMTexture(int _program, float[] mtx, int n) {

		GL_Toolbox.bindFrameBufferWithTexture(finalFrameBuffer, finalTexture);
		
		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(_program);
		for(int i = 0; i < n; i++){
			GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0+i, GLES20.GL_TEXTURE_2D, textures[activeOrderOldestToNewest[newestIndex-i]]);	
		}
		
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(_program, "uMVPMatrix"), 1, false, mtx, 0);
		
		int mPositionHandle = GL_Toolbox.handleProgramAttribute(_program, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(_program, "inputTextureCoordinate", textureVerticesBuffer);

		for(int i = 1; i < n+1; i++){
			GL_Toolbox.handleProgramUniform1i(_program, "texture"+i, i-1);
		}
		
		GL_Toolbox.handleProgramUniform1f(_program, "imageWidthFactor", widthFactor);
		GL_Toolbox.handleProgramUniform1f(_program, "imageHeightFactor", heightFactor);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

		return true;
	}
	
	public boolean renderFocusedTextureToTexture(int _program, float[] mtx, int n) {

		GL_Toolbox.bindFrameBufferWithTexture(finalFrameBuffer, finalTexture);
		
		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(_program);
		for(int i = 0; i < n; i++){
			GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0+i, GLES20.GL_TEXTURE_2D, textures[activeOrderOldestToNewest[newestIndex-i]]);	
		}
		
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(_program, "uMVPMatrix"), 1, false, mtx, 0);
		
		int mPositionHandle = GL_Toolbox.handleProgramAttribute(_program, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(_program, "inputTextureCoordinate", textureVerticesBuffer);

		for(int i = 1; i < n+1; i++){
			GL_Toolbox.handleProgramUniform1i(_program, "texture"+i, i-1);
		}
		
		GL_Toolbox.handleProgramUniform1f(_program, "imageWidthFactor", widthFactor);
		GL_Toolbox.handleProgramUniform1f(_program, "imageHeightFactor", heightFactor);
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);

		return true;
	}

	

}

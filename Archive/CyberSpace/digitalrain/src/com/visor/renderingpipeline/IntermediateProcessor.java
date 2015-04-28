package com.visor.renderingpipeline;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.visor.model.FilterVault;

public class IntermediateProcessor {

	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgramCameraTransfer, mProgramDigitalRain, mProgramEVM, mProgramMotion;

	private int cameraTexture;
	private DigitalRainAdapter digitalRainAdapter;
	private int transferredCameraTexture, cameraFrameBuffer;
	private int finalTexture, finalFrameBuffer;

	private int numElements;
	private float widthInverse, heightInverse;

	private static int whichProgramGoesNow = 0;
	private static int numSimplePrograms = NewFilterRepo.shaders.length;
	private static int numComplexPrograms = 3;
	
	private static int maxNumPrograms = numSimplePrograms + numComplexPrograms;
	private static int[] programIDs = new int[numSimplePrograms];

	private EVMHandler evmHandler = new EVMHandler();
	private EVMHandler motionHandler = new EVMHandler();

	public IntermediateProcessor(int cameraTexture, short[] drawOrder, float[] triangleVertices, float[] textureVertices) {
		this.cameraTexture = cameraTexture;
		digitalRainAdapter = new DigitalRainAdapter();

		numElements = drawOrder.length;
		drawListBuffer = GL_Toolbox.setupShortBuffer(drawOrder);
		vertexBuffer = GL_Toolbox.setupFloatBuffer(triangleVertices);
		textureVerticesBuffer = GL_Toolbox.setupFloatBuffer(textureVertices);

		mProgramCameraTransfer = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.OESto2D);
		mProgramDigitalRain = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.digitalRain);
		mProgramEVM = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.approxFocusedHighEVM);
		mProgramMotion = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.motionEVM);
		

		for(int i = 0; i < numSimplePrograms; i++){
			programIDs[i] = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, NewFilterRepo.shaders[i]);
		}
		
		evmHandler.setGLESBasics(numElements, drawListBuffer, vertexBuffer, textureVerticesBuffer, mProgramCameraTransfer, mProgramEVM, cameraTexture);
		motionHandler.setGLESBasics(numElements, drawListBuffer, vertexBuffer, textureVerticesBuffer, mProgramCameraTransfer, mProgramMotion, cameraTexture);
		
	}

	public int setDimensions(int width, int height){
		widthInverse = 1f/width;
		heightInverse = 1f/height;

		transferredCameraTexture = GL_Toolbox.createRegularTexture();
		cameraFrameBuffer = GL_Toolbox.createFrameBuffer(width, height);

		finalTexture = GL_Toolbox.createRegularTexture();
		finalFrameBuffer = GL_Toolbox.createFrameBuffer(width, height);
		
		evmHandler.setBasics(width, height, finalTexture, finalFrameBuffer, widthInverse, heightInverse);
		motionHandler.setBasics(width, height, finalTexture, finalFrameBuffer, widthInverse, heightInverse);
		
		return finalTexture;
	}

	public void process(float[] mtx, float[] color) {

		
		if(whichProgramGoesNow < numSimplePrograms){
			renderCameraToTexture(mtx);
			renderGenericProgram(mtx, programIDs[whichProgramGoesNow]);
		}
		else if(whichProgramGoesNow == maxNumPrograms-1){
			renderCameraToTexture(mtx);
			renderDigitalRain(mtx, digitalRainAdapter.getRandomImage());
		}
		else if(whichProgramGoesNow == maxNumPrograms-2){
			motionHandler.processEVM(mtx);
		}
		else{
			evmHandler.processEVM(mtx);
		}
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

	private void renderGenericProgram(float[] mtx, int program) {
		GL_Toolbox.bindFrameBufferWithTexture(finalFrameBuffer, finalTexture);

		GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(program);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE_2D, transferredCameraTexture);	

		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(program, "uMVPMatrix"), 1, false, mtx, 0);

		int mPositionHandle = GL_Toolbox.handleProgramAttribute(program, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(program, "inputTextureCoordinate", textureVerticesBuffer);

		GL_Toolbox.handleProgramUniform1i(program, "texture1", 0);

		GL_Toolbox.handleProgramUniform1f(program, "imageWidthFactor", widthInverse);
		GL_Toolbox.handleProgramUniform1f(program, "imageHeightFactor", heightInverse);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
	}

	public DigitalRainAdapter getDigitalRainAdapter() {
		return digitalRainAdapter;
	}

	public static void incrementProcess(){
		whichProgramGoesNow += 1;
		whichProgramGoesNow %= maxNumPrograms;
	}

	public static void decrementProcess(){
		whichProgramGoesNow += (maxNumPrograms - 1);
		whichProgramGoesNow %= maxNumPrograms;
	}
}

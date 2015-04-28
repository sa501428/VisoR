package com.visor.renderingpipeline;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.visor.model.FilterVault;

public class IntermediateProcessor {

	private int cameraTexture;
	
	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgram0, mProgramEVM;
	
	private EVMHandler evmHandler = new EVMHandler();
	
	private float widthFactor, heightFactor;	
	private int finalTexture, finalFrameBuffer;

	private int numElements;

	public IntermediateProcessor(int cameraTexture, short[] drawOrder,
			float[] triangleVertices, float[] textureVertices) {
		this.cameraTexture = cameraTexture;
		
		numElements = drawOrder.length;
		drawListBuffer = GL_Toolbox.setupShortBuffer(drawOrder);
		vertexBuffer = GL_Toolbox.setupFloatBuffer(triangleVertices);
		textureVerticesBuffer = GL_Toolbox.setupFloatBuffer(textureVertices);

		mProgram0 = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.OESto2D);
		mProgramEVM = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.approxFocusedHighEVM);
		
		evmHandler.setGLESBasics(numElements, drawListBuffer, vertexBuffer, textureVerticesBuffer, mProgram0, mProgramEVM, cameraTexture);
		
	}

	public void setDimensions(int width, int height){
		finalTexture = GL_Toolbox.createRegularTexture();
		finalFrameBuffer = GL_Toolbox.createFrameBuffer(width, height);
		widthFactor = 1f/width;
		heightFactor = 1f/height;
		evmHandler.setBasics(width, height, finalTexture, finalFrameBuffer, widthFactor, heightFactor);
	}

	public int process(float[] mtx, float[] color) {
		evmHandler.processEVM(mtx);
		return finalTexture;
	}

}

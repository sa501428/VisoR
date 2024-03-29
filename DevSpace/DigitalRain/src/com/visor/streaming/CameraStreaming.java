package com.visor.streaming;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.visor.model.FilterVault;
import com.visor.renderingpipeline.GL_Toolbox;
import com.visor.screenmesh.ScreenMesh;

public class CameraStreaming {

	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgram0, mProgram2;

	// order to draw vertices

	private int numElements;



	public CameraStreaming(ScreenMesh screenMesh) 
	{
		
		short[] drawOrder = screenMesh.getDrawOrder();
		float[] triangleVertices = screenMesh.getTriangleVertices();
		float[] textureVertices = screenMesh.getTextureVertices();
		
		numElements = drawOrder.length;
		drawListBuffer = GL_Toolbox.setupShortBuffer(drawOrder);
		vertexBuffer = GL_Toolbox.setupFloatBuffer(triangleVertices);
		textureVerticesBuffer = GL_Toolbox.setupFloatBuffer(textureVertices);

		mProgram0 = GL_Toolbox.createProgram(FilterVault.vertexShaderCode, FilterVault.plain);
		mProgram2 = GL_Toolbox.createProgram(FilterVault.vertexMatrixShaderCode, FilterVault.plain);
	}


	public void draw(int texture, boolean clearSBS, float[] mtx){
		
		processSBSTexture(texture, clearSBS, mtx);

		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

		GLES20.glUseProgram(mProgram0);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE_2D, texture);

		GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram0, "texture1"), 0);

		int mPositionHandle = GL_Toolbox.handleProgramAttribute(mProgram0, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(mProgram0, "inputTextureCoordinate", textureVerticesBuffer);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
		
		
	}

	
	private void processSBSTexture(int texture, boolean clearSBS, float[] mtx) {
		GL_Toolbox.bindFrameBufferWithTexture(finalFrameBuffer, finalTextureSBS);

		if(clearSBS)
			GL_Toolbox.clearGLScreen(0f,0f,0f);
		GLES20.glUseProgram(mProgram2);
		GL_Toolbox.activateTexture(GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE_2D, texture);

		//GLES20.glUniform1i(GLES20.glGetUniformLocation(mProgram2, "texture1"), 0);
		GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(mProgram2, "uMVPMatrix"), 1, false, mtx, 0);

		
		int mPositionHandle = GL_Toolbox.handleProgramAttribute(mProgram2, "position", vertexBuffer);
		int mTextureCoordHandle = GL_Toolbox.handleProgramAttribute(mProgram2, "inputTextureCoordinate", textureVerticesBuffer);

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, numElements, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
	}


	private int finalTextureSBS, finalFrameBuffer;

	public void handleSBSTexture(int finalFrameBuffer, int finalTextureSBS, int width, int height) {
		this.finalTextureSBS = finalTextureSBS;
		this.finalFrameBuffer = finalFrameBuffer;
	}
	

}


package com.visor.rendering;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

import com.visor.model.FilterVault;
import com.visor.renderingpipeline.GL_Toolbox;

public class HeadsetView {

	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mProgram0;

	// order to draw vertices

	private int numElements;



	public HeadsetView(short[] drawOrder, float[] triangleVertices, float[] textureVertices) 
	{

		numElements = drawOrder.length;
		drawListBuffer = GL_Toolbox.setupShortBuffer(drawOrder);
		vertexBuffer = GL_Toolbox.setupFloatBuffer(triangleVertices);
		textureVerticesBuffer = GL_Toolbox.setupFloatBuffer(textureVertices);

		mProgram0 = GL_Toolbox.createProgram(FilterVault.vertexShaderCode, FilterVault.plain);
	}


	public void draw(int texture){

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


}


package com.visor.streaming;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import com.visor.filters.FilterVault;
import com.visor.filters.UniformPair;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

public class CameraStreaming {

	private final String vertexShaderCode =
			"attribute vec4 position;" +
					"uniform mat4 uMVPMatrix;   	\n" +		// Uniform variables are consistent across all elements
					"attribute vec2 inputTextureCoordinate;" +
					"varying vec2 textureCoordinate;" +
					"void main()" +
					"{"+
					"gl_Position = uMVPMatrix*position;"+
					"textureCoordinate = inputTextureCoordinate;" +
					"}";

	private FloatBuffer vertexBuffer, textureVerticesBuffer;
	private ShortBuffer drawListBuffer;
	private int mPositionHandle;
	@SuppressWarnings("unused")
	private int mColorHandle;
	private int mTextureCoordHandle;
	private ArrayList<UniformPair> uniformPairs;
	private int mProgram0;


	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 2;

	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	private final short drawOrder[] = { 0, 1, 2, 3, 4, 5}; // order to draw vertices

	private int texture;


	public CameraStreaming(int texture,	float[] triangleVertices, float[] textureVertices) 
	{

		//hiddenTexture = MyRenderer.createTexture();
		this.texture = texture;

		ByteBuffer bb = ByteBuffer.allocateDirect(triangleVertices.length * 4);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		vertexBuffer.put(triangleVertices);
		vertexBuffer.position(0);

		ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
		dlb.order(ByteOrder.nativeOrder());
		drawListBuffer = dlb.asShortBuffer();
		drawListBuffer.put(drawOrder);
		drawListBuffer.position(0);

		ByteBuffer bb2 = ByteBuffer.allocateDirect(textureVertices.length * 4);
		bb2.order(ByteOrder.nativeOrder());
		textureVerticesBuffer = bb2.asFloatBuffer();
		textureVerticesBuffer.put(textureVertices);
		textureVerticesBuffer.position(0);


		int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader;	

		fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FilterVault.intenseCartoon);
		mProgram0 = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		GLES20.glAttachShader(mProgram0, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(mProgram0, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(mProgram0); 
		//GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		
	

		uniformPairs = FilterVault.getAllUniformPairs();
	}

	public void updateFilterSelection(int index){
		updateVariables();
	}

	public void updateVariables(){
		uniformPairs = FilterVault.getAllUniformPairs();
	}


	public void draw(float mtx[]){
		
	/*	GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_LEQUAL, GLES20.GL_LEQUAL);
		GLES20.glBlendEquation( GLES20.GL_FUNC_ADD );
	*/	
		drawProgramStage(mProgram0, GLES20.GL_TEXTURE0, mtx);
	/*	
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_LEQUAL, GLES20.GL_LEQUAL);
		GLES20.glBlendEquation( GLES20.GL_FUNC_ADD );
		
		
		drawProgramStage(mProgram1, GLES20.GL_TEXTURE0, mtx);
	*/	
		
	}

	private void drawProgramStage(int mProgram, int glTexture, float[] mtx) {
		GLES20.glUseProgram(mProgram);
		GLES20.glActiveTexture(glTexture);
		GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);

		//GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);

		for(UniformPair pair : uniformPairs){
			int tempUniformPair = GLES20.glGetUniformLocation(mProgram, pair.getKey());
			GLES20.glUniform1f(tempUniformPair, pair.getValue());
		}

		int muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");	// grab the variable from the shader
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mtx, 0);


		mPositionHandle = GLES20.glGetAttribLocation(mProgram, "position");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,vertexStride, vertexBuffer);

		mTextureCoordHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
		GLES20.glEnableVertexAttribArray(mTextureCoordHandle);
		GLES20.glVertexAttribPointer(mTextureCoordHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false,vertexStride, textureVerticesBuffer);

		mColorHandle = GLES20.glGetAttribLocation(mProgram, "s_texture");

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,
				GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

		GLES20.glDisableVertexAttribArray(mPositionHandle);
		GLES20.glDisableVertexAttribArray(mTextureCoordHandle);
	}

}


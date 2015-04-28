package com.visor.streaming;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Random;
import com.visor.filters.FilterVault;
import com.visor.model.Filter;
import com.visor.model.UniformPair;
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
	private ArrayList<Filter> allFilters;
	private ArrayList<UniformPair> uniformPairs;
	private Filter activeFilter;

	/* for double render testing */
	private int mProgram1, mProgram2;


	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 2;

	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	private final short drawOrder[] = { 0, 1, 2, 3, 4, 5}; // order to draw vertices

	private int texture;

	private Random generator = new Random();

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
		allFilters = FilterVault.getAllFilters();

		for(Filter filter : allFilters){
			fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, filter.getFilterShader());
			int newMProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
			GLES20.glAttachShader(newMProgram, vertexShader);   // add the vertex shader to program
			GLES20.glAttachShader(newMProgram, fragmentShader); // add the fragment shader to program
			GLES20.glLinkProgram(newMProgram); 
			filter.setProgram(newMProgram);
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);


		}

		fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FilterVault.inverted);
		mProgram1 = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		GLES20.glAttachShader(mProgram1, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(mProgram1, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(mProgram1); 
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FilterVault.fs_GrayCCIR);
		mProgram2 = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		GLES20.glAttachShader(mProgram2, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(mProgram2, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(mProgram2); 
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		uniformPairs = FilterVault.getAllUniformPairs();
	}

	public void updateFilterSelection(int index){
		activeFilter = allFilters.get(index);
		updateVariables();
	}

	public void updateVariables(){
		uniformPairs = FilterVault.getAllUniformPairs();
	}


	private float[] ijkRand = {0f,0f,0f};
	private int counter = 0, counterMod = 10;


	public void draw(float mtx[]){
		int mProgram = activeFilter.getProgram();
		GLES20.glUseProgram(mProgram);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);


		for(UniformPair pair : uniformPairs){
			int tempUniformPair = GLES20.glGetUniformLocation(mProgram, pair.getKey());
			GLES20.glUniform1f(tempUniformPair, pair.getValue());
		}

		
		int timeHandle = GLES20.glGetUniformLocation(mProgram, "time");
		GLES20.glUniform1f(timeHandle, generator.nextFloat() );



		counter++;

		if(counter % counterMod == 0){
			ijkRand[0] = generator.nextFloat();
			ijkRand[1] = generator.nextFloat();
			ijkRand[2] = generator.nextFloat();
		}

		int iHandle = GLES20.glGetUniformLocation(mProgram, "irand");
		GLES20.glUniform1f(iHandle, ijkRand[0] );
		int jHandle = GLES20.glGetUniformLocation(mProgram, "jrand");
		GLES20.glUniform1f(jHandle, ijkRand[1] );
		int kHandle = GLES20.glGetUniformLocation(mProgram, "krand");
		GLES20.glUniform1f(kHandle, ijkRand[2] );

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


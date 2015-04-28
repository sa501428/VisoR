package com.visor.renderingpipeline;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GL_Toolbox {
	
	private final static int FLOAT_SIZE_BYTES = 4;
	private final static int SHORT_SIZE_BYTES = 2;
	private final static int COORDS_PER_VERTEX = 2;
	private final static int vertexStride = COORDS_PER_VERTEX * FLOAT_SIZE_BYTES; // 4 bytes per vertex

	public static int loadShader(int type, String shaderCode)
	{
		int shader = GLES20.glCreateShader(type);

		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);

		return shader;
	}

	public static int createOESTexture()
	{
		int[] newTexture = new int[1];

		GLES20.glGenTextures(1,newTexture, 0);
		GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, newTexture[0]);
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_LINEAR);        
		GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

		return newTexture[0];
	}

	public static int createRegularTexture()
	{
		int[] newTexture = new int[1];

		GLES20.glGenTextures(1,newTexture, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, newTexture[0]);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);

		return newTexture[0];
	}	

	public static void handleProgramUniform1f(int program, String variableName, float value) {
		GLES20.glUniform1f(GLES20.glGetUniformLocation(program, variableName), value );
	}
	
	public static void handleProgramUniform1i(int program, String variableName, int value) {
		GLES20.glUniform1i(GLES20.glGetUniformLocation(program, variableName), value );
	}

	public static int handleProgramAttribute(int program, String attributeName, FloatBuffer floatBuffer) {
		int attributeHandle = GLES20.glGetAttribLocation(program, attributeName);
		GLES20.glEnableVertexAttribArray(attributeHandle);
		GLES20.glVertexAttribPointer(attributeHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, floatBuffer);
		return attributeHandle;
	}

	public static int createProgram(String vertexShaderText, String fragmentShaderText) {
		int vertexShader = GL_Toolbox.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderText);
		int fragmentShader = GL_Toolbox.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderText);
		int newProgram = GLES20.glCreateProgram();             // create empty OpenGL ES Program
		GLES20.glAttachShader(newProgram, vertexShader);   // add the vertex shader to program
		GLES20.glAttachShader(newProgram, fragmentShader); // add the fragment shader to program
		GLES20.glLinkProgram(newProgram);
		return newProgram;
	}

	public static float[] getIdentityMatrix(){//, float[] translation, float[] scale) {
		float[] newMatrix = {1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1};
		return newMatrix;
	}
	
	public static float[] getMatrix(float[] rotation){//, float[] translation, float[] scale) {
		float[] newMatrix = getIdentityMatrix();
		Matrix.rotateM(newMatrix, 0, rotation[0], rotation[1], rotation[2], rotation[3]);
		//Matrix.translateM(newMatrix, 0, translation[0], translation[1], translation[2]);
		//Matrix.scaleM(newMatrix, 0, scale[0], scale[1], scale[2]);
		return newMatrix;
	}

	public static void clearGLScreen(float colorR, float colorG, float colorB){
		GLES20.glClearColor(colorR, colorG, colorB, 1.0f);
		GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
	}

	public static void activateTexture(int textureGL, int textureType, int textureName) {
		GLES20.glActiveTexture(textureGL);
		GLES20.glBindTexture(textureType, textureName);
	}

	public static FloatBuffer setupFloatBuffer(float[] floatValues) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floatValues.length * FLOAT_SIZE_BYTES);
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(floatValues);
		floatBuffer.position(0);
		return floatBuffer;
	}

	public static ShortBuffer setupShortBuffer(short[] shortValues) {
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(shortValues.length * SHORT_SIZE_BYTES);
		byteBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		shortBuffer.put(shortValues);
		shortBuffer.position(0);
		return shortBuffer;
	}

	public static int createFrameBuffer(int width, int height) {
		int[] frameBuffer = new int[1];
		GLES20.glGenFramebuffers(1, frameBuffer, 0);

		int[] buf = new int[width * height];
		IntBuffer texBuffer = ByteBuffer.allocateDirect(buf.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();;

		// generate the textures
		//GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, width, height, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, texBuffer);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, width, height, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, texBuffer);
		return frameBuffer[0];
	}

	public static void bindFrameBufferWithTexture(int frameBuffer, int texture) {
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, frameBuffer);
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, texture, 0);
	}

	


}

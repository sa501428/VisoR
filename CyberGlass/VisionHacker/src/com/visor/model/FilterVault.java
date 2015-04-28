package com.visor.model;

import java.util.ArrayList;

public class FilterVault {

	public static final String vertexShaderCode =
			"attribute vec4 position;" +
					"attribute vec2 inputTextureCoordinate;" +
					"varying vec2 textureCoordinate;" +
					"void main()" +
					"{"+
					"  gl_Position = position;"+
					"  textureCoordinate = inputTextureCoordinate;" +
					"}";

	public static final String vertexMatrixShaderCode =
			"attribute vec4 position;" +
					"uniform mat4 uMVPMatrix;   	\n" +
					"attribute vec2 inputTextureCoordinate;" +
					"varying vec2 textureCoordinate;" +
					"void main()" +
					"{"+
					"gl_Position = uMVPMatrix*position;"+
					"textureCoordinate = inputTextureCoordinate;" +
					"}";

	public static final String plain =
			"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform sampler2D texture1;" +
					"void main() {" +
					"  vec4 tex = texture2D( texture1, textureCoordinate );" +
					"  gl_FragColor = tex;" +
					"}";


	public static final String OESto2D =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  gl_FragColor = texture2D( s_texture, textureCoordinate );" +
					"}"; 

	public static final String digitalRain =
			"precision mediump float;" +
			"varying vec2 textureCoordinate;" +
			"uniform sampler2D texture1;" +
			"uniform sampler2D texture2;" +
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump float intensity = 0.5;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.9;"+

			"void main() {" +
			"  mediump vec3 textureColor = texture2D(texture1, textureCoordinate).rgb;"+
			"  mediump vec3 leakedTextureColor = texture2D(texture2, textureCoordinate).rgb;"+

			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+

			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(texture1, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(texture1, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(texture1, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(texture1, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(texture1, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(texture1, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(texture1, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(texture1, textureCoordinate + st0p).rgb, W);"+

			"  mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  if(mag > MagTol){"+
			"    	gl_FragColor = vec4(vec3(0.0),1.0);"+
			"  }"
			+ "else{"
			+ "		gl_FragColor = vec4(0.0, leakedTextureColor.g, 0.0, 0.0);"
			+ "}"+
			"}";



	public static String[] uniformDataNames = {"imageWidthFactor", "imageHeightFactor"};
	public static float[] uniformDataValues = {(float) 10.0, (float) 10.0};



	public static ArrayList<UniformPair> getAllUniformPairs() {

		ArrayList<UniformPair> uniformPairs = new ArrayList<UniformPair>();
		for(int i = 0; i < uniformDataNames.length; i++){
			UniformPair pair = new UniformPair(uniformDataNames[i], uniformDataValues[i]);
			uniformPairs.add(pair);
		}
		return uniformPairs;
	}

	public static void updateUniformValues(float[] updatedUniformValues) {
		for(int i = 0; i < updatedUniformValues.length; i++){
			uniformDataValues[i] = updatedUniformValues[i];
		}
	}

}

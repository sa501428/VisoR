package com.visor.model;

import java.util.ArrayList;

public class FilterVault {

	private static final String distort = "\n"+
			"vec4 Distort(vec4 p){"+
			" vec2 v = p.xy/p.w - vec2(0.5f,0f);"+
			" float theta  = atan(v.y,v.x);"+
			" float radius = length(v);"+
			" radius = pow(radius, 1.5f);"+
			" v.x = radius * cos(theta);"+
			" v.y = radius * sin(theta);"+
			" p.xy = v.xy*p.w;"+
			" return p;"+
			"}\n";
	
	public static final String vertexShaderCode =
			"attribute vec4 position;" +
					"attribute vec2 inputTextureCoordinate;" +
					"varying vec2 textureCoordinate;"+
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

	public static String approxFocusedHighEVM = 
			"precision highp float;" +
					"varying highp vec2 textureCoordinate;" +
					"uniform sampler2D texture1;" +
					"uniform sampler2D texture2;" +
					"uniform sampler2D texture3;" +
					"const highp vec2 centerCoordinate = vec2(0.5,0.5);"+
					"uniform highp float imageWidthFactor;"+
					"uniform highp float imageHeightFactor;"+
					"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
					"const highp float radius = 1.;"+
					"const highp float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
					"const highp float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
					"const highp float upperBound = 1e-1;"+
					"const highp float lowerBound = 1e-2;"+
					"const mediump float enhancedBound = 0.3;"+
					//"const mediump float augment = 2000.;"+
					"void main() {" +
					"  highp vec2 dirNorthEast =  vec2(imageWidthFactor, imageHeightFactor);"+
					"  highp vec2 dirSouthEast =  vec2(imageWidthFactor, -imageHeightFactor);"+

			"  highp vec3 texelCenter = texture2D(texture1, textureCoordinate).rgb;"+
			"if(length(centerCoordinate - textureCoordinate) < radius){"+
			"  highp vec3 texelNorthEastA = texture2D(texture2, textureCoordinate + offsetA*dirNorthEast).rgb;"+
			"  highp vec3 texelSouthEastA = texture2D(texture2, textureCoordinate + offsetA*dirSouthEast).rgb;"+
			"  highp vec3 texelSouthWestA = texture2D(texture2, textureCoordinate - offsetA*dirNorthEast).rgb;"+
			"  highp vec3 texelNorthWestA = texture2D(texture2, textureCoordinate - offsetA*dirSouthEast).rgb;"+
			"  highp vec3 texelNorthEastB = texture2D(texture3, textureCoordinate + offsetB*dirNorthEast).rgb;"+
			"  highp vec3 texelSouthEastB = texture2D(texture3, textureCoordinate + offsetB*dirSouthEast).rgb;"+
			"  highp vec3 texelSouthWestB = texture2D(texture3, textureCoordinate - offsetB*dirNorthEast).rgb;"+
			"  highp vec3 texelNorthWestB = texture2D(texture3, textureCoordinate - offsetB*dirSouthEast).rgb;"+

			"  highp float ptCenter   = dot( texelCenter, W);"+
			"  highp float ptNorthEastA = dot( texelNorthEastA , W);"+
			"  highp float ptSouthEastA = dot( texelSouthEastA , W);"+
			"  highp float ptSouthWestA = dot( texelSouthWestA , W);"+
			"  highp float ptNorthWestA = dot( texelNorthWestA , W);"+
			"  highp float ptNorthEastB = dot( texelNorthEastB , W);"+
			"  highp float ptSouthEastB = dot( texelSouthEastB , W);"+
			"  highp float ptSouthWestB = dot( texelSouthWestB , W);"+
			"  highp float ptNorthWestB = dot( texelNorthWestB , W);"+

			"  highp vec3 texelOutsideIntensityA = (texelNorthEastA + texelSouthEastA + texelSouthWestA + texelNorthWestA)/4.;"+
			"  highp vec3 texelOutsideIntensityB = (texelNorthEastB + texelSouthEastB + texelSouthWestB + texelNorthWestB)/4.;"+
			"  highp float averageOutsideIntensityA = (ptNorthEastA + ptSouthEastA + ptSouthWestA + ptNorthWestA)/4.;"+
			"  highp float averageOutsideIntensityB = (ptNorthEastB + ptSouthEastB + ptSouthWestB + ptNorthWestB)/4.;"+

			"  highp vec3 diffTexelOutsideIntensity = texelOutsideIntensityA - texelOutsideIntensityB;"+
			"  highp float diffAverageOutsideIntensity = averageOutsideIntensityA - averageOutsideIntensityB;"+
			"  highp float multiplier = 0.0;"+
			"  if(diffAverageOutsideIntensity < upperBound && diffAverageOutsideIntensity > lowerBound ){"+
			"    multiplier = enhancedBound / diffAverageOutsideIntensity;"+
			//"    multiplier = augment * diffAverageOutsideIntensity;"+
			"  }"+
			"  gl_FragColor = vec4(texelCenter + multiplier*diffTexelOutsideIntensity, 1.0);"+
			"}else{gl_FragColor = vec4(texelCenter, 1.0);}"+
			"}";

	public static String motionEVM = 
			"precision highp float;" +
					"varying highp vec2 textureCoordinate;" +
					"uniform sampler2D texture1;" +
					"uniform sampler2D texture2;" +
					"uniform sampler2D texture3;" +
					"const highp vec2 centerCoordinate = vec2(0.5,0.5);"+
					"uniform highp float imageWidthFactor;"+
					"uniform highp float imageHeightFactor;"+
					"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
					"const highp float radius = 1.;"+
					"const highp float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
					"const highp float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
					"const highp float upperBound = 1e-1;"+
					"const highp float lowerBound = 1e-2;"+
					"const mediump float enhancedBound = 0.3;"+
					//"const mediump float augment = 2000.;"+
					"void main() {" +
					"  highp vec3 texelCenter0 = texture2D(texture1, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter1 = texture2D(texture2, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter2 = texture2D(texture3, textureCoordinate).rgb;"+
					"  gl_FragColor = vec4(abs(texelCenter0.r - texelCenter1.r),abs(texelCenter0.g - texelCenter1.g),abs(texelCenter0.b - texelCenter1.b), 1.0);"+
			"}";



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

package com.visor.filters;

import java.util.ArrayList;

public class FilterVault {



	public static final String fs_GrayCCIR =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"; 
	
	public static final String standard =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"; 




	public static String intenseEVM = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensityOrigKawasaki = 0.85;"+
			"const mediump float intensityPosterEdge = 0.95;"+
			"const mediump float intensityAll = 0.50;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump float gaussFactor = 2.2;"+
			"const float nInv = float(1.0/(3.0));"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.18;"+
			"const mediump float Quantize = 3.0;"+
			"const mediump float edgeStrength = 2.;"+
			"const mediump float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
			"const mediump float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
			"const highp float upperBound = 0.000000001;"+
		//	"const highp float lowerBound = 0.0000000000000005;"+
		//	"const mediump float enhancedBound = 0.3;"+
			"const mediump float augment = 200.;"+
			"void main() {" +
			"  mediump vec2 dirNorthEast = gaussFactor * vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = gaussFactor * vec2(imageWidthFactor, -imageHeightFactor);"+

			"  mediump vec3 texelCenter = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec3 texelNorthEastA = texture2D(s_texture, textureCoordinate + offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastA = texture2D(s_texture, textureCoordinate + offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestA = texture2D(s_texture, textureCoordinate - offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestA = texture2D(s_texture, textureCoordinate - offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelNorthEastB = texture2D(s_texture, textureCoordinate + offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastB = texture2D(s_texture, textureCoordinate + offsetB*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestB = texture2D(s_texture, textureCoordinate - offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestB = texture2D(s_texture, textureCoordinate - offsetB*dirSouthEast).rgb;"+

			"  mediump float ptCenter   = dot( texelCenter, W);"+
			"  mediump float ptNorthEastA = dot( texelNorthEastA , W);"+
			"  mediump float ptSouthEastA = dot( texelSouthEastA , W);"+
			"  mediump float ptSouthWestA = dot( texelSouthWestA , W);"+
			"  mediump float ptNorthWestA = dot( texelNorthWestA , W);"+
			"  mediump float ptNorthEastB = dot( texelNorthEastB , W);"+
			"  mediump float ptSouthEastB = dot( texelSouthEastB , W);"+
			"  mediump float ptSouthWestB = dot( texelSouthWestB , W);"+
			"  mediump float ptNorthWestB = dot( texelNorthWestB , W);"+

			"  mediump vec3 texelOutsideIntensityA = (texelNorthEastA + texelSouthEastA + texelSouthWestA + texelNorthWestA)/4.;"+
			"  mediump vec3 texelOutsideIntensityB = (texelNorthEastB + texelSouthEastB + texelSouthWestB + texelNorthWestB)/4.;"+
			"  mediump float averageOutsideIntensityA = (ptNorthEastA + ptSouthEastA + ptSouthWestA + ptNorthWestA)/4.;"+
			"  mediump float averageOutsideIntensityB = (ptNorthEastB + ptSouthEastB + ptSouthWestB + ptNorthWestB)/4.;"+
			"  mediump vec3 diffTexelOutsideIntensity = texelOutsideIntensityA - texelOutsideIntensityB;"+
			"  mediump float diffAverageOutsideIntensity = averageOutsideIntensityA - averageOutsideIntensityB;"+
			"  highp float multiplier = 0.0;"+
			"  if(diffAverageOutsideIntensity < upperBound ){"+
	//		"    multiplier = enhancedBound / diffAverageOutsideIntensity;"+
			"    multiplier = augment * diffAverageOutsideIntensity;"+
			"  }"+
			"  gl_FragColor = vec4(texelCenter + multiplier*diffTexelOutsideIntensity, 1.0);"+
			"}";



	public static String intenseEVM_1 = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensityOrigKawasaki = 0.85;"+
			"const mediump float intensityPosterEdge = 0.95;"+
			"const mediump float intensityAll = 0.50;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump float gaussFactor = 2.2;"+
			"const float nInv = float(1.0/(3.0));"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.18;"+
			"const mediump float Quantize = 3.0;"+
			"const mediump float edgeStrength = 2.;"+
			"const mediump float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
			"const mediump float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
			"void main() {" +
			"  mediump vec2 dirNorthEast = gaussFactor * vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = gaussFactor * vec2(imageWidthFactor, -imageHeightFactor);"+

			"  mediump vec3 texelCenter = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec3 texelNorthEastA = texture2D(s_texture, textureCoordinate + offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastA = texture2D(s_texture, textureCoordinate + offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestA = texture2D(s_texture, textureCoordinate - offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestA = texture2D(s_texture, textureCoordinate - offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelNorthEastB = texture2D(s_texture, textureCoordinate + offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastB = texture2D(s_texture, textureCoordinate + offsetB*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestB = texture2D(s_texture, textureCoordinate - offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestB = texture2D(s_texture, textureCoordinate - offsetB*dirSouthEast).rgb;"+

			"  mediump float ptCenter   = dot( texelCenter, W);"+
			"  mediump float ptNorthEastA = dot( texelNorthEastA , W);"+
			"  mediump float ptSouthEastA = dot( texelSouthEastA , W);"+
			"  mediump float ptSouthWestA = dot( texelSouthWestA , W);"+
			"  mediump float ptNorthWestA = dot( texelNorthWestA , W);"+
			"  mediump float ptNorthEastB = dot( texelNorthEastB , W);"+
			"  mediump float ptSouthEastB = dot( texelSouthEastB , W);"+
			"  mediump float ptSouthWestB = dot( texelSouthWestB , W);"+
			"  mediump float ptNorthWestB = dot( texelNorthWestB , W);"+

			"  mediump vec3 texelOutsideIntensityA = (texelNorthEastA + texelSouthEastA + texelSouthWestA + texelNorthWestA)/4.;"+
			"  mediump vec3 texelOutsideIntensityB = (texelNorthEastB + texelSouthEastB + texelSouthWestB + texelNorthWestB)/4.;"+
			"  mediump float averageOutsideIntensityA = (ptNorthEastA + ptSouthEastA + ptSouthWestA + ptNorthWestA)/4.;"+
			"  mediump float averageOutsideIntensityB = (ptNorthEastB + ptSouthEastB + ptSouthWestB + ptNorthWestB)/4.;"+
			"  mediump vec3 diffTexelOutsideIntensity = texelOutsideIntensityA - texelOutsideIntensityB;"+
			"  mediump float diffAverageOutsideIntensity = averageOutsideIntensityA - averageOutsideIntensityB;"+
			"  mediump float multiplier = 0.0;"+
			"  if(diffAverageOutsideIntensity < 0.01){"+
			"    multiplier = 0.5 / diffAverageOutsideIntensity;"+
			"  }"+
			"  gl_FragColor = vec4(texelCenter + multiplier*diffTexelOutsideIntensity, 1.0);"+
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

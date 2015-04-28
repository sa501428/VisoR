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

	public static final String standardPlain =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = tex;" +
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

	public static String focusedGreenEVM = 
			"precision highp float;" +
					"varying highp vec2 textureCoordinate;" +
					"const highp vec2 centerCoordinate = vec2(0.5, 0.5);" +
					"const highp float radius = 1.;" +
					"uniform sampler2D texture1;" +
					"uniform sampler2D texture2;" +
					"uniform sampler2D texture3;" +
					"uniform sampler2D texture4;" +
					"uniform sampler2D texture5;" +
					"uniform sampler2D texture6;" +
					"uniform sampler2D texture7;" +
					"uniform sampler2D texture8;" +
					"uniform sampler2D texture9;" +
					//"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
					"const highp float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
					"const highp float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
					"const highp float upperBound = 1e-0;"+
					"const highp float lowerBound = 1e-998;"+
					"const mediump float enhancedBound = 0.5;"+
					//"const mediump float augment = 2000.;"+
					"void main() {" +
					"  highp vec3 texelCenter1 = texture2D(texture1, textureCoordinate).rgb;"+
					"  if( length(textureCoordinate - centerCoordinate) < radius ){"+			
					"  highp float texelCenter2 = texture2D(texture2, textureCoordinate).g;"+
					"  highp float texelCenter3 = texture2D(texture3, textureCoordinate).g;"+
					"  highp float texelCenter4 = texture2D(texture4, textureCoordinate).g;"+
					"  highp float texelCenter5 = texture2D(texture5, textureCoordinate).g;"+
					"  highp float texelCenter6 = texture2D(texture6, textureCoordinate).g;"+
					"  highp float texelCenter7 = texture2D(texture7, textureCoordinate).g;"+
					"  highp float texelCenter8 = texture2D(texture8, textureCoordinate).g;"+
					"  highp float texelCenter9 = texture2D(texture9, textureCoordinate).g;"+
				
					"  highp float texelCenterAverage = (texelCenter1.g + texelCenter2 + texelCenter3"+
					"+ texelCenter4 + texelCenter5 + texelCenter6 + texelCenter7 + texelCenter8 + texelCenter9)/9.0;"+
/*
				"  highp float ptCenter1   = dot( texelCenter1, W);"+
					"  highp float ptCenter2   = dot( texelCenter2, W);"+
					"  highp float ptCenter3   = dot( texelCenter3, W);"+
					"  highp float ptCenter4   = dot( texelCenter4, W);"+
					"  highp float ptCenter5   = dot( texelCenter5, W);"+
					"  highp float ptCenter6   = dot( texelCenter6, W);"+
					"  highp float ptCenter7   = dot( texelCenter7, W);"+
					"  highp float ptCenter8   = dot( texelCenter8, W);"+
					"  highp float ptCenter9   = dot( texelCenter9, W);"+
*/
			//"  highp float ptCenterAverage   = dot( texelCenterAverage, W);"+
	 			
			"  highp float diffTexel = texelCenter1.g - texelCenterAverage;"+
			"  highp float multiplier = 0.;"+//;"+
			"  highp float magnitude = diffTexel;"+//;"+
			"if( magnitude < upperBound && magnitude > lowerBound){"+
				"multiplier = enhancedBound / magnitude;}"+
			//"    multiplier = augment * diffAverageOutsideIntensity;"+
			"  gl_FragColor = vec4(texelCenter1 + vec3(0.,multiplier*diffTexel,0.), 1.0);"+
			"  }else{gl_FragColor = vec4(texelCenter1, 1.0);}"+
			"}";


	public static String focusedEVM = 
			"precision highp float;" +
					"varying highp vec2 textureCoordinate;" +
					"const highp vec2 centerCoordinate = vec2(0.5, 0.5);" +
					"const highp float radius = 1.;" +
					"uniform sampler2D texture1;" +
					"uniform sampler2D texture2;" +
					"uniform sampler2D texture3;" +
					"uniform sampler2D texture4;" +
					"uniform sampler2D texture5;" +
					"uniform sampler2D texture6;" +
					"uniform sampler2D texture7;" +
					"uniform sampler2D texture8;" +
					"uniform sampler2D texture9;" +
					"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
					"const highp float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
					"const highp float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
					"const highp float upperBound = 1e-2;"+
					"const highp float lowerBound = 1e-998;"+
					"const mediump float enhancedBound = 0.1;"+
					//"const mediump float augment = 2000.;"+
					"void main() {" +
					"  highp vec3 texelCenter1 = texture2D(texture1, textureCoordinate).rgb;"+
					"  if( length(textureCoordinate - centerCoordinate) < radius ){"+			
					"  highp vec3 texelCenter2 = texture2D(texture2, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter3 = texture2D(texture3, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter4 = texture2D(texture4, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter5 = texture2D(texture5, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter6 = texture2D(texture6, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter7 = texture2D(texture7, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter8 = texture2D(texture8, textureCoordinate).rgb;"+
					"  highp vec3 texelCenter9 = texture2D(texture9, textureCoordinate).rgb;"+
				
					"  highp vec3 texelCenterAverage = (texelCenter1 + texelCenter2 + texelCenter3"+
					"+ texelCenter4 + texelCenter5 + texelCenter6 + texelCenter7 + texelCenter8 + texelCenter9)/9.0;"+
/*
				"  highp float ptCenter1   = dot( texelCenter1, W);"+
					"  highp float ptCenter2   = dot( texelCenter2, W);"+
					"  highp float ptCenter3   = dot( texelCenter3, W);"+
					"  highp float ptCenter4   = dot( texelCenter4, W);"+
					"  highp float ptCenter5   = dot( texelCenter5, W);"+
					"  highp float ptCenter6   = dot( texelCenter6, W);"+
					"  highp float ptCenter7   = dot( texelCenter7, W);"+
					"  highp float ptCenter8   = dot( texelCenter8, W);"+
					"  highp float ptCenter9   = dot( texelCenter9, W);"+
*/
			"  highp float ptCenterAverage   = dot( texelCenterAverage, W);"+
	 			
			"  highp vec3 diffTexel = texelCenter1 - texelCenterAverage;"+
			"  highp float multiplier = 0.;"+//;"+
			"  highp float magnitude = length(diffTexel);"+//;"+
			"if( magnitude < upperBound && magnitude > lowerBound){"+
				"multiplier = enhancedBound / magnitude;}"+
			//"    multiplier = augment * diffAverageOutsideIntensity;"+
			"  gl_FragColor = vec4(texelCenter1 + multiplier*diffTexel, 1.0);"+
			"  }else{gl_FragColor = vec4(texelCenter1, 1.0);}"+
			"}";


	public static String approxHighEVM = 
			"precision highp float;" +
					"varying highp vec2 textureCoordinate;" +
					"uniform sampler2D texture1;" +
					"uniform sampler2D texture2;" +
					"uniform sampler2D texture3;" +
					"uniform highp float imageWidthFactor;"+
					"uniform highp float imageHeightFactor;"+
					"const highp vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
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
			"}";
	
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

	/*	

	public static String approxEVM = 
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform sampler2D texture1;" +
			"uniform sampler2D texture2;" +
			"uniform sampler2D texture3;" +
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float offsetA = 0.4;"+  // 0 < x < 1 // preferred < .5
			"const mediump float offsetB = 1.4;"+  // 1 < x < 2 // preferred < 1.5
			"const highp float upperBound = 0.000000000000000001;"+
			//	"const highp float lowerBound = 0.0000000000000005;"+
			//	"const mediump float enhancedBound = 0.3;"+
			"const mediump float augment = 2000.;"+
			"void main() {" +
			"  mediump vec2 dirNorthEast =  vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast =  vec2(imageWidthFactor, -imageHeightFactor);"+

			"  mediump vec3 texelCenter = texture2D(texture1, textureCoordinate).rgb;"+
			"  mediump vec3 texelNorthEastA = texture2D(texture2, textureCoordinate + offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastA = texture2D(texture2, textureCoordinate + offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestA = texture2D(texture2, textureCoordinate - offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestA = texture2D(texture2, textureCoordinate - offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelNorthEastB = texture2D(texture3, textureCoordinate + offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastB = texture2D(texture3, textureCoordinate + offsetB*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestB = texture2D(texture3, textureCoordinate - offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestB = texture2D(texture3, textureCoordinate - offsetB*dirSouthEast).rgb;"+

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

	public static String intenseEVM = 
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform sampler2D texture1;" +
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

			"  mediump vec3 texelCenter = texture2D(texture1, textureCoordinate).rgb;"+
			"  mediump vec3 texelNorthEastA = texture2D(texture1, textureCoordinate + offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastA = texture2D(texture1, textureCoordinate + offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestA = texture2D(texture1, textureCoordinate - offsetA*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestA = texture2D(texture1, textureCoordinate - offsetA*dirSouthEast).rgb;"+
			"  mediump vec3 texelNorthEastB = texture2D(texture1, textureCoordinate + offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelSouthEastB = texture2D(texture1, textureCoordinate + offsetB*dirSouthEast).rgb;"+
			"  mediump vec3 texelSouthWestB = texture2D(texture1, textureCoordinate - offsetB*dirNorthEast).rgb;"+
			"  mediump vec3 texelNorthWestB = texture2D(texture1, textureCoordinate - offsetB*dirSouthEast).rgb;"+

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

	 */

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

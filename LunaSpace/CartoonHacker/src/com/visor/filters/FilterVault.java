package com.visor.filters;

import java.util.ArrayList;

public class FilterVault {

	public static final String fragmentShaderCode =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;                            \n" +
					"uniform samplerExternalOES s_texture;               \n" +
					"void main() {" +
					"  gl_FragColor = texture2D( s_texture, textureCoordinate );\n" +
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

	public static final String cb_p =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec2 rcoeff = vec2(0.56667, 0.43333);" +
					"const vec2 gcoeff = vec2(0.55833, 0.44167);" +
					"const vec2 bcoeff = vec2(0.24167, 0.75833);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float r2 =  dot(tex.rg, rcoeff);" +
					"  float g2 =  dot(tex.rg, gcoeff);" +
					"  float b2 =  dot(tex.gb, bcoeff);" +
					"  gl_FragColor = vec4(r2, g2, b2, tex.a);" +
					"}";

	public static final String cb_d =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec2 rcoeff = vec2(0.625, 0.375);" +
					"const vec2 gcoeff = vec2(0.7, 0.3);" +
					"const vec2 bcoeff = vec2(0.3, 0.7);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float r2 =  dot(tex.rg, rcoeff);" +
					"  float g2 =  dot(tex.rg, gcoeff);" +
					"  float b2 =  dot(tex.gb, bcoeff);" +
					"  gl_FragColor = vec4(r2, g2, b2, tex.a);" +
					"}";

	public static final String cb_t =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec2 rcoeff = vec2(0.95, 0.05);" +
					"const vec2 gcoeff = vec2(0.43333, 0.56667);" +
					"const vec2 bcoeff = vec2(0.475, 0.525);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float r2 =  dot(tex.rg, rcoeff);" +
					"  float g2 =  dot(tex.gb, gcoeff);" +
					"  float b2 =  dot(tex.gb, bcoeff);" + 
					"  gl_FragColor = vec4(r2, g2, b2, tex.a);" +
					"}";

	public static final String cb_daltonize_p =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec2 gcoeff = vec2(-0.255, 1.255);" +
					"const vec3 bcoeff = vec3(0.30333, -0.545, 1.2417);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float g2 =  dot(tex.rg, gcoeff);" +
					"  float b2 =  dot(tex.rgb, bcoeff);" +
					"  gl_FragColor = vec4(tex.r, g2, b2, tex.a);" +
					"}"; 

	public static final String cb_daltonize_d =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec2 rcoeff = vec2(0.885, 0.115);" +
					"const vec3 bcoeff = vec3(-0.49, 0.19, 1.3);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float r2 =  dot(tex.rg, rcoeff);" +
					"  float b2 =  dot(tex.rgb, bcoeff);" + 
					"  gl_FragColor = vec4(r2, tex.g, b2, tex.a);" +
					"}"; 

	public static final String cb_daltonize_t =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 rcoeff = vec3(1.05, -0.3825, 0.3325);" +
					"const vec2 gcoeff = vec2(1.2342, -0.23417);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  float r2 =  dot(tex.rgb, rcoeff);" +
					"  float g2 =  dot(tex.gb, gcoeff);" + 
					"  gl_FragColor = vec4( r2, g2, tex.ba);" +
					"}";

	public static final String inverted =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(vec3(1.0)-tex.rgb, 0.5);" +
					"}";

	public static final String blue =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, 0.0, tex.b, tex.a);" +
					"}";

	public static final String noblue =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, tex.g, 0.0, tex.a);" +
					"}";

	public static final String green =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, tex.g, 0.0, tex.a);" +
					"}";

	public static final String nogreen =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, 0.0, tex.b, tex.a);" +
					"}";

	public static final String red =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, 0.0, 0.0, tex.a);" +
					"}";

	public static final String nored =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, tex.gba);" +
					"}";

	public static final String posterize =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"const mediump float Quantize = 5.0;"+
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"    gl_FragColor = vec4(vec3(ivec3(Quantize*tex.rgb + vec3(0.5)))/Quantize, tex.a);"+
					"}";

	public static String sobelEdge = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 1.0;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float ptCenter   = dot( textureColor, W);"+
			"mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"mediump float h = -ptNorthWest - 2.0 * ptNorth - ptNorthEast + ptSouthWest + 2.0 * ptSouth + ptSouthEast;"+
			"mediump float v = -ptSouthWest - 2.0 * ptWest - ptNorthWest + ptSouthEast + 2.0 * ptEast + ptNorthEast;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 0.5);"+
			"}";

	public static String invertedSobelEdge = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 1.0;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float ptCenter   = dot( textureColor, W);"+
			"mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"mediump float h = -ptNorthWest - 2.0 * ptNorth - ptNorthEast + ptSouthWest + 2.0 * ptSouth + ptSouthEast;"+
			"mediump float v = -ptSouthWest - 2.0 * ptWest - ptNorthWest + ptSouthEast + 2.0 * ptEast + ptNorthEast;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(vec3(1,1,1)-mix(textureColor, target, intensity), 1.0);"+
			"}";

	public static String sobelCartoon = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float ptCenter   = dot( textureColor, W);"+
			"mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"mediump float h = -ptNorthWest - 2.0 * ptNorth - ptNorthEast + ptSouthWest + 2.0 * ptSouth + ptSouthEast;"+
			"mediump float v = -ptSouthWest - 2.0 * ptWest - ptNorthWest + ptSouthEast + 2.0 * ptEast + ptNorthEast;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"}";


	public static String sobelCartoon2 = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float factor = 5.0;"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 dirEast = vec2(factor*imageWidthFactor, 0.0);"+
			"mediump vec2 dirNorth = vec2(0.0, factor*imageHeightFactor);"+
			"mediump vec2 dirNorthEast = vec2(factor*imageWidthFactor, factor*imageHeightFactor);"+
			"mediump vec2 dirSouthEast = vec2(factor*imageWidthFactor, -factor*imageHeightFactor);"+
			"mediump float ptCenter   = dot( textureColor, W);"+
			"mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"mediump float h = -ptNorthWest - 2.0 * ptNorth - ptNorthEast + ptSouthWest + 2.0 * ptSouth + ptSouthEast;"+
			"mediump float v = -ptSouthWest - 2.0 * ptWest - ptNorthWest + ptSouthEast + 2.0 * ptEast + ptNorthEast;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"}";


	public static String sobelPosterize = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.25;"+
			"const mediump float Quantize = 5.0;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float ptCenter   = dot( textureColor, W);"+
			"  mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"  mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"  mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"  mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"  mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"  mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"  mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"  mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"  mediump float h = -ptNorthWest - 2.0 * ptNorth - ptNorthEast + ptSouthWest + 2.0 * ptSouth + ptSouthEast;"+
			"  mediump float v = -ptSouthWest - 2.0 * ptWest - ptNorthWest + ptSouthEast + 2.0 * ptEast + ptNorthEast;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"  if(mag > MagTol){"+
			"    textureColor *= Quantize;"+
			"    textureColor += vec3(.5);"+
			"    ivec3 intrgb = ivec3(textureColor);"+
			"    textureColor = vec3(intrgb)/Quantize;"+
			"    gl_FragColor = vec4(textureColor, 1.);"+
			"  }"+
			"}";


	public static String sobelPosterize2 = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.3;"+
			"const mediump float Quantize = 5.0;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float ptCenter   = dot( textureColor, W);"+
			"  mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"  mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"  mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"  mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"  mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"  mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"  mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"  mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"  mediump float h = -ptNorthWest - 5.0 * ptNorth - ptNorthEast + ptSouthWest + 5.0 * ptSouth + ptSouthEast;"+
			"  mediump float v = -ptSouthWest - 5.0 * ptWest - ptNorthWest + ptSouthEast + 5.0 * ptEast + ptNorthEast;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"  if(mag > MagTol){"+
			"    textureColor *= Quantize;"+
			"    textureColor += vec3(.5);"+
			"    ivec3 intrgb = ivec3(textureColor);"+
			"    textureColor = vec3(intrgb)/Quantize;"+
			"    gl_FragColor = vec4(textureColor, 1.);"+
			"  }"
			+ "else{"
			+ "gl_FragColor = vec4(vec3(0.0), 1.);}"+
			"}";

	public static String sobelPosterize3 = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.3;"+
			"const mediump float Quantize = 15.0;"+
			"const mediump float factor = 2.5;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 dirEast = vec2(factor*imageWidthFactor, 0.0);"+
			"  mediump vec2 dirNorth = vec2(0.0, factor*imageHeightFactor);"+
			"  mediump vec2 dirNorthEast = vec2(factor*imageWidthFactor, factor*imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = vec2(factor*imageWidthFactor, -factor*imageHeightFactor);"+
			"  mediump float ptCenter   = dot( textureColor, W);"+
			"  mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"  mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"  mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"  mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"  mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"  mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"  mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"  mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"  mediump float h = -ptNorthWest - 5.0 * ptNorth - ptNorthEast + ptSouthWest + 5.0 * ptSouth + ptSouthEast;"+
			"  mediump float v = -ptSouthWest - 5.0 * ptWest - ptNorthWest + ptSouthEast + 5.0 * ptEast + ptNorthEast;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"  if(mag > MagTol){"+
			"    textureColor *= Quantize;"+
			"    textureColor += vec3(.5);"+
			"    ivec3 intrgb = ivec3(textureColor);"+
			"    textureColor = vec3(intrgb)/Quantize;"+
			"    gl_FragColor = vec4(textureColor, 1.);"+
			"  }"
			+ "else{"
			+ "gl_FragColor = vec4(vec3(0.0), 1.);}"+
			"}";


	public static String cartoonFlash = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"uniform mediump float irand;"+
			"uniform mediump float jrand;"+
			"uniform mediump float krand;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.1;"+
			"const mediump float Quantize = 3.0;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 dirEast = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 dirNorth = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 dirNorthEast = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float ptCenter   = dot( textureColor, W);"+
			"  mediump float ptSouthWest = dot( texture2D(s_texture, textureCoordinate - dirNorthEast).rgb, W);"+
			"  mediump float ptNorthEast = dot( texture2D(s_texture, textureCoordinate + dirNorthEast).rgb, W);"+
			"  mediump float ptNorthWest = dot( texture2D(s_texture, textureCoordinate - dirSouthEast).rgb, W);"+
			"  mediump float ptSouthEast = dot( texture2D(s_texture, textureCoordinate + dirSouthEast).rgb, W);"+
			"  mediump float ptWest = dot( texture2D(s_texture, textureCoordinate - dirEast).rgb, W);"+
			"  mediump float ptEast = dot( texture2D(s_texture, textureCoordinate + dirEast).rgb, W);"+
			"  mediump float ptSouth = dot( texture2D(s_texture, textureCoordinate - dirNorth).rgb, W);"+
			"  mediump float ptNorth = dot( texture2D(s_texture, textureCoordinate + dirNorth).rgb, W);"+
			"  mediump float h = -ptNorthWest - 5.0 * ptNorth - ptNorthEast + ptSouthWest + 5.0 * ptSouth + ptSouthEast;"+
			"  mediump float v = -ptSouthWest - 5.0 * ptWest - ptNorthWest + ptSouthEast + 5.0 * ptEast + ptNorthEast;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"  if(mag > MagTol){"+
			"    textureColor *= Quantize;"+
			"    textureColor += vec3(.5);"+
			"    ivec3 intrgb = ivec3(textureColor);"+
			"    textureColor = vec3(intrgb)/Quantize;"+
			"  if(length(textureColor - vec3(irand,jrand,krand) ) < 0.3  ){"+
			"    gl_FragColor = vec4(textureColor, 1.);"+
			"    }else{"+
			"    gl_FragColor = vec4(1.);"+
			"  }}"
			+ "else{"
			+ "gl_FragColor = vec4(vec3(0.0), 1.);}"+
			"}";


	public static final String kuwahara =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const mediump float radius = 1.0;"+
					"const mediump float radiusFactor = 5.0;"+
					"uniform mediump float imageWidthFactor;"+
					"uniform mediump float imageHeightFactor;"+
					"void main()"+
					"{"+
					"vec2 uv = textureCoordinate;"+
					"float n = ((radius + 1.0) * (radius + 1.0));"+
					"float nInv = float(1.0/n);"+
					"vec3 m[4];"+
					"vec3 s[4];"+
					"for (int k = 0; k < 4; ++k) {"+
					"    m[k] = vec3(0.0);"+
					"    s[k] = vec3(0.0);"+
					"}"+
					"for (float j = -radius; j <= 0.; ++j)  {"+
					"    for (float i = -radius; i <= 0.; ++i)  {"+
					"        vec3 c = texture2D(s_texture, uv + vec2(i*radiusFactor*imageWidthFactor,j*radiusFactor*imageHeightFactor)).rgb;"+
					"        m[0] += c; s[0] += c * c;"+ 
					"}}"+
					"for (float j = -radius; j <= 0.; ++j)  {"+
					"    for (float i = 0.; i <= radius; ++i)  {"+
					"        vec3 c = texture2D(s_texture, uv + vec2(i*radiusFactor*imageWidthFactor,j*radiusFactor*imageHeightFactor)).rgb;"+
					"        m[1] += c; s[1] += c * c;"+
					"   }}"+
					"for (float j = 0.; j <= radius; ++j)  {"+
					"    for (float i = 0.; i <= radius; ++i)  {"+
					"        vec3 c = texture2D(s_texture, uv + vec2(i*radiusFactor*imageWidthFactor,j*radiusFactor*imageHeightFactor)).rgb;"+
					"        m[2] += c; s[2] += c * c;"+
					"}}"+
					"for (float j = 0.; j <= radius; ++j)  {"+
					"   for (float i = -radius; i <= 0.; ++i)  {"+
					"        vec3 c = texture2D(s_texture, uv + vec2(i*radiusFactor*imageWidthFactor,j*radiusFactor*imageHeightFactor)).rgb;"+
					"        m[3] += c; s[3] += c * c;"+
					"}}"+
					"float min_sigma2 = 1e+2;"+
					"for (int k = 0; k < 4; ++k) {"+
					"    m[k] *= nInv;"+
					"    s[k] = abs(s[k] * nInv - m[k] * m[k]);"+
					"    float sigma2 = s[k].r + s[k].g + s[k].b;"+
					"    if (sigma2 < min_sigma2) {"+
					"        min_sigma2 = sigma2;"+
					"        gl_FragColor = vec4(m[k], 1.0);"+
					"}}}";



	public static String intenseCartoon = "#extension GL_OES_EGL_image_external : require\n"+
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
			"void main() {" +
			"  mediump vec2 dirEast = gaussFactor * vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 dirNorth = gaussFactor * vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 dirNorthEast = gaussFactor * vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 dirSouthEast = gaussFactor * vec2(imageWidthFactor, -imageHeightFactor);"+

			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec3 texelNorth = texture2D(s_texture, textureCoordinate + dirNorth).rgb;"+
			"  mediump vec3 texelNorthEast = texture2D(s_texture, textureCoordinate + dirNorthEast).rgb;"+
			"  mediump vec3 texelEast = texture2D(s_texture, textureCoordinate + dirEast).rgb;"+
			"  mediump vec3 texelSouthEast = texture2D(s_texture, textureCoordinate + dirSouthEast).rgb;"+
			"  mediump vec3 texelSouth = texture2D(s_texture, textureCoordinate - dirNorth).rgb;"+
			"  mediump vec3 texelSouthWest = texture2D(s_texture, textureCoordinate - dirNorthEast).rgb;"+
			"  mediump vec3 texelWest = texture2D(s_texture, textureCoordinate - dirEast).rgb;"+
			"  mediump vec3 texelNorthWest = texture2D(s_texture, textureCoordinate - dirSouthEast).rgb;"+

			"  mediump vec3 textureColor2 = textureColor * textureColor;"+
			"  mediump vec3 texelNorth2 = texelNorth * texelNorth;"+
			"  mediump vec3 texelNorthEast2 = texelNorthEast * texelNorthEast;"+
			"  mediump vec3 texelEast2 = texelEast * texelEast;"+
			"  mediump vec3 texelSouthEast2 = texelSouthEast * texelSouthEast;"+
			"  mediump vec3 texelSouth2 = texelSouth * texelSouth;"+
			"  mediump vec3 texelSouthWest2 = texelSouthWest * texelSouthWest;"+
			"  mediump vec3 texelWest2 = texelWest * texelWest;"+
			"  mediump vec3 texelNorthWest2 = texelNorthWest * texelNorthWest;"+

			"  mediump float ptCenter   = dot( textureColor, W);"+
			"  mediump float ptNorth = dot( texelNorth , W);"+
			"  mediump float ptNorthEast = dot( texelNorthEast , W);"+
			"  mediump float ptEast = dot( texelEast , W);"+
			"  mediump float ptSouthEast = dot( texelSouthEast , W);"+
			"  mediump float ptSouth = dot( texelSouth , W);"+
			"  mediump float ptSouthWest = dot( texelSouthWest , W);"+
			"  mediump float ptWest = dot( texelWest , W);"+
			"  mediump float ptNorthWest = dot( texelNorthWest , W);"+

			"  mediump float h = -ptNorthWest - edgeStrength * ptNorth - ptNorthEast + ptSouthWest + edgeStrength * ptSouth + ptSouthEast;"+
			"  mediump float v = -ptSouthWest - edgeStrength * ptWest - ptNorthWest + ptSouthEast + edgeStrength * ptEast + ptNorthEast;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			/*   */
			"vec3 m[4];"+
			"vec3 s[4];"+
			"for (int k = 0; k < 4; ++k) {"+
			"    m[k] = vec3(0.0);"+
			"    s[k] = vec3(0.0);"+
			"}"+
			"m[0] = texelSouthWest + texelSouth + texelWest + textureColor;"+
			"s[0] = texelSouthWest2 + texelSouth2 + texelWest2 + textureColor2;"+
			"m[1] = texelSouthEast + texelSouth + texelEast + textureColor;"+
			"s[1] = texelSouthEast2 + texelSouth2 + texelEast2 + textureColor2;"+    
			"m[2] = texelNorthEast + texelNorth + texelEast + textureColor;"+
			"s[2] = texelNorthEast2 + texelNorth2 + texelEast2 + textureColor2;"+    
			"m[3] = texelNorthWest + texelNorth + texelWest + textureColor;"+
			"s[3] = texelNorthWest2 + texelNorth2 + texelWest2 + textureColor2;"+
			//"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"vec3 posterized = vec3(ivec3(Quantize*textureColor ))/Quantize;"+
			"vec3 mixPosterEdge = posterized;"+
			"  if(mag < MagTol){"+
			"  mixPosterEdge = vec3(0.0);"+
			"}"+
			/*	"    textureColor *= Quantize;"+
			"    textureColor += vec3(.5);"+
			"    ivec3 intrgb = ivec3(textureColor);"+
			"    textureColor = vec3(intrgb)/Quantize;"+
			"    gl_FragColor = vec4(textureColor, 1.);"+
			
			 */
			 "vec3 mixOrigKawasaki = vec3(0.0);"+
			 "float min_sigma2 = 1e+2;"+
			 "for (int k = 0; k < 4; ++k) {"+
			 "    m[k] *= nInv;"+
			 "    s[k] = abs(s[k] * nInv - m[k] * m[k]);"+
			 "    float sigma2 = s[k].r + s[k].g + s[k].b;"+
			 "    if (sigma2 < min_sigma2) {"+
			 "        min_sigma2 = sigma2;"+
		//	 "        ivec3 intrgb = ivec3(Quantize*m[k] );"+
		//	 "        m[k] = vec3(intrgb)/Quantize;"+
//			 "        gl_FragColor = vec4(m[k], 1.0);"+
			 
			 "  mixOrigKawasaki = mix(textureColor, m[k], intensityOrigKawasaki);"+
			 
			 "}}"+
			 
			 "  gl_FragColor = vec4(mix(mixPosterEdge, mixOrigKawasaki, intensityAll), 1.0);"+
	//		 "    }"+
	//		 "else{"+
	//		 "  gl_FragColor = vec4(vec3(0.0), 1.);}"+
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

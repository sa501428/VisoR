package com.visor.filters;

import java.util.ArrayList;

import com.visor.model.Filter;
import com.visor.model.UniformPair;

public class FilterVault {

	private static final String fragmentShaderCode =
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

	private static final String cb_p =
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

	private static final String cb_d =
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

	private static final String cb_t =
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

	private static final String cb_daltonize_p =
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

	private static final String cb_daltonize_d =
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

	private static final String cb_daltonize_t =
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
					"  gl_FragColor = vec4(vec3(1.0)-tex.rgb, tex.a);" +
					"}";

	private static final String blue =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, 0.0, tex.b, tex.a);" +
					"}";

	private static final String noblue =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, tex.g, 0.0, tex.a);" +
					"}";

	private static final String green =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, tex.g, 0.0, tex.a);" +
					"}";

	private static final String nogreen =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, 0.0, tex.b, tex.a);" +
					"}";

	private static final String red =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(tex.r, 0.0, 0.0, tex.a);" +
					"}";

	private static final String nored =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"  gl_FragColor = vec4(0.0, tex.gba);" +
					"}";

	private static final String posterize =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"const mediump float Quantize = 5.0;"+
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"    gl_FragColor = vec4(vec3(ivec3(Quantize*tex.rgb + vec3(0.5)))/Quantize, tex.a);"+
					"}";

	private static String sobelEdge = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 1.0;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float i00   = dot( textureColor, W);"+
			"mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"}";

	private static String invertedSobelEdge = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 1.0;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float i00   = dot( textureColor, W);"+
			"mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(vec3(1,1,1)-mix(textureColor, target, intensity), 1.0);"+
			"}";

	private static String sobelCartoon = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"void main() {" +
			"mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"mediump float i00   = dot( textureColor, W);"+
			"mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"}";


/*	private static String sobelCartoon2 = "#extension GL_OES_EGL_image_external : require\n"+
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
			"mediump vec2 stp0 = vec2(factor*imageWidthFactor, 0.0);"+
			"mediump vec2 st0p = vec2(0.0, factor*imageHeightFactor);"+
			"mediump vec2 stpp = vec2(factor*imageWidthFactor, factor*imageHeightFactor);"+
			"mediump vec2 stpm = vec2(factor*imageWidthFactor, -factor*imageHeightFactor);"+
			"mediump float i00   = dot( textureColor, W);"+
			"mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"mediump float mag = 1.0 - length(vec2(h, v));"+
			"mediump vec3 target = vec3(mag);"+
			"gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"}";
			*/


	private static String sobelPosterize = "#extension GL_OES_EGL_image_external : require\n"+
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
			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
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

	public static final String redFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(1,0,0)) < .5){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	public static final String greenFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(0,1,0)) < 0.7){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	public static final String blueFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(0,0,1)) < .7){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	public static final String cyanFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(0,1,1)) < .7){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	public static final String magentaFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(1,0,1)) < .8){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	public static final String yellowFlash =
			"#extension GL_OES_EGL_image_external : require\n"+
					"precision mediump float;" +
					"varying vec2 textureCoordinate;" +
					"uniform samplerExternalOES s_texture;" +
					"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);" +
					"void main() {" +
					"  vec4 tex = texture2D( s_texture, textureCoordinate );" +
					"if(length(tex.rgb-vec3(1,1,0)) < .7){"+
					"  gl_FragColor = vec4(tex);"+
					"}"+
					"else{"+
					"  float c = dot(tex.rgb, graycoeff);" +
					"  gl_FragColor = vec4(vec3(c), tex.a);" +
					"}"+
					"}";

	private static String digitalRain = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.5;"+
			"const mediump float Quantize = 5.0;"+
			"uniform mediump float time;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  mediump vec3 target = vec3(mag);"+
			"  gl_FragColor = vec4(mix(textureColor, target, intensity), 1.0);"+
			"  if(mag > MagTol && abs(sin(dot(textureColor.rg ,textureCoordinate.xy) *5000.0*time)) <0.99){"+
			"    gl_FragColor = vec4(vec3(0.0), 1.);"+
			"  }else{"+
			"    gl_FragColor = vec4(vec3(0.494, 0.589, 0.623), 1.);}"+
			"}";
	
	private static String magneto = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const vec3 graycoeff = vec3(0.299, 0.587, 0.114);"+
			"const mediump float center = 0.5;"+
			"uniform mediump float targetX;"+
			"uniform mediump float targetY;"+
			"const mediump float radius = 0.001;"+
			"void main() {" +
			"  mediump vec4 textureColor = texture2D(s_texture, textureCoordinate);"+
			"  mediump float grayscale = dot(textureColor.rgb, graycoeff);"+ 
			"  mediump float diffX = targetX - textureCoordinate.x;"+
			"  mediump float diffY = targetY - textureCoordinate.y;"+
			"  if(diffX*diffX + diffY*diffY < radius){"+
			"    gl_FragColor = vec4(1.0-textureColor.rgb, 1.);"+
			"  }else{"+
			"    gl_FragColor = textureColor;}"+
			"}";

	private static String sobelPosterize2 = "#extension GL_OES_EGL_image_external : require\n"+
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
			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 5.0 * i0p1 - ip1p1 + im1m1 + 5.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 5.0 * im10 - im1p1 + ip1m1 + 5.0 * ip10 + ip1p1;"+
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
	
	private static String sobelPosterize3 = "#extension GL_OES_EGL_image_external : require\n"+
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
			"  mediump vec2 stp0 = vec2(factor*imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, factor*imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(factor*imageWidthFactor, factor*imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(factor*imageWidthFactor, -factor*imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 5.0 * i0p1 - ip1p1 + im1m1 + 5.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 5.0 * im10 - im1p1 + ip1m1 + 5.0 * ip10 + ip1p1;"+
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

	
	private static String cartoonFlash = "#extension GL_OES_EGL_image_external : require\n"+
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
			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 5.0 * i0p1 - ip1p1 + im1m1 + 5.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 5.0 * im10 - im1p1 + ip1m1 + 5.0 * ip10 + ip1p1;"+
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
	
	private static String matrix = "#extension GL_OES_EGL_image_external : require\n"+
			"precision mediump float;" +
			"varying mediump vec2 textureCoordinate;" +
			"uniform samplerExternalOES s_texture;" +
			"const mediump float intensity = 0.5;"+
			"uniform mediump float imageWidthFactor;"+
			"uniform mediump float imageHeightFactor;"+
			"const mediump vec3 W = vec3(0.2125, 0.7154, 0.0721);"+
			"const mediump float MagTol = 0.95;"+
			"const mediump float Quantize = 5.0;"+
			"void main() {" +
			"  mediump vec3 textureColor = texture2D(s_texture, textureCoordinate).rgb;"+
			"  mediump vec2 stp0 = vec2(imageWidthFactor, 0.0);"+
			"  mediump vec2 st0p = vec2(0.0, imageHeightFactor);"+
			"  mediump vec2 stpp = vec2(imageWidthFactor, imageHeightFactor);"+
			"  mediump vec2 stpm = vec2(imageWidthFactor, -imageHeightFactor);"+
			"  mediump float i00   = dot( textureColor, W);"+
			"  mediump float im1m1 = dot( texture2D(s_texture, textureCoordinate - stpp).rgb, W);"+
			"  mediump float ip1p1 = dot( texture2D(s_texture, textureCoordinate + stpp).rgb, W);"+
			"  mediump float im1p1 = dot( texture2D(s_texture, textureCoordinate - stpm).rgb, W);"+
			"  mediump float ip1m1 = dot( texture2D(s_texture, textureCoordinate + stpm).rgb, W);"+
			"  mediump float im10 = dot( texture2D(s_texture, textureCoordinate - stp0).rgb, W);"+
			"  mediump float ip10 = dot( texture2D(s_texture, textureCoordinate + stp0).rgb, W);"+
			"  mediump float i0m1 = dot( texture2D(s_texture, textureCoordinate - st0p).rgb, W);"+
			"  mediump float i0p1 = dot( texture2D(s_texture, textureCoordinate + st0p).rgb, W);"+
			"  mediump float h = -im1p1 - 2.0 * i0p1 - ip1p1 + im1m1 + 2.0 * i0m1 + ip1m1;"+
			"  mediump float v = -im1m1 - 2.0 * im10 - im1p1 + ip1m1 + 2.0 * ip10 + ip1p1;"+
			"  mediump float mag = 1.0 - length(vec2(h, v));"+
			"  if(mag > MagTol){"+
			"    gl_FragColor = vec4(vec3(0.0),1.);"+
			"  }"
			+ "else{"
			+ "gl_FragColor = vec4(0.0,0.797,0.0, 1.);}"+
			"}";

	/*	private static final String[] names = {"No Filter", "Grayscale", "Protanopia Simulator","Protanopia Corrected",
		"Deuteranopia Simulator","Deuteranopia Corrected", "Tritanopia Simulator","Tritanopia Corrected",
		"Inverted", "Blue", "No Blue", "Green",	"No Green", "Red", "No Red", "Posterize", "Sobel Edge",
		"Cartoon"};
	 */
	private static final String[] names = {"No Filter", "Grayscale", "Inverted",
		"Sobel Edge", "Inverted Sobel Edge","Sobel Cartoon", "Posterize", "Sobel Posterize",
		"Sobel Posterize V2", "Sobel Posterize V3",
		"Protanopia Simulator","Protanopia Corrected","Deuteranopia Simulator","Deuteranopia Corrected", "Tritanopia Simulator","Tritanopia Corrected",
		"Red","Green","Blue", "Cyan","Magenta","Yellow",
		"Red Flash", "Green Flash", "Blue Flash", "Cyan Flash", "Yellow Flash", "Magenta Flash",
		"Digital Rain", "Cartoon Flash", "Matrix"};

	private static final String[] shaders = {fragmentShaderCode, fs_GrayCCIR, inverted,
		sobelEdge, invertedSobelEdge, sobelCartoon, posterize, sobelPosterize,
		sobelPosterize2, sobelPosterize3,
		cb_p,cb_daltonize_p, cb_d, cb_daltonize_d, cb_t, cb_daltonize_t,
		red, green, blue, nored, nogreen, noblue,
		redFlash, greenFlash, blueFlash, cyanFlash, yellowFlash, magentaFlash,
		digitalRain, cartoonFlash, matrix};

	private static String[] uniformDataNames = {"imageWidthFactor", "imageHeightFactor"};
	private static float[] uniformDataValues = {(float) 10.0, (float) 10.0};


	public static String[] getAllNames() {
		return names;
	}

	public static int getNumFilters(){
		return names.length;
	}

	public static ArrayList<Filter> getAllFilters() {

		ArrayList<Filter> filters = new ArrayList<Filter>();
		for(int i = 0; i < names.length; i++){
			Filter filter = new Filter(names[i],shaders[i]);

			//filter.addUniformPairs(nameToUniformPairs.get(names[i]));	

			filters.add(filter);
		}
		return filters;
	}

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

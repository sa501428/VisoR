package com.visor.model;

public class DimensionVault {
	
	public static final float[] RED = {1.0f, 0f, 0f};
	public static final float[] BLUE = {0f, 0f, 1.0f};
	public static final float[] GREEN = {0f, 1f, 0f};
	
	public static float wholeTriangleVertices[] = { // in counterclockwise order:
		-1.0f,  1.0f,
		1.0f,  1.0f,
		1.0f,  -1.0f,
		-1.0f,  1.0f,
		1.0f,  -1.0f,
		-1.0f, -1.0f
	};

	public static final short drawOrder[] = { 0, 1, 2, 3, 4, 5};

	public static final float triangleVerticesLeft[] = { // in counterclockwise order:
		-1.0f,  1.0f,
		0.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f, -1.0f
	};

	public static final float triangleVerticesRight[] = { // in counterclockwise order:
		0.0f,  1.0f,
		1.0f,  1.0f,
		1.0f,  -1.0f,
		0.0f,  1.0f,
		1.0f,  -1.0f,
		0.0f, -1.0f
	};

	private static final float l = 0.25f, r = 0.75f, d = 0.0f;	
	private static float ll = l-d, lr = r-d, rl = l+d, rr = r+d;
	private static final float t = 0.0f, b = 1.0f, d2 = 0.0f;
	private static float lt = t+d2, lb = b-d2, rt = t+d2, rb = b-d2;

	public static float fullTextureVertices[] = { // in counterclockwise order:
		0f,  0f,
		1f,  0f,
		1f,  1f,
		0f,  0f,
		1f,  1f,
		0f,  1f
	};
	
	public static float textureVertices[] = { // in counterclockwise order:
		ll,  lt,
		rr,  rt,
		rr,  rb,
		ll,  lt,
		rr,  rb,
		ll,  lb
	};

	public static float textureVerticesLeft[] = { // in counterclockwise order:
		ll,  lt,
		lr,  lt,
		lr,  lb,
		ll,  lt,
		lr,  lb,
		ll,  lb
	};

	public static float textureVerticesRight[] = { // in counterclockwise order:
		rl,  rt,
		rr,  rt,
		rr,  rb,
		rl,  rt,
		rr,  rb,
		rl,  rb
	};
}

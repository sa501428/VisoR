package com.visor.model;

public class DimensionVault {

    public static final float[] RED = {1.0f, 0f, 0f};
    public static final float[] BLUE = {0f, 0f, 1.0f};
    public static final float[] GREEN = {0f, 1f, 0f};
    public static final short drawOrder[] = {0, 1, 2, 3, 4, 5};
    public static final float triangleVerticesLeft[] = { // in counterclockwise order:
            -1.0f, 1.0f,
            0.0f, 1.0f,
            0.0f, -1.0f,
            -1.0f, 1.0f,
            0.0f, -1.0f,
            -1.0f, -1.0f
    };
    public static final float triangleVerticesRight[] = { // in counterclockwise order:
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,
            0.0f, 1.0f,
            1.0f, -1.0f,
            0.0f, -1.0f
    };
    private static final float l = 0.25f, r = 0.75f, d = 0.0f;
    private static final float ll = l - d;
    private static final float lr = r - d;
    private static final float rl = l + d;
    private static final float rr = r + d;
    private static final float t = 0.0f, b = 1.0f, d2 = 0.0f;
    private static final float lt = t + d2;
    private static final float lb = b - d2;
    public static final float[] textureVerticesLeft = { // in counterclockwise order:
            ll, lt,
            lr, lt,
            lr, lb,
            ll, lt,
            lr, lb,
            ll, lb
    };
    private static final float rt = t + d2;
    private static final float rb = b - d2;
    public static final float[] textureVerticesRight = { // in counterclockwise order:
            rl, rt,
            rr, rt,
            rr, rb,
            rl, rt,
            rr, rb,
            rl, rb
    };
    public static float wholeTriangleVertices[] = { // in counterclockwise order:
            -1.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, -1.0f,
            -1.0f, -1.0f
    };
    public static float fullTextureVertices[] = { // in counterclockwise order:
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 0f,
            1f, 1f,
            0f, 1f
    };
    public static float textureVertices[] = { // in counterclockwise order:
            ll, lt,
            rr, rt,
            rr, rb,
            ll, lt,
            rr, rb,
            ll, lb
    };
}

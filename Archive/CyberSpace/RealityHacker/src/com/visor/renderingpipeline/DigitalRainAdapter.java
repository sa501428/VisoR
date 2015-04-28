package com.visor.renderingpipeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.util.Random;

public class DigitalRainAdapter {

	private Random generator = new Random();
	private int[] digitalRainImages;
	private int numImages;

	public int getRandomImage() {
		return digitalRainImages[generator.nextInt(numImages)];
	}

	public void addDigitalRainImages(Context context, int[] images) {

		numImages = images.length;
		digitalRainImages = new int[numImages];
		
		for(int i = 0; i < numImages; i++){
			digitalRainImageLoader(context, images[i], i);
		}
	}
	
	private void digitalRainImageLoader(Context context, int image, int indx){
		Bitmap currentImage = BitmapFactory.decodeResource(context.getResources(), image);
		digitalRainImages[indx] = GL_Toolbox.createRegularMirroredTexture();
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, currentImage, 0);
		currentImage.recycle();
	}
}

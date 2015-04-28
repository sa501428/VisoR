package com.visor.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

public class VisorButton extends IntermediateButton {

	public VisorButton(Context context) {super(context);}
	public VisorButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public VisorButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

	
	@Override
	protected void additionalSetup(Context context) {
		activateAlphaAnimator();
	}

	@Override
	protected void thisButtonClicked(Context context) {
		//filterIndices[0] = numPicker.getValue();
		//filterIndices[1] = numPicker.getValue();
		//context.setUpCameraViews();
	}
	
	private void activateAlphaAnimator() {
		ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "alpha", 100, 255);
		colorAnim.setDuration(1000);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
		colorAnim.start();
	}

}

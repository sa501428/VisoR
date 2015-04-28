package com.visor.ui;

import com.visor.hackerui.R;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public abstract class IntermediateButton extends ImageButton {

	private Animation shake;
	private ValueAnimator helpAnim;
	private boolean hasNotBeenMade = true;

	public IntermediateButton(Context context) {
		super(context);
		setup(context);
	}

	public IntermediateButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(context);
	}

	public IntermediateButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup(context);
	}

	private void setup(final Context context) {
		if(hasNotBeenMade){
			setAlpha(1.0f);
			shake = AnimationUtils.loadAnimation(context, R.anim.shake);
			helpAnim = ObjectAnimator.ofFloat(this, "alpha", 1.0f,0.5f,1.0f);
			setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					thisButtonClicked(context);
					animateClickedButton();
				}
			});
			additionalSetup(context);
			hasNotBeenMade = false;
		}
	}

	private void animateClickedButton() {
		helpAnim.start();
		startAnimation(shake);
	}

	abstract protected void additionalSetup(Context context);
	abstract protected void thisButtonClicked(Context context);

}

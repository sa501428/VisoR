package com.example.hacker3;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

class UISetup {

	int helpButtonId = R.id.helpIm;

	private static Animation shake;
	//private static ValueAnimator helpAnim;
	private final MainActivity context;

	public UISetup(MainActivity context) {
		this.context = context;
		shake = AnimationUtils.loadAnimation(context, R.anim.shake);
	}

	public void setUpHelp(final AlertDialog helpDialog) {

		// set up main button
		ImageButton visorButton = (ImageButton) context.findViewById(R.id.visorIm);
		visorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// animate visor button
		ValueAnimator colorAnim = ObjectAnimator.ofInt(visorButton, "alpha", 100, 255);
		colorAnim.setDuration(1000);
		colorAnim.setEvaluator(new ArgbEvaluator());
		colorAnim.setRepeatCount(ValueAnimator.INFINITE);
		colorAnim.setRepeatMode(ValueAnimator.REVERSE);
		colorAnim.start();


		// set up help button
		ImageButton helpButton = (ImageButton) context.findViewById(R.id.helpIm);
		helpButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//long dtMili = System.currentTimeMillis();
				animateClickedButton((ImageButton) v.findViewById(R.id.helpIm));
				helpDialog.show();
			}
		});
		helpButton.setAlpha(1.0f);
	}

	private void animateClickedButton(ImageButton button) {
		ValueAnimator helpAnim = ObjectAnimator.ofFloat(button, "alpha", 1.0f,0.5f,1.0f);
		helpAnim.start();
		button.startAnimation(shake);
	}


	public AlertDialog retrieveHelpDialog() {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context)
		.setTitle("Help")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		})
		.setMessage("Reality Hacker Version 0.9\n\nSelect a filter, then click the VisoR button to view your world "+
				"in a new way with a VR headset.\n\nSwipe on the camera screens or use the volume "+
				"button to change the filter while viewing.\n\nUse a fisheye lens to increase your field of view.\n\n"+
				"Compatible with Google Cardboard, Durovis Dive, and other VR Headsets.\n\n\u00A9 2014 VisoR");
		return helpBuilder.create();
	}
}

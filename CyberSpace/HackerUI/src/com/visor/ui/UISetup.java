package com.visor.ui;

import com.visor.hackerui.MainActivity;
import com.visor.hackerui.R;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class UISetup {
	
	int helpButtonId = R.id.helpIm;
	
	private Animation shake;
	private ValueAnimator helpAnim;

	public UISetup(Context context) {

		commonSetup(context);
		

	}
	
	private void commonSetup(Context context) {
		shake = AnimationUtils.loadAnimation(context, R.anim.shake);
	}

	public void setUpHelp(Context context, AlertDialog helpDialog){
		ImageButton button = (ImageButton) ((MainActivity) context).findViewById(helpButtonId);
		setup(button, helpDialog);
	}
	
	private void setup(final ImageButton button, final AlertDialog helpDialog) {
		button.setAlpha(1.0f);
		helpAnim = ObjectAnimator.ofFloat(button, "alpha", 1.0f,0.5f,1.0f);
		
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animateClickedButton(button);
				helpDialog.show();
			}
		});
	}
	
	private void animateClickedButton(ImageButton button) {
		helpAnim.start();
		button.startAnimation(shake);
	}




	public static AlertDialog retrieveHelpDialog(Context context) {
		
		//LayoutInflater inflater = LayoutInflater.from(context);
		//View layout = inflater.inflate(R.layout.helpscroll, null);
		
		
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
				
				
		
		//helpBuilder.setView(layout);
		return helpBuilder.create();
	}
}

package com.visor.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;

public class HelpButton extends IntermediateButton {
	
	public HelpButton(Context context) {super(context);}
	public HelpButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public HelpButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

	private static AlertDialog helpDialog = null;
	
	@Override
	protected void additionalSetup(Context context) {
		if(helpDialog == null)
			helpDialog = retrieveHelpDialog(context);
	}
	
	@Override
	protected void thisButtonClicked(Context context) {
		helpDialog.show();
	}
	
	private static AlertDialog retrieveHelpDialog(Context context) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context)
		.setTitle("Help")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		})
		.setMessage("Reality Hacker Version 1.0\n\nSelect a filter, then click the VisoR button to view your world "+
				"in a new way with a VR headset.\n\nSwipe on the camera screens or use the volume "+
				"button to change the filter while viewing.\n\nUse a fisheye lens to increase your field of view.\n\n"+
				"Compatible with Google Cardboard, Durovis Dive, and other VR Headsets.\n\n\u00A9 2014 VisoR");
		AlertDialog dialog = helpBuilder.create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
	
	
}

package com.visor.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.visor.filters.GlobalVarVault;
import com.visor.visionhacker.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;

public class FavFilterButton extends ToggleImageButton {

	public FavFilterButton(Context context) {super(context);}
	public FavFilterButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public FavFilterButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}	
	
	private static AlertDialog myDialog = null;

	@Override
	protected void additionalSetup(Context context) {
		offImageResource = R.drawable.heartlow;
		onImageResource = R.drawable.heartfluidlow;
		antagonistID = R.id.allFilters;
		iAmAllFilters = false;
		myListType = "Favorites";
		myFilters = new ArrayList<String>(Arrays.asList(GlobalVarVault.allFilterNames));
		if(myDialog == null)
			myDialog = createMyDialog(context);
	}
	
	@Override
	protected AlertDialog createMyDialog(Context context) {
		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(context)
		.setTitle("Help1")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		})
		.setMessage("Reality Hacker Version 0.9\n\nSelect a filter, then click the VisoR button to view your world "+
				"in a new way with a VR headset.\n\nSwipe on the camera screens or use the volume "+
				"button to change the filter while viewing.\n\nUse a fisheye lens to increase your field of view.\n\n"+
				"Compatible with Google Cardboard, Durovis Dive, and other VR Headsets.\n\n\u00A9 2014 VisoR");
		AlertDialog dialog = helpBuilder.create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
	@Override
	protected void launchDialog() {
		myDialog.show();
	}

}

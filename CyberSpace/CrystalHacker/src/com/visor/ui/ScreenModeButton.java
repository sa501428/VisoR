package com.visor.ui;

import com.visor.filters.GlobalVarVault;

import android.content.Context;
import android.util.AttributeSet;

public class ScreenModeButton extends IntermediateButton {

	public ScreenModeButton(Context context) {super(context);}
	public ScreenModeButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public ScreenModeButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

	
	@Override
	protected void additionalSetup(Context context) {
		setImageResource(GlobalVarVault.screenModeImages[GlobalVarVault.getFullScreenMode()]);
	}

	@Override
	protected void thisButtonClicked(Context context) {
		int index = GlobalVarVault.toggleFullScreenMode();
		setImageResource(GlobalVarVault.screenModeImages[index]);
		Toaster.displayUrgentToast(context, GlobalVarVault.screenModeNames[index]+" Mode", 0, Toaster.LENGTH_LONG);
	}

}

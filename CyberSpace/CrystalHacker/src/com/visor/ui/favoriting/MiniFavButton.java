package com.visor.ui.favoriting;

import com.visor.filters.GlobalVarVault;
import com.visor.ui.IntermediateButton;
import com.visor.ui.Toaster;

import android.content.Context;
import android.util.AttributeSet;

public class MiniFavButton extends IntermediateButton {

	public MiniFavButton(Context context) {super(context);}
	public MiniFavButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public MiniFavButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}

	@Override
	protected void additionalSetup(Context context) {
		
	}

	@Override
	protected void thisButtonClicked(Context context) {
		int index = GlobalVarVault.toggleFullScreenMode();
		setImageResource(GlobalVarVault.screenModeImages[index]);
		Toaster.displayUrgentToast(context, GlobalVarVault.screenModeNames[index]+" Mode", 0, Toaster.LENGTH_LONG);
	}

}

package com.visor.ui;

import java.util.ArrayList;

import com.visor.visionhacker.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;

public abstract class ToggleImageButton extends IntermediateButton {

	protected static boolean allFiltersActive = true;
	protected boolean iAmAllFilters;
	protected int offImageResource;
	protected int onImageResource;
	protected int antagonistID;
	
	protected ToggleImageButton antagonist = null;
	protected String myListType;
	protected ArrayList<String> myFilters = new ArrayList<String>();
	
	public ToggleImageButton(Context context) {super(context);}
	public ToggleImageButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public ToggleImageButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}	

	@Override
	protected void thisButtonClicked(Context context) {
		if(antagonist == null)
			antagonist = retrieveAntagonist(context);
		antagonist.toggleOff();
		setImageResource(onImageResource);
		Toaster.displayUrgentToast(context, "Active: "+myListType+" Filters", 0, Toaster.LENGTH_SHORT);
		
		setAllFilterBooleanStatus();
		launchDialog();
	}
	
	
	abstract protected void launchDialog();
	
	private ToggleImageButton retrieveAntagonist(Context context){
		return (ToggleImageButton) ((MainActivity) context).getWindow().getDecorView().findViewById(android.R.id.content).findViewById(antagonistID);
	}
	
	public void toggleOff(){
		setImageResource(offImageResource);
	}
	
	private void setAllFilterBooleanStatus(){
		allFiltersActive = iAmAllFilters;
	}
	
	abstract protected AlertDialog createMyDialog(Context context);
	
}

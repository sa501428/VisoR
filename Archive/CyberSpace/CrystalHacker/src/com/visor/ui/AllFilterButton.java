package com.visor.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.visor.filters.GlobalVarVault;
import com.visor.visionhacker.R;
import com.visor.visionhacker.MainActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

public class AllFilterButton extends ToggleImageButton {

	public AllFilterButton(Context context) {super(context);}
	public AllFilterButton(Context context, AttributeSet attrs) {super(context, attrs);}
	public AllFilterButton(Context context, AttributeSet attrs, int defStyle) {super(context, attrs, defStyle);}
	
	private static AlertDialog myDialog = null;

	@Override
	protected void additionalSetup(Context context) {
		offImageResource = R.drawable.redselectlow;
		onImageResource = R.drawable.greenselectlow;
		
		antagonistID = R.id.favFilters;
		iAmAllFilters = true;
		myListType = "All";
		myFilters = new ArrayList<String>(Arrays.asList(GlobalVarVault.allFilterNames));
		
		if(myDialog == null)
			myDialog = createMyDialog(context);
	}
	
	@Override
	protected AlertDialog createMyDialog(Context context) {
	
		// settingsOptions = {volumeButtonActive, xRotation, yRotation, zRotation}
		View settingsView = ((MainActivity) context).getLayoutInflater().inflate( R.layout.scrollview, null);
		
		
		TableLayout layout = (TableLayout) settingsView.findViewById(R.id.subTable);
		LayoutInflater inflater = LayoutInflater.from(context);
		
		for(int i = 0; i < 10; i++) {
		    View layout_number = inflater.inflate(R.layout.filterrow, layout, false);
		    layout.addView(layout_number);
		}
		
		
		//temp.addView(R.layout.filterrow);
		
		//settingsView = ((MainActivity) context).getLayoutInflater().inflate( R.layout.filterrow, null);
		//temp.addView(settingsView);
		//temp.addView(settingsView);
		//temp.addView(settingsView);
		
		//SeekBar seekBar = new SeekBar(this);
		//final Switch volumeSwitch = (Switch) settingsView.findViewById(R.id.switch_volume);
		

		AlertDialog.Builder setBuilder = new AlertDialog.Builder(context)
		.setTitle("All Filters")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		})
		.setView(settingsView);

		AlertDialog dialog = setBuilder.create();
		dialog.setCanceledOnTouchOutside(true);
		
		return dialog;
	}
	@Override
	protected void launchDialog() {
		myDialog.show();
	}

}

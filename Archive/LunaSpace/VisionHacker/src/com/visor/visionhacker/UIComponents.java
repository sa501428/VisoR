package com.visor.visionhacker;

import java.lang.reflect.Field;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.visor.visionhacker.R;

@SuppressLint("InflateParams")
public class UIComponents {

	private MainActivity context;
	private int numFilters;
	private String[] names;
	private Animation shake;

	public UIComponents(MainActivity context, int numFilters, String[] names) {
		this.context = context;
		this.numFilters = numFilters;
		this.names = names;
		shake = AnimationUtils.loadAnimation(context, R.anim.shake);
	}

	public void initialUISetup(final int[] filterIndices, final AlertDialog helpDialog, final AlertDialog settDialog) {

		final NumberPicker numPicker = (NumberPicker) context.findViewById(R.id.numberPicker);
		initializeNumberPickerWithFilters(numPicker);
		setNumberPickerTextColor(numPicker, Color.RED);


		// set up main button
		ImageButton visorButton = (ImageButton) context.findViewById(R.id.visorIm);
		visorButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				filterIndices[0] = numPicker.getValue();
				filterIndices[1] = numPicker.getValue();
				context.setUpCameraViews();
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
				animateClickedButton((ImageButton) context.findViewById(R.id.helpIm));
				helpDialog.show();
			}
		});
		helpButton.setAlpha(1.0f);

		// set up settings button
		ImageButton settButton = (ImageButton) context.findViewById(R.id.settingsIm);
		settButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				animateClickedButton((ImageButton) context.findViewById(R.id.settingsIm));



				settDialog.show();
			}
		});
		settButton.setAlpha(1.0f);
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
		.setMessage("Reality Hacker Version 1.1\n\nSelect a filter, then click the VisoR button to view your world "+
				"in a new way with a VR headset.\n\nSwipe on the camera screens or use the volume "+
				"button to change the filter while viewing.\n\nUse a fisheye lens to increase your field of view.\n\n"+
				"Compatible with Google Cardboard, Durovis Dive, and other VR Headsets.\n\n\u00A9 2014 VisoR");
		return helpBuilder.create();
	}

	public AlertDialog retrieveUpdateFilterDialog(final int[] filterIndices) {
		final NumberPicker picker = new NumberPicker(context);
		initializeNumberPickerWithFilters(picker);

		AlertDialog.Builder updateBuilder = new AlertDialog.Builder(context)
		.setTitle("Update Filter")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				filterIndices[0] = picker.getValue();
				filterIndices[1] = picker.getValue();
				context.updateFilters(MainActivity.NO_FILTERS);
			}
		})
		.setNegativeButton("Main Menu", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				context.endCurrentStreaming();
			}
		})
		.setNeutralButton("Record", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// TODO
			}
		})
		.setView(picker);

		return updateBuilder.create();
	}

	public AlertDialog retrieveSettingsDialog(final int[] settingsOptions) {

		// settingsOptions = {volumeButtonActive, xRotation, yRotation, zRotation}

		View settingsView = context.getLayoutInflater().inflate( R.layout.dialog_settings, null);
		//SeekBar seekBar = new SeekBar(this);
		final Switch volumeSwitch = (Switch) settingsView.findViewById(R.id.switch_volume);
		volumeSwitch.setChecked(settingsOptions[0] == 1);

		textViewSeekBarLinker(settingsView, R.id.textViewXnum, R.id.seekBarX,
				R.id.buttonXminus,R.id.buttonXplus, settingsOptions, 1, 24, 15);
		textViewSeekBarLinker(settingsView, R.id.textViewYnum, R.id.seekBarY,
				R.id.buttonYminus,R.id.buttonYplus,settingsOptions, 2, 24, 15);
		textViewSeekBarLinker(settingsView, R.id.textViewZnum, R.id.seekBarZ,
				R.id.buttonZminus,R.id.buttonZplus,settingsOptions, 3, 24, 15);


		AlertDialog.Builder setBuilder = new AlertDialog.Builder(context)
		.setTitle("Settings")
		.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				if(volumeSwitch.isChecked()){
					settingsOptions[0] = 1;
				}
				else{
					settingsOptions[0] = 0;
				}
				context.updateFilterVariables();
			}
		})
		.setView(settingsView);

		return setBuilder.create();
	}

	private void textViewSeekBarLinker(View settingsView, int textViewNum,
			int seekBarNum, int minus, int plus,
			final int[] settingsOptions, final int offset, final int maxNum, final int increment) {

		final TextView textView = (TextView) settingsView.findViewById(textViewNum); 
		textView.setText(""+settingsOptions[offset]);

		final SeekBar seekBar = (SeekBar) settingsView.findViewById(seekBarNum);		
		seekBar.setMax(maxNum);
		seekBar.setProgress(settingsOptions[offset]);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			public void onStopTrackingTouch(SeekBar arg0) {
				setViewValsTextArray(textView,settingsOptions,offset,arg0.getProgress()*increment);
			}
			public void onStartTrackingTouch(SeekBar arg0) {
				setViewValsTextArray(textView,settingsOptions,offset,arg0.getProgress()*increment);
			}
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				setViewValsTextArray(textView,settingsOptions,offset,arg0.getProgress()*increment);
			}
		});

		Button minusButton = (Button) settingsView.findViewById(minus);
		minusButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				settingsOptions[offset] -= increment;
				if(settingsOptions[offset] <= 0)
					settingsOptions[offset] = 0;
				setViewValsSeekText(textView, seekBar, settingsOptions[offset], increment);
			}
		});
		Button plusButton = (Button) settingsView.findViewById(plus);
		plusButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				settingsOptions[offset] += increment;
				if(settingsOptions[offset] >= maxNum*increment)
					settingsOptions[offset] = maxNum*increment;
				setViewValsSeekText(textView, seekBar, settingsOptions[offset], increment);
			}
		});

	}



	protected void setViewValsSeekText(TextView textView, SeekBar seekBar, int value, int increment) {
		textView.setText(""+value);
		seekBar.setProgress(value/increment);
	}

	protected void setViewValsTextArray(TextView textView, int[] settingsOptions, int offset, int value) {
		textView.setText(""+value);
		settingsOptions[offset] = value;
	}

	private void initializeNumberPickerWithFilters(NumberPicker numberPicker) {
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(numFilters-1);
		numberPicker.setDisplayedValues(names);
		numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
	}

	private static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
	{
		final int count = numberPicker.getChildCount();
		for(int i = 0; i < count; i++){
			View child = numberPicker.getChildAt(i);
			if(child instanceof EditText){
				try{
					Field selectorWheelPaintField = numberPicker.getClass()
							.getDeclaredField("mSelectorWheelPaint");
					selectorWheelPaintField.setAccessible(true);
					((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
					((EditText)child).setTextColor(color);
					numberPicker.invalidate();
					return true;
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		return false;
	}
}

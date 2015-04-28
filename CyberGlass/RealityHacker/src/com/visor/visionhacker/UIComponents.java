package com.visor.visionhacker;

import com.visor.rendering.VisorGLSurfaceView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class UIComponents {

	public static AlertDialog retrieveUpdateDialog(Context context, final VisorGLSurfaceView glSurfaceView) {
		AlertDialog.Builder updateBuilder = new AlertDialog.Builder(context)
		.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		
		if(! glSurfaceView.isInHeadsetMode()){
			updateBuilder.setNeutralButton("Headset Mode", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					glSurfaceView.setHeadsetMode(true);
				}
			});
		}
		else{
			updateBuilder.setNeutralButton("Fullscreen Mode", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					glSurfaceView.setHeadsetMode(false);
				}
			});
		}
		
		if(! glSurfaceView.isRecording()){
			updateBuilder.setNegativeButton("Record", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					glSurfaceView.changeRecordingState(true);
				}
			});
		}
		else{
			updateBuilder.setNegativeButton("Stop Recording", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					glSurfaceView.changeRecordingState(false);
				}
			});
		}

		return updateBuilder.create();
	}

}

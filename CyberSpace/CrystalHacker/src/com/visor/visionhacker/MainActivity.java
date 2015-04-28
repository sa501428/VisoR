package com.visor.visionhacker;

import com.visor.ui.Toaster;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	
	private Point size = new Point(100, 100);
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupIntialViewSettings();
		setContentView(R.layout.activity_main);
		handleUISettings();
	}

	private void handleUISettings() {
		getWindowManager().getDefaultDisplay().getSize(size);
		Toaster.setScreenWidth(size.x);	
	}

	private void setupIntialViewSettings() {
		this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}

package com.example.hacker3;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private final Point size = new Point(100, 100);

	private UISetup uiSetup = null;
	private AlertDialog helpDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupIntialViewSettings();
		setContentView(R.layout.activity_main);
		handleUISettings();
	}

	private void handleUISettings() {
		uiSetup = new UISetup(this);
		helpDialog = uiSetup.retrieveHelpDialog();
		helpDialog.setCanceledOnTouchOutside(true);

		getWindowManager().getDefaultDisplay().getSize(size);
		Toaster.setScreenWidth(size.x);

		uiSetup.setUpHelp(helpDialog);
	}

	private void setupIntialViewSettings() {
		this.setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}

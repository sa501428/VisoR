package com.visor.cartoonhacker;

import com.visor.filters.FilterVault;
import com.visor.streaming.MyGLSurfaceView;
import com.visor.streaming.MyRenderer;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements SurfaceTexture.OnFrameAvailableListener{

	// variables for camera feed
	private MyGLSurfaceView glSurfaceView;
	private SurfaceTexture surface;
	private MyRenderer renderer;
	private CameraAdapter cameraAdapter = new CameraAdapter();


	// elements of view
	private Point size = new Point();
	
	// customizable components
	private static float[] defaultUniformValues = {(float) 10.0, (float) 10.0};

	/* start of overriding activity methods */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setUpOverlayedViews();

		fixScreenView();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		cameraAdapter.destroyCamera();
	}

	@Override
	public void onResume(){
		super.onResume();
		cameraAdapter.destroyCamera();
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		super.onKeyLongPress(keyCode, event);
		return processAndroidButton( keyCode,  event);	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		return processAndroidButton( keyCode,  event);
	}

	private void setUpOverlayedViews() {
		RelativeLayout rLayout = new RelativeLayout(this);
		LayoutParams rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT); 
		rLayout.setLayoutParams(rlParams);


		RelativeLayout.LayoutParams tParams = new RelativeLayout.LayoutParams
				(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		tParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		//tParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		tParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		tParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		tParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		tParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		tParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		//tParams.addRule(RelativeLayout., RelativeLayout.TRUE);


		glSurfaceView = new MyGLSurfaceView(this);

		rLayout.addView(glSurfaceView);

		setContentView(rLayout);

	//	new Thread(new DigitalRainTask()).start();
	}

/*	public void updateDigitalRainImage(int imageIndex){
		rainImage.setImageResource(images[imageIndex]);
		System.out.println("Change attempted "+imageIndex);
	}
	

	class DigitalRainTask implements Runnable {

		Random generator = new Random();

		@Override
		public void run() {
			while(true)
				try {
					Thread.sleep(100);
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							updateDigitalRainImage(generator.nextInt(numImages));
						}

					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
		}
	}
*/


	private void fixScreenView() {

		getWindowManager().getDefaultDisplay().getSize(size);
		defaultUniformValues[0] = (float) (2.0/(float)size.x);
		defaultUniformValues[1] = (float) (1.0/(float)size.y);

		setUpCameraViews();
	}

	public void setUpCameraViews() {
		FilterVault.updateUniformValues(defaultUniformValues);
		renderer = glSurfaceView.getRenderer();
	}

	private boolean processAndroidButton(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			System.exit(0);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void startCamera(int texture)
	{
		surface = new SurfaceTexture(texture);
		surface.setOnFrameAvailableListener(this);
		renderer.setSurface(surface);
		cameraAdapter.setupCamera(surface);
	}

	public void onFrameAvailable(SurfaceTexture surfaceTexture)
	{
		glSurfaceView.requestRender();
	}
}

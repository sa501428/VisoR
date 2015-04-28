package com.visor.digitalrain;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public abstract class OnSwipeTouchListener implements OnTouchListener {

	//private int midWidth;

	public final GestureDetector gestureDetector;
	protected Context context;

	public OnSwipeTouchListener (Context context){
		gestureDetector = new GestureDetector(context, new GestureListener());
		this.context = context;
		//midWidth = widthPixels/2;
	}

	private final class GestureListener extends SimpleOnGestureListener {

		//private static final int SWIPE_THRESHOLD = 100;
		//private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		//private GestureDetector gestureDetector;

		@Override
		public boolean onDown(MotionEvent e) {
			//launchUpdateBox();
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			boolean result = false;
			try {
				//float diffY = e2.getY() - e1.getY();
				//float diffX = e2.getX() - e1.getX();

				launchUpdateBox();

			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	abstract public void launchUpdateBox();

}
package com.visor.visionhacker;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;

public abstract class OnSwipeTouchListener implements OnTouchListener {

    public final GestureDetector gestureDetector;
    private final int midWidth;

    public OnSwipeTouchListener(Context ctx, int widthPixels) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        midWidth = widthPixels / 2;
    }

    abstract public void onLeftScreenSwipe(int i);

    abstract public void onRightScreenSwipe(int i);

    abstract public void launchUpdateBox();

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

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
                float diffY = e2.getY() - e1.getY();
                //float diffX = e2.getX() - e1.getX();

                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {

                    if (e1.getRawX() < midWidth) {
                        onLeftScreenSwipe(Integer.signum((int) diffY));
                    } else {
                        onRightScreenSwipe(Integer.signum((int) diffY));
                    }
                    result = true;
                } else {
                    launchUpdateBox();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

}
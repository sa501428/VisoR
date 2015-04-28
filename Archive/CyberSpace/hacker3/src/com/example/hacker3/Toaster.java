package com.example.hacker3;

import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

class Toaster {
	
	public static int LENGTH_SHORT = Toast.LENGTH_SHORT;
	public static int LENGTH_LONG = Toast.LENGTH_LONG;
	private static int screenHalfWidth = 100;
	private static final Set<Toast> bread= new HashSet<Toast>();
	
	public static void setScreenWidth(int screenWidth) {
		screenHalfWidth = (int) (0.5*screenWidth);
	}
	
	public static void displayUrgentToast(Context context, String message, int offset, int timeLength){
		clearAllBread();
		displayRegularToast(context, message, offset, timeLength);
	}
	
	private static void displayRegularToast(Context context, String message, int offset, int timeLength){
		bread.add(createToast(context, message, offset, timeLength));
	}
	
	private static Toast createToast(Context context, String message, float offset, int length){
		Toast toast= Toast.makeText(context, message, length);  
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, (int)(offset*screenHalfWidth), 0);
		toast.show();
		return toast;
	}

	private static void clearAllBread() {
		for(Toast toast: bread){
			if(toast != null)
				toast.cancel();
		}
		bread.clear();
	}
}

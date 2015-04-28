package com.visor.filters;

import com.visor.hackerui.R;

public class GlobalVarVault {
	
	private static int isFullScreen = 0;
	public static boolean usingFavoritesList = false;
	public static int[] screenModeImages = {R.drawable.halflow, R.drawable.fulllow};
	public static String[] screenModeNames = {"Stereoscopic", "Fullscreen"};
	
	public static Integer filterSetActiveIndex = Integer.valueOf(0);
	public final static String allSet = "All", simpleSet = "Simple", colTransSet = "Color Transformation",
			colFlashSet = "Color Flash", simSet = "Simulation", compTransSet = "Complex Transformation";
	
	public static String[] allFilterNames = {"F1", "F2", "F3", "F4", "F5", "F6"};
	public static boolean[] allFilterFavorited = {false, false, false, false, false, false};
	
	public static int toggleFullScreenMode(){
		isFullScreen = 1 - isFullScreen;
		return isFullScreen;
	}
	
	public static boolean isFullscreen(){
		return isFullScreen == 1;
	}
	
}

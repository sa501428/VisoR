package com.visor.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class FavoritingBar extends LinearLayout {
	
	private int index;
	private String name;
	private boolean activeStatus;
	private boolean favStatus;

	public FavoritingBar(Context context, int index, String name, boolean activeStatus, boolean favStatus) {
		super(context);
		mySetup(activeStatus, name, index, favStatus);
	}
	public FavoritingBar(Context context, AttributeSet attrs, int index, String name, boolean activeStatus, boolean favStatus) {
		super(context, attrs);
		mySetup(activeStatus, name, index, favStatus);
	}
	public FavoritingBar(Context context, AttributeSet attrs, int defStyle, int index, String name, boolean activeStatus, boolean favStatus) {
		super(context, attrs, defStyle);
		mySetup(activeStatus, name, index, favStatus);
	}

	private void mySetup(boolean activeStatus, String name, int index, boolean favStatus) {
		this.index = index;
		this.name = name;
		this.activeStatus = activeStatus;
		this.favStatus = favStatus;
		
		
		
		inflateSelfWithOptions();
	}
	
	private void inflateSelfWithOptions() {
		
	}

}

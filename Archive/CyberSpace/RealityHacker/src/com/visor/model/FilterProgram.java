package com.visor.model;

import java.util.ArrayList;

public class FilterProgram {

	private String filterShader;
	private String id;
	private int myProgram;
	private ArrayList<UniformPair> myUnifromPairs = new ArrayList<UniformPair>();

	public FilterProgram(String id, String filterShader){
		this.id = id;
		this.filterShader = filterShader;
	}

	public String getFilterShader() {
		return filterShader;
	}

	@Override
	public String toString() {
		return id;
	}

	public void setProgram(int myProgram) {
		this.myProgram = myProgram;
	}
	
	public int getProgram(){
		return myProgram;
	}
	
	public void addUniformPairs(ArrayList<UniformPair> uniformPair){
		myUnifromPairs.addAll(uniformPair);
	}
	
	public ArrayList<UniformPair> getUniformPairs(){

		return myUnifromPairs;
	}
}

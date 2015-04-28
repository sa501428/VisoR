package com.visor.model;

public class UniformPair {
	
	private String key;
	private float value;
	
	public UniformPair(String key, float value){
		this.key = key;
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}
}

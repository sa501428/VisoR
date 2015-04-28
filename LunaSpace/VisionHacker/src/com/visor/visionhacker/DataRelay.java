package com.visor.visionhacker;

public class DataRelay {

	private float[] magneticValues;

	public void setMagneticField(float[] magneticValues) {
		this.magneticValues = magneticValues;
		normalize();
	}

	private void normalize() {
		float totalMagnitude = 0;
		for(int i = 0; i < magneticValues.length; i++)
			totalMagnitude += magneticValues[i] * magneticValues[i];

		totalMagnitude = (float) Math.sqrt(totalMagnitude);

		for(int i = 0; i < magneticValues.length; i++)
			magneticValues[i] = magneticValues[i] / totalMagnitude;
	}

	public float[] getMagneticValues(){
		return magneticValues;
	}

}

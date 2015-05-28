package com.visor.screenmesh;

import com.visor.model.DimensionVault;

public class ScreenMesh {


	//DimensionVault.drawOrder, DimensionVault.triangleVerticesLeft, DimensionVault.textureVerticesLeft;

	private float[][] textureXGrid, textureYGrid;
	private float[][] triangleXGrid, triangleYGrid;


	private short[] drawOrder;

	public ScreenMesh(int n, float[] triangleBounds, float textureOffset){
		if(n < 2)
			n = 2;

		generateInternalGrid(n, triangleBounds, textureOffset);
	}

	private void generateInternalGrid(int n, float[] triangleBounds, float textureOffset) {
		generateInternalTriangleGrid(n, triangleBounds);
		generateInternalTextureGrid(n, textureOffset);
		generateInternalOrder(n);
	}

	private void generateInternalTextureGrid(int n, float textureOffset) {
		textureXGrid = new float[n][n];
		textureYGrid = new float[n][n];

		float leftBound = 0.25f+textureOffset, rightBound = 0.75f+textureOffset;
		float topBound = 0f, bottomBound = 1f;

		float dX = (rightBound - leftBound)/(n-1);
		float dY = (bottomBound - topBound)/(n-1);

		// x values
		for(int j = 0; j < n; j++){
			float xVal = leftBound + j*dX;
			for(int i = 0; i < n; i++){
				textureXGrid[i][j] = xVal;
			}
		}

		// y values
		for(int i = 0; i < n; i++){
			float yVal = topBound + i*dY;
			for(int j = 0; j < n; j++){
				textureYGrid[i][j] = yVal;
			}
		}
	}

	// triangleBoundsLeft = [-1 0 1 -1]  right = [0 1 1 -1]
	private void generateInternalTriangleGrid(int n, float[] triangleBounds) {
		triangleXGrid = new float[n][n];
		triangleYGrid = new float[n][n];
		
		float leftBound = triangleBounds[0], rightBound = triangleBounds[1];
		float topBound = triangleBounds[2], bottomBound = triangleBounds[3];

		float dX = (rightBound - leftBound)/(n-1);
		float dY = (bottomBound - topBound)/(n-1);

		// x values
		for(int j = 0; j < n; j++){
			float xVal = leftBound + j*dX;
			for(int i = 0; i < n; i++){
				triangleXGrid[i][j] = xVal;
			}
		}

		// y values
		for(int i = 0; i < n; i++){
			float yVal = topBound + i*dY;
			for(int j = 0; j < n; j++){
				triangleYGrid[i][j] = yVal;
			}
		}

	}

	private void generateInternalOrder(int n) {
		int totalNumRows = 6*(n-1)*(n-1);
		drawOrder = new short[totalNumRows];
		for(short i = 0; i < totalNumRows; i++){
			drawOrder[i] = i;
		}
	}

	public float[] getTriangleVertices() {
		return generateTriangleVertices();
	}

	public static final float triangleVerticesLeft[] = { // in counterclockwise order:
		-1.0f,  1.0f,
		0.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f,  1.0f,
		0.0f,  -1.0f,
		-1.0f, -1.0f
	};
	
	
	
	private float[] generateTriangleVertices() {
		int n = triangleXGrid.length;
		int l = (n-1)*(n-1)*3*2*2;
		float[] triangleVertices = new float[l];
		
		int index = 0;
		for(int i = 0; i < n-1; i++){
			for(int j = 0; j < n-1; j++){
				// TODO replace with index++
				triangleVertices[index] = triangleXGrid[i][j];
				triangleVertices[index+1] = triangleYGrid[i][j];
				
				triangleVertices[index+2] = triangleXGrid[i][j+1];
				triangleVertices[index+3] = triangleYGrid[i][j+1];
				
				triangleVertices[index+4] = triangleXGrid[i+1][j+1];
				triangleVertices[index+5] = triangleYGrid[i+1][j+1];
				
				triangleVertices[index+6] = triangleXGrid[i][j];
				triangleVertices[index+7] = triangleYGrid[i][j];
				
				triangleVertices[index+8] = triangleXGrid[i+1][j+1];
				triangleVertices[index+9] = triangleYGrid[i+1][j+1];
				
				triangleVertices[index+10] = triangleXGrid[i+1][j];
				triangleVertices[index+11] = triangleYGrid[i+1][j];
				
				index += 12;
			}
		}
		
		return triangleVertices;
	}

	public float[] getTextureVertices() {
		return generateTextureVertices();
	}

	private float[] generateTextureVertices() {
		int n = textureXGrid.length;
		int l = (n-1)*(n-1)*3*2*2;
		float[] textureVertices = new float[l];
		
		int index = 0;
		for(int i = 0; i < n-1; i++){
			for(int j = 0; j < n-1; j++){
				// TODO replace with index++
				textureVertices[index] = textureXGrid[i][j];
				textureVertices[index+1] = textureYGrid[i][j];
				
				textureVertices[index+2] = textureXGrid[i][j+1];
				textureVertices[index+3] = textureYGrid[i][j+1];
				
				textureVertices[index+4] = textureXGrid[i+1][j+1];
				textureVertices[index+5] = textureYGrid[i+1][j+1];
				
				textureVertices[index+6] = textureXGrid[i][j];
				textureVertices[index+7] = textureYGrid[i][j];
				
				textureVertices[index+8] = textureXGrid[i+1][j+1];
				textureVertices[index+9] = textureYGrid[i+1][j+1];
				
				textureVertices[index+10] = textureXGrid[i+1][j];
				textureVertices[index+11] = textureYGrid[i+1][j];
				
				index += 12;
			}
		}
		
		return textureVertices;
	}

	public short[] getDrawOrder() {
		return drawOrder;
	}

	public void manipulateTriangleGrid(int i, int j, double dx, double dy) {
		triangleXGrid[i][j] += dx;
		triangleYGrid[i][j] += dy;
	} 

}

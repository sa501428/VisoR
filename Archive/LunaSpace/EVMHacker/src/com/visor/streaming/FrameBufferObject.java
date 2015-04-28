package com.visor.streaming;

import android.opengl.GLES20;

public class FrameBufferObject {
	
	private int fboId, fboTex, renderBufferId, fboWidth, fboHeight;

	FrameBufferObject(int fboWidth, int fboHeight){
		this.fboWidth = fboWidth;
		this.fboHeight = fboHeight;
	}

	public void initializeFBO(){
		int[] temp = new int[1];
		//generate fbo id
		GLES20.glGenFramebuffers(1, temp, 0);
		fboId = temp[0];
		//generate texture
		GLES20.glGenTextures(1, temp, 0);
		fboTex = temp[0];
		//generate render buffer
		GLES20.glGenRenderbuffers(1, temp, 0);
		renderBufferId = temp[0];
		//Bind Frame buffer
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fboId);
		//Bind texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fboTex);
		//Define texture parameters
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, fboWidth, fboHeight, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		//Bind render buffer and define buffer dimension
		GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, renderBufferId);
		GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, fboWidth, fboHeight);
		//Attach texture FBO color attachment
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, fboTex, 0);
		//Attach render buffer to depth attachment
		GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, renderBufferId);
		//we are done, reset
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, 0);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
	}

	
	private void setupRenderToTexture(){
		// create the ints for the framebuffer, depth render buffer and texture
		fb = new int[1];
		depthRb = new int[1];
		renderTex = new int[1];

		// generate
		GLES20.glGenFramebuffers(1, fb, 0);
		GLES20.glGenRenderbuffers(1, depthRb, 0); // the depth buffer
		GLES20.glGenTextures(1, renderTex, 0);

		// generate texture
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, renderTex[0]);

		// parameters - we have to make sure we clamp the textures to the edges
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
				GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
				GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
				GLES20.GL_LINEAR);

		// create it
		// create an empty intbuffer first
		int[] buf = new int[texW * texH];
		texBuffer = ByteBuffer.allocateDirect(buf.length
				* FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asIntBuffer();;

		// generate the textures
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGB, texW, texH, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_SHORT_5_6_5, texBuffer);

		// create render buffer and bind 16-bit depth buffer
		GLES20.glBindRenderbuffer(GLES20.GL_RENDERBUFFER, depthRb[0]);
		GLES20.glRenderbufferStorage(GLES20.GL_RENDERBUFFER, GLES20.GL_DEPTH_COMPONENT16, texW, texH);
	}
	
	public boolean actualRendering(){
		// viewport should match texture size
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1, 1, 0.5f, 10);
		GLES20.glViewport(0, 0, this.texW, this.texH);

		// Bind the framebuffer
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fb[0]);

		// specify texture as color attachment
		GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, renderTex[0], 0);

		// attach render buffer as depth buffer
		GLES20.glFramebufferRenderbuffer(GLES20.GL_FRAMEBUFFER, GLES20.GL_DEPTH_ATTACHMENT, GLES20.GL_RENDERBUFFER, depthRb[0]);

		// check status
		int status = GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER);
		if (status != GLES20.GL_FRAMEBUFFER_COMPLETE)
			return false;

		// Clear the texture (buffer) and then render as usual...
		GLES20.glClearColor(.0f, .0f, .0f, 1.0f);
		GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		return true;
	}
	
	
}

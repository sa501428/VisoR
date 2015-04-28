package com.visor.streaming;

import android.opengl.GLES20;

public class RenderToTexture {


	private void createRenderTarget(){
		// The framebuffer, which regroups 0, 1, or more textures, and 0 or 1 depth buffer.
		int[] FramebufferName;
		GLES20.glGenFramebuffers(1, FramebufferName);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, FramebufferName);
	}
}

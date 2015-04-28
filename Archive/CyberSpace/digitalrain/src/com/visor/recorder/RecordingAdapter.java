package com.visor.recorder;

import java.io.File;
import javax.microedition.khronos.opengles.GL10;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.text.format.Time;
import android.util.Log;

public class RecordingAdapter {

	public static final String TAG = "Recorder";
	private int recordingTexture;
	private static TextureMovieEncoder sVideoEncoder = new TextureMovieEncoder();
	private FullFrameRect mFullScreen;
	private boolean mRecordingEnabled;
	private int mFrameCount = 0;

	private static final int RECORDING_OFF = 0;
	private static final int RECORDING_ON = 1;
	private static final int RECORDING_RESUMED = 2;
	private static int mRecordingStatus = RECORDING_OFF;
	private boolean isRecording = false;
	// HD quality
	private int filmWidth = 1280, filmHeight = 720;
	// regular quality
	// private int filmWidth = 640, filmHeight = 480;

	public RecordingAdapter(int recordingTexture, int width, int height) {
		this.recordingTexture = recordingTexture;
		mRecordingEnabled = sVideoEncoder.isRecording();

		// match android screen
		//this.filmWidth = width; this.filmHeight = height;

		Log.d(TAG, "Recording Adapter made");

		// We're starting up or coming back.  Either way we've got a new EGLContext that will
		// need to be shared with the video encoder, so figure out if a recording is already
		// in progress.
		mRecordingEnabled = sVideoEncoder.isRecording();
		if (mRecordingEnabled) {
			mRecordingStatus = RECORDING_RESUMED;
		} else {
			mRecordingStatus = RECORDING_OFF;
		}

		// Set up the texture blitter that will be used for on-screen display.  This
		// is *not* applied to the recording, because that uses a separate shader.
		mFullScreen = new FullFrameRect();
	}


	/**
	 * Notifies the renderer that we want to stop or start recording.
	 */
	public void changeRecordingState(boolean isRecording) {
		Log.d(TAG, "changeRecordingState: was " + mRecordingEnabled + " now " + isRecording);
		mRecordingEnabled = isRecording;
	}

	public void onDrawFrame(GL10 unused, float[] mtx, SurfaceTexture mSurfaceTexture) {

		if (mRecordingEnabled) {
			determineProperResponseIntiateRecording();
		} else {
			determineProperResponseWhileNotRecording();
		}

		sVideoEncoder.setTextureId(recordingTexture);

		// Tell the video encoder thread that a new frame is available.
		// This will be ignored if we're not actually recording.
		sVideoEncoder.frameAvailable(mSurfaceTexture);

		mFullScreen.drawFrame(recordingTexture, mtx);
	}

	private void determineProperResponseWhileNotRecording() {
		isRecording = false;
		switch (mRecordingStatus) {
		case RECORDING_ON:
		case RECORDING_RESUMED:
			// stop recording
			Log.d(TAG, "STOP recording");
			sVideoEncoder.stopRecording();
			mRecordingStatus = RECORDING_OFF;
			break;
		case RECORDING_OFF:
			// yay
			break;
		default:
			throw new RuntimeException("unknown status " + mRecordingStatus);
		}
	}


	private void determineProperResponseIntiateRecording() {
		isRecording = true;
		switch (mRecordingStatus) {
		case RECORDING_OFF:
			Log.d(TAG, "START recording");
			// start recording
			sVideoEncoder.startRecording(new TextureMovieEncoder.EncoderConfig(
					generateNewFile(), filmWidth, filmHeight, 20000000, EGL14.eglGetCurrentContext()));
			mRecordingStatus = RECORDING_ON;
			break;
		case RECORDING_RESUMED:
			Log.d(TAG, "RESUME recording");
			sVideoEncoder.updateSharedContext(EGL14.eglGetCurrentContext());
			mRecordingStatus = RECORDING_ON;
			break;
		case RECORDING_ON:
			// yay
			break;
		default:
			throw new RuntimeException("unknown status " + mRecordingStatus);
		}
	}


	private static String getTimeStamp() {
		Time now = new Time();
		now.setToNow();
		return now.format("%Y_%m_%d_%H_%M_%S");
	}

	private File generateNewFile() {
		File outputFile = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/download", getTimeStamp() +".mp4");
		return outputFile;
	}


	public void drawBox() {
		if (isRecording() && (++mFrameCount & 0x04) == 0) {

			GLES20.glEnable(GLES20.GL_SCISSOR_TEST);
			GLES20.glScissor(0, 0, 100, 100);
			GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
			GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
			GLES20.glDisable(GLES20.GL_SCISSOR_TEST);
		}
	}


	public boolean isRecording() {
		return isRecording;
	}
}

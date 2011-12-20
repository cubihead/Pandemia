package com.beecub.games.pandemic;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
	private static final String TAG = "beecub";

	// Surface holder that can access the physical surface
	private SurfaceHolder mSurfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainGamePanel mGamePanel;

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {
		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.mSurfaceHolder = surfaceHolder;
		this.mGamePanel = gamePanel;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			try {
				canvas = this.mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					this.mGamePanel.draw(canvas);				
				}
			} finally {
				if (canvas != null) {
				    mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
}

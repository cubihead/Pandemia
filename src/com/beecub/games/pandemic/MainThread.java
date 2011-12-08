/**
 * 
 */
package com.beecub.games.pandemic;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * @author impaler
 *
 * The Main thread which contains the game loop. The thread must have access to 
 * the surface view and holder to trigger events every game tick.
 */
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
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.mSurfaceHolder.lockCanvas();
				synchronized (mSurfaceHolder) {
					// update game state 
					this.mGamePanel.update();
					// render state to the screen
					// draws the canvas on the panel
					this.mGamePanel.draw(canvas);				
				}
			} finally {
				// in case of an exception the surface is not left in 
				// an inconsistent state
				if (canvas != null) {
				    mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
	}
	
}

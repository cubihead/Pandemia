/**
 * 
 */
package com.beecub.games.pandemic;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.beecub.games.pandemic.model.WorldMap;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = "beecub";
	
	private MainThread mThread;
	private WorldMap mMap;
	
	public static int mCanvasWidth;
	public static int mCanvasHeight;
	

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create droid and load bitmap
		mMap = new WorldMap(BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight);
		
		// create the game loop thread
		mThread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	    mCanvasWidth = width;
	    mCanvasHeight = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
	    mThread.setRunning(true);
	    mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		mThread.setRunning(false);
        ((Activity)getContext()).finish();
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
			    mThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
		    
		    if (!mMap.isTouched()) {
                mMap.setTouched(true);
            }
		    
		    mMap.setDown((int)event.getX(), (int)event.getY());
			
			Log.d(TAG, "Coords Down: x=" + event.getX() + ",y=" + event.getY());
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (mMap.isTouched()) {
			    mMap.setUp((int)event.getX(), (int)event.getY());
	            mMap.setPosition();
	            mMap.setDown((int)event.getX(), (int)event.getY());
			    
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
		    mMap.setUp((int)event.getX(), (int)event.getY());
		    mMap.setPosition();
		    
		    Log.d(TAG, "Coords Up: x=" + event.getX() + ",y=" + event.getY());

			if (mMap.isTouched()) {
			    mMap.setTouched(false);
			}
		}
		return true;
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		mMap.draw(canvas);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		// Update the lone droid
	    mMap.update();
	}

}

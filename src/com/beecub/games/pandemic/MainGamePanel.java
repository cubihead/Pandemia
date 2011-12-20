package com.beecub.games.pandemic;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.beecub.games.pandemic.model.Map;
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = "beecub";
	
	private MainThread mThread;
	//private Map mMap;
	
	private ArrayList<Map> mAreas = new ArrayList<Map>();
	
	public static int mCanvasWidth;
	public static int mCanvasHeight;
	
	private long mUpdateTime = new Date().getTime();
	public static int mInfectivity = 0;
	public static int mConspicuity = 0;
	public static int mDeadliness = 0;
	

	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		
		//mMap = new Map("WorldMap", 0, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight);
		
		// create areas
		mAreas.add(new Map("Canada", 34278406, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight));
		mAreas.add(new Map("UnitedStates", 311484627, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Mexico", 112322757, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Peru", 29546963, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Brazil", 194946470, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Argentina", 40412376, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("WestEurope", 481132879, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("EastEurope", 334787421, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("SouthAfrica", 87517832, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("NorthAfrica", 703574174, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("MiddleEast", 523157477, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Russia", 145637542, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("China", 1573234951, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("Australia", 378233475, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		mAreas.add(new Map("India", 1242354837, BitmapFactory.decodeResource(getResources(), R.drawable.worldmap), mCanvasWidth, mCanvasHeight)); 
		
		mThread = new MainThread(getHolder(), this);
		
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
	    mThread = new MainThread(getHolder(), this);
        mThread.setRunning(true);
        mThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		mThread.setRunning(false);
        ((Activity)getContext()).finish();
		boolean retry = true;
		while (retry) {
			try {
			    Log.d(TAG, "Try to join thread");
			    mThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    for(int i = 0; i < mAreas.size(); i++) {
    		if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		    
    		    if (!mAreas.get(i).isTouched()) {
    		        mAreas.get(i).setTouched(true);
                }
    		    
    		    mAreas.get(i).setDown((int)event.getX(), (int)event.getY());
    			
    			Log.d(TAG, "Coords Down: x=" + event.getX() + ",y=" + event.getY());
    		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
    			if (mAreas.get(i).isTouched()) {
    			    mAreas.get(i).setUp((int)event.getX(), (int)event.getY());
    			    mAreas.get(i).setPosition();
    			    mAreas.get(i).setDown((int)event.getX(), (int)event.getY());
    			}
    		} if (event.getAction() == MotionEvent.ACTION_UP) {
    		    mAreas.get(i).setUp((int)event.getX(), (int)event.getY());
    		    mAreas.get(i).setPosition();
    		    
    		    Log.d(TAG, "Coords Up: x=" + event.getX() + ",y=" + event.getY());
    
    			if (mAreas.get(i).isTouched()) {
    			    mAreas.get(i).setTouched(false);
    			}
    		}
	    }
		return true;
	}

	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		//mMap.draw(canvas);
		for(int i = 0; i < mAreas.size(); i++) {
		    mAreas.get(i).draw(canvas);
		}
	}
}

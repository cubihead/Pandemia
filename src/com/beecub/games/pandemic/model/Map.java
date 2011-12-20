package com.beecub.games.pandemic.model;

import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.beecub.games.pandemic.MainGamePanel;

public class Map {

	private Bitmap mBitmap;
	private int mX;
	private int mY;
	private int mDownX;
	private int mUpX;
	private int mDownY;
    private int mUpY;
	private boolean mTouched = false;
	
	private String mName;
	private boolean mInfected;
	private long mPopulation;
	private long mInfectedPopulation;
	
	
	public Map(String name, long population, Bitmap bitmap, int x, int y) {
	    mName = name;
	    mPopulation = population;
	    mBitmap = bitmap;
		mX = x;
		mY = y;
	}
	
	public Bitmap getBitmap() {
		return mBitmap;
	}
	public void setBitmap(Bitmap bitmap) {
	    mBitmap = bitmap;
	}
	public void setDown(int downX, int downY) {
	    mDownX = downX;
	    mDownY = downY;
	}
	public void setUp(int upX, int upY) {
        mUpX = upX;
        mUpY = upY;
    }
	public void setPosition() {
	    Log.d("beecub", mX + " | " + mY);
	    
	    if(mX > 0) {
            mX = 0;
            mUpX = 0;
            mDownX = 0;
	    }
	    if(mY > 0) {
            mY = 0;
            mUpY = 0;
            mDownY = 0;
	    }
	    if(mX < (MainGamePanel.mCanvasWidth - mBitmap.getWidth())) {
	        mX = (MainGamePanel.mCanvasWidth - mBitmap.getWidth());
            mUpX = (MainGamePanel.mCanvasWidth - mBitmap.getWidth());
            mDownX = (MainGamePanel.mCanvasWidth - mBitmap.getWidth());
	    }	    
	    if(mY < (MainGamePanel.mCanvasHeight - mBitmap.getHeight())) {
            mY = (MainGamePanel.mCanvasHeight - mBitmap.getHeight());
            mUpY = (MainGamePanel.mCanvasHeight - mBitmap.getHeight());
            mDownY = (MainGamePanel.mCanvasHeight - mBitmap.getHeight());
        }
	    
        mX += mUpX - mDownX;
        mY += mUpY - mDownY;
	}

	public boolean isTouched() {
		return mTouched;
	}

	public void setTouched(boolean touched) {
		mTouched = touched;
	}

	public void draw(Canvas canvas) {	    
		canvas.drawBitmap(mBitmap, mX, mY, null);
	}
	
	public void infect() {
	    mInfected = true;
	}
	
	public void update(long last) {
	    long difference = new Date().getTime() - last;
	    
	    if(mInfected) {
    	    if(mInfectedPopulation != mPopulation) {
    	        mInfectedPopulation += (mPopulation - mInfectedPopulation) * MainGamePanel.mDeadliness;
    	    }
	    }
	}
}


//float[] colorTransform = {
//        0, 1f, 0, 0, 0, 
//        0, 0, 0f, 0, 0,
//        0, 0, 0, 0f, 0, 
//        0, 0, 0, 1f, 0};
//
//ColorMatrix colorMatrix = new ColorMatrix();
//colorMatrix.setSaturation(0f); //Remove Colour 
//colorMatrix.set(colorTransform); //Apply the Red
//
//ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
//Paint paint = new Paint();
//paint.setColorFilter(colorFilter);   
//
//Bitmap resultBitmap = Bitmap.createBitmap(this.bitmap, 0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());            
//
//canvas.drawBitmap(resultBitmap, x, y, paint);

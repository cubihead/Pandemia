/**
 * 
 */
package com.beecub.games.pandemic.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.beecub.games.pandemic.MainGamePanel;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 * 
 * @author impaler
 *
 */
public class WorldMap {

	private Bitmap bitmap;	// the actual bitmap
	private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private int downX;
	private int upX;
	private int downY;
    private int upY;
	private boolean touched;
	
	public WorldMap(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public void setDown(int downX, int downY) {
	    this.downX = downX;
	    this.downY = downY;
	}
	public void setUp(int upX, int upY) {
        this.upX = upX;
        this.upY = upY;
    }
	public void setPosition() {
	    Log.d("beecub", this.x + " | " + this.y);
	    
	    if(this.x > 0) {
            this.x = 0;
            this.upX = 0;
            this.downX = 0;
	    }
	    if(this.y > 0) {
            this.y = 0;
            this.upY = 0;
            this.downY = 0;
	    }
	    if(this.x < (MainGamePanel.mCanvasWidth - this.bitmap.getWidth())) {
	        this.x = (MainGamePanel.mCanvasWidth - this.bitmap.getWidth());
            this.upX = (MainGamePanel.mCanvasWidth - this.bitmap.getWidth());
            this.downX = (MainGamePanel.mCanvasWidth - this.bitmap.getWidth());
	    }	    
	    if(this.y < (MainGamePanel.mCanvasHeight - this.bitmap.getHeight())) {
            this.y = (MainGamePanel.mCanvasHeight - this.bitmap.getHeight());
            this.upY = (MainGamePanel.mCanvasHeight - this.bitmap.getHeight());
            this.downY = (MainGamePanel.mCanvasHeight - this.bitmap.getHeight());
        }
	    
        this.x += this.upX - this.downX;
        this.y += this.upY - this.downY;
	    
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x, y, null);
	}

	public void update() {
		if (!touched) {
		    
		}
	}
}

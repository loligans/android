package com.ethan.Game.tapthat.gameactivity;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;

public class TapOut extends SurfaceView implements SurfaceHolder.Callback{
	private boolean		   		mSurfaceCreated = false;
	private boolean				mGameStarted = false;
	private int			   		mDisplayX;
	private int			   	    mDisplayY;
	private int		  	   		mScaledX;
	private int 			   	mScaledY;
	private int[]           	mImageMatrix = new int[4];
	private int			   		mMaximumSquares = 50;
	private long				mTime;
	private Paint   		   	mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private SurfaceHolder   	mSurfaceHolder;
	private GestureDetector 	mGestureDetector;
	private Context 		   	mApplicationContext;
	private Bitmap 		   		mRedSquareBitmap;
	private Bitmap   			mImageBackground;
	private static Random 		mRandom;
	
	public TapOut(Context context){
		super(context);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mSurfaceCreated = true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void updateView(){
		if(mSurfaceCreated){
			Canvas mCanvas = mSurfaceHolder.lockCanvas();
			//Drawing code goes here
			mCanvas.drawBitmap(mImageBackground, 0, 0, mPaint);
			for(int x=0;x<4;x++){
				mCanvas.drawBitmap(mRedSquareBitmap, mScaledX * (mImageMatrix[x] - 1), mScaledY*(3-x), mPaint);
			}
			mSurfaceHolder.unlockCanvasAndPost(mCanvas);
		}
	}
	
	private void regenerateMatrix(){
		if(mGameStarted == false){
			mGameStarted = true;
			mTime = System.currentTimeMillis();
		}
		try{
			if(mMaximumSquares < 4){
				for(int x = 0;x<3;x++){
					mImageMatrix[x] = mImageMatrix[x+1];
				}
				mImageMatrix[3] = 0;
				mMaximumSquares--;
			}else{
				for(int x=0;x<3;x++){
					mImageMatrix[x] = mImageMatrix[x+1];
				}
				mImageMatrix[3] = mRandom.nextInt(4) + 1;
				mMaximumSquares--;
			}
		}finally{
			updateView();
		}
		if(mMaximumSquares == -1){
			mTime = System.currentTimeMillis()-mTime;
			//loadBestTimesUI(true);
		}
	}
	
	
	/**
	 * Sets up the {@code GestureDetector} that will be used for registering tap events.
	 * <p>When a tap event occurs the code only registers the column it resides on the display
	 * is the column is not == the red squares column, they lose.
	 * @param gestureDetectorReference The passed in {@code GestureDetector reference}
	 * @return the new {@code GestureDetector}
	 */
	private GestureDetector createGestureDetector(GestureDetector gestureDetectorReference){
		GestureDetector GD = gestureDetectorReference;
		GD = new GestureDetector(mApplicationContext, new OnGestureListener() {
			
			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
			
			@Override
			public void onShowPress(MotionEvent e) {
				
			}
			
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
					float distanceY) {
				return false;
			}
			
			@Override
			public void onLongPress(MotionEvent e) {
				
			}
			
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
					float velocityY) {
				return false;
			}
			
			@Override
			public boolean onDown(MotionEvent e) {
				int rawX = (int) e.getRawX();
				//Log.d("Touch Event", "Raw X = " + rawX);
				if(rawX < mScaledX){
					//Log.d("Touch Event", "Column 1");
					if(mImageMatrix[0] == 1){
						regenerateMatrix();
					}else{
						//loadBestTimesUI(false);
					}
					return true;
				}else if(rawX < mScaledX * 2){
					//Log.d("Touch Event", "Column 2");
					if(mImageMatrix[0] == 2){
						//Consumed
						regenerateMatrix();
					}else{
						//loadBestTimesUI(false);
					}
					return true;
				}else if(rawX < mScaledX* 3){
					//Log.d("Touch Event", "Column 3");
					if(mImageMatrix[0] == 3){
						//Consumed
						regenerateMatrix();
					}else{
						//loadBestTimesUI(false);
					}
					return true;
				}else if(rawX > mScaledX * 3){
					//Log.d("Touch Event", "Column 4");
					if(mImageMatrix[0] == 4){
						//Consumed
						regenerateMatrix();
					}else{
						//loadBestTimesUI(false);
					}
					return true;
				}
				return true;
			}
		});
		return GD;
	}
}

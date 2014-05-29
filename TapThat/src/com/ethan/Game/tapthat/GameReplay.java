package com.ethan.Game.tapthat;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameReplay extends SurfaceView implements Runnable, SurfaceHolder.Callback{
	public static Bitmap		 mTileImage = null;
	public static Bitmap 		 mBackgroundImage = null;
	private static Paint   		 mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static SurfaceHolder mSurfaceHolder;
	private static Random		 mRandom = new Random();
	private static long			 mHighScore;
	private static int 			 mScaledReplayX;
	private static int 			 mScaledReplayY;
	private int[]           	 mImageMatrix = new int[4];
	private int					 mMaximumSquares = 50;
	private boolean		 		 mThreadRunning = false;
	private	boolean				 mSurfaceCreated = false;
	
	
	
	/**
	 * Passes in the Tile Images to be used
	 * @param highScore 
	 */
	public void initGameReplay(long highScore){
		mHighScore = highScore/50;
	}
	private void loadMatrix(){
		for(int i=0;i<4;i++){
			mImageMatrix[i] = mRandom.nextInt(4) + 1;
		}
	}
	
	public GameReplay(Context context, AttributeSet attributeSet){
		super(context, attributeSet);
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mPaint.setColor(Color.YELLOW);
		mPaint.setStyle(Style.FILL);
		Log.v("GameReplay", "Finished Loading Constructor");
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//Safety precaution to draw on a surface that is not yet available.
		mSurfaceCreated = true;
		Log.v("GameReplay", "Surface Created");
		loadMatrix();
		updateView();
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSurfaceCreated = false;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(oldw == 0 || oldh == 0){
			mScaledReplayX = w/4;
			mScaledReplayY = h/4;
			Log.v("GameReplay", "Surface Area has changed X = " + w + " | Y = " + h);
			//TODO Change scaled already if new image is selected
			mTileImage = Bitmap.createScaledBitmap(mTileImage, w/4, h/4, false);
			mBackgroundImage = Bitmap.createScaledBitmap(mBackgroundImage, w, h, false);
		}
	}
	@Override
	public void run() {
		mThreadRunning = true;
		mMaximumSquares = 50;
		Log.v("Thread", "Launching Thread");
		while(mThreadRunning){
			regenerateMatrix();
			updateView();
			sleepThread(mHighScore);
		}
	}
	private void regenerateMatrix(){
		Log.d("GameReplay", "Regenerating Matrix");
		if(mMaximumSquares < 4){
			for(int x = 0;x<3;x++){
				mImageMatrix[x] = mImageMatrix[x+1];
			}
			mImageMatrix[3] = 0;
			mMaximumSquares--;
			if(mMaximumSquares == -1){
				updateView();
				mMaximumSquares = 50;
				Log.d("GameReplay", "Sleeping 1500ms");
				sleepThread(1500);
				loadMatrix();
				sleepThread(mHighScore);
			}
		}else{
			for(int x=0;x<3;x++){
				mImageMatrix[x] = mImageMatrix[x+1];
			}
			mImageMatrix[3] = mRandom.nextInt(4) + 1;
			mMaximumSquares--;
		}
	}
	private void updateView(){
		try{
			if(mSurfaceCreated){
				Canvas mCanvas = mSurfaceHolder.lockCanvas();
				//Drawing code goes here
				mCanvas.drawBitmap(mBackgroundImage, 0, 0, mPaint);
				for(int i=1;i<5;i++){
					mCanvas.drawBitmap(mTileImage, mScaledReplayX*(mImageMatrix[i-1]-1 ), mScaledReplayY*(4-i), mPaint);
				}
				mSurfaceHolder.unlockCanvasAndPost(mCanvas);
			}
		}catch(NullPointerException error){
			Log.e("UpdateView", "Null Pointer Exception");
		}
	}
	private void sleepThread(long duration){
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			Log.v("Sleeping Game Replay", "" + duration + "ms");
			e.printStackTrace();
		}
	}
	
	public void setThreadStatus(boolean newStatus){
		mThreadRunning = newStatus;
	}
}

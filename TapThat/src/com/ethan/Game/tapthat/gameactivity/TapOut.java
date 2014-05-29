package com.ethan.Game.tapthat.gameactivity;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.OnGestureListener;

public class TapOut extends SurfaceView implements SurfaceHolder.Callback{
	public static Bitmap 		mTileBitmap = null;
	public static Bitmap   		mBackgroundBitmap = null;
	private boolean		   		mSurfaceCreated = false;
	private boolean				mGameStarted = false;
	private boolean 			mScaledImages = false;
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
	private static Random 		mRandom = new Random();
	private Game 				mGameInstance;
	
	public TapOut(Context context, Game game){
		super(context);
		mApplicationContext = context;
		mGameInstance = game;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mGestureDetector = createGestureDetector(mGestureDetector);
	}
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mSurfaceCreated = true;
		for(int i=0;i<4;i++){
			mImageMatrix[i] = mRandom.nextInt(4)+1;
		}
		updateView();
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
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mScaledX = w/4;
		mScaledY = h/4;
			mTileBitmap = Bitmap.createScaledBitmap(mTileBitmap, w/4, h/4, false);
			mBackgroundBitmap = Bitmap.createScaledBitmap(mBackgroundBitmap, w, h, false);
	}
	
	private void updateView(){
		if(mSurfaceCreated){
			Canvas mCanvas = mSurfaceHolder.lockCanvas();
			//Drawing code goes here
			mCanvas.drawBitmap(mBackgroundBitmap, 0, 0, mPaint);
			for(int x=0;x<4;x++){
				mCanvas.drawBitmap(mTileBitmap, mScaledX * (mImageMatrix[x] - 1), mScaledY*(3-x), mPaint);
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
			finishGame(mTime);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
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
				Log.d("Touch Event", "Raw X = " + rawX);
				if(rawX < mScaledX){
					//Log.d("Touch Event", "Column 1");
					if(mImageMatrix[0] == 1){
						regenerateMatrix();
					}else{
						finishGame(0);
					}
					return true;
				}else if(rawX < mScaledX * 2){
					//Log.d("Touch Event", "Column 2");
					if(mImageMatrix[0] == 2){
						//Consumed
						regenerateMatrix();
					}else{
						finishGame(0);
					}
					return true;
				}else if(rawX < mScaledX* 3){
					//Log.d("Touch Event", "Column 3");
					if(mImageMatrix[0] == 3){
						//Consumed
						regenerateMatrix();
					}else{
						finishGame(0);
					}
					return true;
				}else if(rawX > mScaledX * 3){
					//Log.d("Touch Event", "Column 4");
					if(mImageMatrix[0] == 4){
						//Consumed
						regenerateMatrix();
					}else{
						finishGame(0);
					}
					return true;
				}
				return true;
			}
		});
		return GD;
	}
	
	private void finishGame(long time){
		mGameInstance.finishGameActivity(time);
	}
}

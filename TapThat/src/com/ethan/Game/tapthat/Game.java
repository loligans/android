package com.ethan.Game.tapthat;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends SurfaceView implements Runnable, SurfaceHolder.Callback{
	private static boolean 		 mThreadRunning = false;
	private static boolean		 mSurfaceCreated = false;
	private static Paint   		 mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static SurfaceHolder mSurfaceHolder;
	private static Context 		 mApplicationContext;
	public Game(Context context) {
		super(context);
		mApplicationContext = context;
		mSurfaceHolder = getHolder();
		mSurfaceHolder.addCallback(this);
		mPaint.setColor(Color.MAGENTA);
		mPaint.setStyle(Style.FILL);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//Safety precaution to draw on a surface that is not yet available.
		mSurfaceCreated = true;
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	public void run() {
		mThreadRunning = true;
		while(mThreadRunning){
			updateView();
		}
	}
	
	private void updateView(){
		if(mSurfaceCreated){
			Canvas mCanvas = mSurfaceHolder.lockCanvas();
			//Drawing code goes here
			mSurfaceHolder.unlockCanvasAndPost(mCanvas);
		}
	}
}

package com.ethan.Game.tapthat.gameactivity;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game extends Activity{
	public static Bitmap		 mTileImage = null;
	public static Bitmap 		 mBackgroundImage = null;
	private static boolean 		 mThreadRunning = false;
	private static boolean		 mSurfaceCreated = false;
	private static Paint   		 mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static SurfaceHolder mSurfaceHolder;
	private static Context 		 mApplicationContext;
	private int 				 mGameMode = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onBackPressed() {}
}

package com.ethan.Game.tapthat.gameactivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;

public class Game extends Activity{
	public static Bitmap		 mTileImage = null;
	public static Bitmap 		 mBackgroundImage = null;
	private SurfaceHolder 		 mSurfaceHolder;
	private boolean				 mSurfaceCreated = false;
	private int 				 mGameMode = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//getIntent().getIntExtra("game_mode", 1);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onBackPressed() 
	{
		Log.d("Debug", "OR here");
		finishGameActivity();
		
		return;
	}
	private void finishGameActivity(){
		Intent data = new Intent();
		data.putExtra("score", 9500);
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, data);
		} else {
		    getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}
}

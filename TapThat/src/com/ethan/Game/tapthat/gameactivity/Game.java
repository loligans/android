package com.ethan.Game.tapthat.gameactivity;
import com.ethan.Game.tapthat.globals.Globals;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;

public class Game extends Activity{
	public static Bitmap		 mTileImage = null;
	public static Bitmap 		 mBackgroundImage = null;
	private int 				 mGameMode = 0;
	private Globals 			 mGlobalVar;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		mGameMode = getIntent().getIntExtra("game_mode", 1);
		Log.d("Received Intent", "Game Mode = " + mGameMode);
		switch (mGameMode) {
		case 1:
			Log.d("Game", "Starting Tap Out");
			setContentView(new TapOut(getApplicationContext(), Game.this));
			break;
		}
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		finishGameActivity(0);
		super.onPause();
	}
	
	@Override
	public void onBackPressed() 
	{
		Log.d("Debug", "OR here");
		finishGameActivity(0);
		
		return;
	}
	public void finishGameActivity(long score){
		Intent data = new Intent();
		data.putExtra("score", score);
		if (getParent() == null) {
		    setResult(Activity.RESULT_OK, data);
		} else {
		    getParent().setResult(Activity.RESULT_OK, data);
		}
		finish();
	}
}

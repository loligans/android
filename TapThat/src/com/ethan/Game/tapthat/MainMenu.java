package com.ethan.Game.tapthat;

import java.util.logging.Logger;

import com.ethan.Game.tapthat.globals.Globals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;

public class MainMenu extends Activity{
	private GameReplay replay;
	private OptionsMenu opts;
	private Globals globalVar;
	private View main_Menu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		globalVar = (Globals)getApplicationContext();
		initGame();
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		new Thread(replay).start();
		super.onResume();
	}
	protected void initGame(){
		main_Menu = getLayoutInflater().inflate(R.layout.activity_startup_menu, null);
		setContentView(main_Menu);
		setupGameReplayView();
		opts = new OptionsMenu(getApplicationContext());
		setupButtons();
	}
	private void setupGameReplayView(){
		replay = (GameReplay) findViewById(R.id.surfaceView1);
		replay.initGameReplay(Long.parseLong(globalVar.getUserHighScoreData().getString("0", "9000")));
	}
	/**
	 * Finds the users previous tile and background image
	 * If there is none set, it uses the default
	 * @param switchCase 1 = getBackgroundImage <br><b>switchCase</b> 2 = getTileImage
	 * @return returns the bitmap in the particular array
	 */
	private Bitmap selectDefaultImages(int switchCase){
		Bitmap returnedImage = null;
		switch (switchCase) {
		//case background
		case 1:
			int userBackground = Integer.parseInt(globalVar.getUserPrefData().getString("0", "1"));
			if(userBackground < 2){
				//Default Background Selected
				GameReplay.mBackgroundImage = globalVar.getDefaultGameImages()[userBackground];
			}else{
				GameReplay.mBackgroundImage = globalVar.getImportedBackgroundImages()[userBackground-2];
			}
			break;
		//case tile
		case 2:
			int userTile = Integer.parseInt(globalVar.getUserPrefData().getString("1", "1"));
			if(userTile < 2){
				//Default Tile Selected
				GameReplay.mTileImage = globalVar.getDefaultGameImages()[2+userTile];
			}else{
				GameReplay.mTileImage = globalVar.getImportedTileImages()[userTile-2];
			}
			break;
		}
		return returnedImage;
	}
	/**
	 * Initializes all the buttons
	 */
	private void setupButtons(){
		//initialize the Buttons
		Button startTapOut = (Button)findViewById(R.id.menu_start_tap_out);
		Button startTimeCr = (Button)findViewById(R.id.menu_start_time_crunch);
		Button highScoresB = (Button)findViewById(R.id.menu_highscores_button);
		Button optionsMenu = (Button)findViewById(R.id.menu_start_options);
		//Attach listeners look at methods 
		//just below this class to change 
		//listener Behavior
		startTapOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startTapOut();
			}
		});
		startTimeCr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startTimeCrunch();
			}
		});
		highScoresB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadHighScores();
			}
		});	
		optionsMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				loadOptionsMenu();
			}
		});
	}
	//The methods that will be called when a button is pressed
	private void startTapOut(){
		Log.v("MainMenu", "Tap Out Button");
		replay.setThreadStatus(false);
		currentView = 3;
		
	}
	private void startTimeCrunch(){
		Log.v("MainMenu", "Time Crunch Button");
	}
	private void loadHighScores(){
		Log.v("MainMenu", "High Scores Button");
		replay.setThreadStatus(false);
		setContentView(R.layout.best_times);
		currentView = 1;
	}
	private void loadOptionsMenu(){
		Log.v("MainMenu", "Options Menu Button");
		replay.setThreadStatus(false);
		currentView = 2;
		setContentView(R.layout.options_menu);
	}
	/**
	 * 0 = main menu
	 * 1 = high scores menu
	 * 2 = options menu
	 * 3 = in game
	 */
	private int currentView = 0;
	@Override
	public void onBackPressed() {
		Log.d("MainMenu", "Back Pressed");
		switch(currentView){
			case 0:
				super.onBackPressed();
				break;
			case 1:
				setContentView(main_Menu);
				onResume();
				currentView = 0;
				break;
			case 2:
				setContentView(main_Menu);
				onResume();
				currentView = 0;
				break;
			case 3:
				Log.i("In Game", "Preventing back press");
		}
	}
	@Override
	protected void onPause() {
		replay.setThreadStatus(false);
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}
	
}

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
	private Bitmap[] mDefaultGameImages;
	private Bitmap[] mImportedBackgroundImages;
	private Bitmap[] mImportedTileImages;
	/**
	 * 0-5 contain the highScores
	 */
	private SharedPreferences mUserHighScoreData;
	/**
	 * User preferences stored here
	 * 0 = Stores the Currently Set Background Image
	 * 1 = Stored the Currently Set Tile Image
	 */
	private SharedPreferences mUserPrefData;
	/**
	 * Imported Background image Locations are stored here
	 */
	private SharedPreferences mUserBackgroundImageImportsData;
	/**
	 * Imported Tile Image Locations are stored here
	 */
	private SharedPreferences mUserTileImageImportsData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Globals globalVar = (Globals) getApplicationContext();
		mDefaultGameImages = globalVar.getDefaultGameImages();
		mImportedBackgroundImages = globalVar.getImportedBackgroundImages();
		mImportedTileImages = globalVar.getImportedTileImages();
		mUserHighScoreData = globalVar.getUserHighScoreData();
		mUserBackgroundImageImportsData = globalVar.getUserBackgroundImageImportsData();
		mUserTileImageImportsData = globalVar.getUserTileImageImportsData();
		initGame();
		super.onCreate(savedInstanceState);
	}
	protected void initGame(){
		View main_Menu = getLayoutInflater().inflate(R.layout.activity_startup_menu, null);
		setContentView(main_Menu);
		setupGameReplayView();
		opts = new OptionsMenu(getApplicationContext());
		setupButtons();
		new Thread(replay).start();
	}
	private void setupGameReplayView(){
		replay = (GameReplay) findViewById(R.id.surfaceView1);
		replay.initGameReplay(Long.parseLong(mUserHighScoreData.getString("0", "9000")));
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
			int userBackground = Integer.parseInt(mUserPrefData.getString("0", "1"));
			if(userBackground < 2){
				//Default Background Selected
				returnedImage = mDefaultGameImages[userBackground];
			}else{
				returnedImage = mImportedBackgroundImages[userBackground-2];
			}
			break;
		//case tile
		case 2:
			int userTile = Integer.parseInt(mUserPrefData.getString("1", "1"));
			if(userTile < 2){
				//Default Tile Selected
				returnedImage = mDefaultGameImages[2+userTile];
			}else{
				returnedImage = mImportedTileImages[userTile-2];
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
	}
	private void startTimeCrunch(){
		Log.v("MainMenu", "Time Crunch Button");
	}
	private void loadHighScores(){
		Log.v("MainMenu", "High Scores Button");
	}
	private void loadOptionsMenu(){
		Log.v("MainMenu", "Options Menu Button");
		AppEntrance.viewableUI = 2;
		setContentView(R.layout.options_menu);
	}
	
}

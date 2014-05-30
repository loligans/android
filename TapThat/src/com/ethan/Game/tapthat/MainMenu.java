package com.ethan.Game.tapthat;
import com.ethan.Game.tapthat.gameactivity.Game;
import com.ethan.Game.tapthat.globals.Globals;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity{
	private GameReplay mReplay;
	private Globals mGlobalVar;
	private MenuLoader mViewLoader;
	private View main_Menu;
	private int currentView = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGlobalVar = (Globals)getApplicationContext();
		mViewLoader = new MenuLoader(MainMenu.this);
		initGame();
		super.onCreate(savedInstanceState);
	}
	
	protected void initGame(){
		main_Menu = getLayoutInflater().inflate(R.layout.activity_startup_menu, null);
		setContentView(main_Menu);
		mReplay = (GameReplay) findViewById(R.id.surfaceView1);
		mReplay.initGameReplay(Long.parseLong(mGlobalVar.getUserHighScoreData().getString("0", "9000")));
		setupButtons();
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
			int userBackground = Integer.parseInt(mGlobalVar.getUserPrefData().getString("0", "1"));
			if(userBackground < 2){
				//Default Background Selected
				GameReplay.mBackgroundImage = mGlobalVar.getDefaultGameImages()[userBackground];
			}else{
				GameReplay.mBackgroundImage = mGlobalVar.getImportedBackgroundImages()[userBackground-2];
			}
			break;
		//case tile
		case 2:
			int userTile = Integer.parseInt(mGlobalVar.getUserPrefData().getString("1", "1"));
			if(userTile < 2){
				//Default Tile Selected
				GameReplay.mTileImage = mGlobalVar.getDefaultGameImages()[2+userTile];
			}else{
				GameReplay.mTileImage = mGlobalVar.getImportedTileImages()[userTile-2];
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
		mReplay.setThreadStatus(false);
		Intent tapOut = new Intent(getApplicationContext(), Game.class);
		tapOut.putExtra("game_mode", 1);
		startActivityForResult(tapOut, 1);
	}
	private void startTimeCrunch(){
		Log.v("MainMenu", "Time Crunch Button");
		mReplay.setThreadStatus(false);
		Intent tapOut = new Intent(getApplicationContext(), Game.class);
		tapOut.putExtra("game_mode", 2);
		startActivityForResult(tapOut, 2);
	}
	private void loadHighScores(){
		Log.v("MainMenu", "High Scores Button");
		mReplay.setThreadStatus(false);
		mViewLoader.loadHighscoreMenu();
		currentView = 1;
	}
	private void loadOptionsMenu(){
		Log.v("MainMenu", "Options Menu Button");
		mReplay.setThreadStatus(false);
		mViewLoader.loadOptionsMenu();
		currentView = 2;
	}
	@Override
	protected void onResume() {
		if(currentView == 0){
			new Thread(mReplay).start();
		} 
		super.onResume();
	}
	
	/**
	 * 0 = main menu
	 * 1 = high scores menu
	 * 2 = options menu
	 * 3 = in game
	 */
	
	@Override
	public void onBackPressed() {
		Log.d("MainMenu", "Back Pressed");
		switch(currentView){
			case 0:
				super.onBackPressed();
				break;
			default:
				setContentView(main_Menu);
				currentView = 0;
				onResume();
				break;
		}
	}
	@Override
	protected void onPause() {
		mReplay.setThreadStatus(false);
		super.onPause();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode){
		case 1:
			if(resultCode == Activity.RESULT_OK){
				//Show high scores
				long score = data.getLongExtra("score", 0);
				Log.i("Activity Result Tap That", "Time = " + score);
				if(score != 0){
					//Succeeded
					updateHighScores(score);
					mViewLoader.loadEndGameMenu(true, 1, score);
					currentView = 3;
				}else{
					//failed
					mViewLoader.loadEndGameMenu(false, 1, score);
					currentView = 3;
				}
			}else{
				//Game Stopped some how
			}
			break;
		case 2:
			if(resultCode == Activity.RESULT_OK){
				long score = data.getLongExtra("score", 0);
				Log.i("Activity Result Time Crunch", "Time = " + score);
			}else{
				//Game Stopped some how
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void updateHighScores(long time){
		
		
	}
	
}

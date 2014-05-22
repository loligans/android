package com.ethan.Game.tapthat;
import com.ethan.Game.tapthat.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
public class AppEntrance extends Activity{
	/**
	 * 0 = splash screen <br>
	 * 1 = main menu <br>
	 * 2 = options menu <br>
	 * 3 = highscores menu <br>
	 * 4 = tapout <br>
	 * 5 = time crunch
	 */
	public static int viewableUI = 0;
	public static View main_Menu = null;
	/**
	 * 0-5 contain the highScores
	 */
	private static SharedPreferences highScoreData;
	/**
	 * User preferences stored here
	 */
	private static SharedPreferences userPrefData;
	/**
	 * User Background Imports are stored here
	 */
	private static SharedPreferences userBackgroundImageImportsData;
	/**
	 * User Tile Imports are stored here
	 */
	private static SharedPreferences userTileImageImportsData;
	//Keys Associated with the preferences
	private static final String highScoreKey = "com.ethan.Game.TapThat.highScore";
	private static final String userPrefKey = "com.ethan.Game.TapThat.userPreferences";
	private static final String userBackgroundImageImportsKey = "com.ethan.Game.TapThat.userBackgroundImageImports";
	private static final String userTileImageImportsKey = "com.ethan.Game.TapThat.userTileImageImports";
	//File Key Array
	private static String[] imageBackgroundsToLoadFromFile;
	private static String[] imageTilesToLoadFromFile;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		//Loads the Preferences
		
		loadPreferences();
		//prepares to load the imported images
		prepareImportedImages();
		
		/**
		 * This method passes all the loaded data into
		 * the {@code AsyncTask} so that it may be passed into the
		 * {@code MainMenu}
		 */
		LoadImagesAsyncHelper imageAsyncLoader = new LoadImagesAsyncHelper(getApplicationContext(), AppEntrance.this, 
				highScoreData, userPrefData, userBackgroundImageImportsData, userTileImageImportsData,
				imageBackgroundsToLoadFromFile, imageTilesToLoadFromFile);
		//Execute the AsyncTask
		imageAsyncLoader.execute(R.raw.lava_background, R.raw.water_background, R.raw.lava_tile, R.raw.water_tile);
	}
	
	/**
	 * Instantiates the activity's preferences
	 */
	private void loadPreferences(){
		highScoreData = getSharedPreferences(highScoreKey, Context.MODE_PRIVATE);
		userPrefData = getSharedPreferences(userPrefKey, Context.MODE_PRIVATE);
		userBackgroundImageImportsData = getSharedPreferences(userBackgroundImageImportsKey, Context.MODE_PRIVATE);
		userTileImageImportsData = getSharedPreferences(userTileImageImportsKey, Context.MODE_PRIVATE);
	}
	/**
	 * Gathers the File keys of the imported images
	 * The keys are stored numerically so the images can
	 * be loaded via a loop
	 */
	private void prepareImportedImages(){
		//Loads background File Locations
		if(userBackgroundImageImportsData != null && userBackgroundImageImportsData.getAll().size() != 0){
			//NOB = Number Of Backgrounds
			Log.d("Load File Locations", "The Backgrounds Are Valid");
			int NOB = userBackgroundImageImportsData.getAll().size();
			imageBackgroundsToLoadFromFile = new String[NOB];
			for(int i = 0;i < NOB;i++){
				imageBackgroundsToLoadFromFile[i] = userBackgroundImageImportsData.getString(""+i, null);
			}
		}else{Log.d("Load File Locations", "The Backgrounds Are Null");}
		//Loads Tile File Locations
		if(userTileImageImportsData != null && userTileImageImportsData.getAll().size() != 0){
			//NOT = Number Of Tiles
			Log.d("Load File Locations", "The Tiles Are Valid");
			int NOT = userBackgroundImageImportsData.getAll().size();
			imageTilesToLoadFromFile = new String[NOT];
			for(int i = 0;i < NOT;i++){
				imageTilesToLoadFromFile[i] = userBackgroundImageImportsData.getString(""+i, null);
			}
		}else{Log.d("Load File Locations", "The Tiles Are Null");}
	}
	
}

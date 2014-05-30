package com.ethan.Game.tapthat;

import android.util.Log;
import android.view.View;

import com.ethan.Game.tapthat.globals.Globals;
import com.ethan.Game.tapthat.menus.GameFinishedMenu;
import com.ethan.Game.tapthat.menus.HighScoresMenu;
import com.ethan.Game.tapthat.menus.OptionsMenu;

public class MenuLoader {
	public int 				 mViewLocation;
	private MainMenu		 mMain;
	private Globals			 mGlobalVar;
	private GameFinishedMenu mGameMenu;		private View mGameView;
	private HighScoresMenu 	 mHighscoreMenu;private View mHighscoreView;
	private OptionsMenu		 mOptionsMenu;	private View mOptionsView;
	public MenuLoader(MainMenu appContext){
		mMain = appContext;
		mGlobalVar = (Globals)mMain.getApplicationContext();
		loadMenus();
	}
	private void loadMenus(){
		mGameView = mMain.getLayoutInflater().inflate(R.layout.best_times, null);
		mHighscoreView = mMain.getLayoutInflater().inflate(R.layout.highscores_layout, null);
		mOptionsView = mMain.getLayoutInflater().inflate(R.layout.options_menu, null);
	}
	public void loadEndGameMenu(boolean completed, int gameType, long score){
		Log.i("MenuLoader", "Loading End Game Menu");
		mMain.setContentView(mGameView);
		mGameMenu = new GameFinishedMenu(mMain, mGlobalVar, completed, gameType, score);
		mGameMenu.loadObjects();
	}
	public void loadHighscoreMenu(){
		Log.i("MenuLoader", "Loading High Score Menu");
		mMain.setContentView(mHighscoreView);
		mHighscoreMenu = new HighScoresMenu(mMain, mGlobalVar);
		mHighscoreMenu.loadObjects();
	}
	public void loadOptionsMenu(){
		Log.i("MenuLoader", "Loading Options Menu");
		mMain.setContentView(mOptionsView);
		mOptionsMenu = new OptionsMenu(mMain, mGlobalVar);
		mOptionsMenu.loadObjects();
	}
}

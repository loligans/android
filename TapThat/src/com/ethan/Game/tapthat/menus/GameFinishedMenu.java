package com.ethan.Game.tapthat.menus;

import java.text.NumberFormat;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

import com.ethan.Game.tapthat.MainMenu;
import com.ethan.Game.tapthat.R;
import com.ethan.Game.tapthat.gameactivity.Game;
import com.ethan.Game.tapthat.globals.Globals;

public class GameFinishedMenu {
	private boolean  mGameCompleted;
	private int		 mGameType;
	private MainMenu mAppContext;
	private Globals	 mGlobalVar;
	private long	 mCurrentTime;
	private TextView[] mTextViews = new TextView[6];
	public GameFinishedMenu(MainMenu appContext, Globals globalVar,boolean completed, int gameType, long score) {
		mAppContext = appContext;
		mGlobalVar = globalVar;
		mGameCompleted = completed;
		mGameType = gameType;
		mCurrentTime = score;
	}
	/**
	 * Loads all the objects and sets the textviews contents
	 */
	public void loadObjects(){
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(3);
		String newScore = nf.format((double)mCurrentTime/1000);
		mTextViews[0] = (TextView)mAppContext.findViewById(R.id.end_game_highscore1);
		mTextViews[1] = (TextView)mAppContext.findViewById(R.id.end_game_highscore2);
		mTextViews[2] = (TextView)mAppContext.findViewById(R.id.end_game_highscore3);
		mTextViews[3] = (TextView)mAppContext.findViewById(R.id.end_game_highscore4);
		mTextViews[4] = (TextView)mAppContext.findViewById(R.id.end_game_highscore5);
		mTextViews[5] = (TextView)mAppContext.findViewById(R.id.end_game_highscore6);
		Button endGameButton = (Button)mAppContext.findViewById(R.id.end_game_retry_button);
		TextView gameModeName = (TextView)mAppContext.findViewById(R.id.end_game_game_mode_name);
		TextView endGameScore = (TextView)mAppContext.findViewById(R.id.end_game_current_time);
		switch(mGameType){
		case 1:
			endGameButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent tapOut = new Intent(mAppContext.getApplicationContext(), Game.class);
					tapOut.putExtra("game_mode", 1);
					mAppContext.startActivityForResult(tapOut, 1);
				}
			});
			gameModeName.setText("Tap Out");
			if(!mGameCompleted){//if game lost, display reg score no speeed
				endGameScore.setText("you're outtie");
				long[] scores = mGlobalVar.getHighScores(1);
				for(int i=0; i<6;i++){
					mTextViews[i].setText(nf.format((double)scores[i]/1000));
				}
			}else{//game is won
				endGameScore.setText(newScore + "s");
				long[] scores = checkForNewHighscore(mCurrentTime, 1);
				for(int i=0; i<6;i++){
					mGlobalVar.getUserHighScoreData().edit().putString(""+i, ""+scores[i]).commit();
					mTextViews[i].setText(nf.format((double)scores[i]/1000));
				}
			}
			break;
		case 2:
			endGameButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent tapOut = new Intent(mAppContext.getApplicationContext(), Game.class);
					tapOut.putExtra("game_mode", 2);
					mAppContext.startActivityForResult(tapOut, 2);
				}
			});
			gameModeName.setText("Time Crunch");
			if(!mGameCompleted){
				
			}
			break;
		}
	}
	/**
	 * algorithm that checks for a new highscore
	 * @param currentScore the time from the users run
	 * @param gameType which game the user was playing
	 * @return the new highscores
	 */
	private long[] checkForNewHighscore(long currentScore, int gameType){
		long[] scores = mGlobalVar.getHighScores(gameType);
		long[] newScores = new long[6];
		boolean newHScoreFound = false;
		for(int i=0;i<6;i++){
			if(newHScoreFound == false){
				if(mCurrentTime < scores[i] && mCurrentTime != 0 || mCurrentTime != 0 && scores[i] == 0){
					newScores[i] = mCurrentTime;
					newHScoreFound = true;
				}else{
					newScores[i] = scores[i];
				}
			}else{
				newScores[i] = scores[i-1];
			}
		}
		return newScores;
	}
	
}

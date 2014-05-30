package com.ethan.Game.tapthat.menus;

import java.text.NumberFormat;

import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.TextView;

import com.ethan.Game.tapthat.MainMenu;
import com.ethan.Game.tapthat.R;
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
			gameModeName.setText("Tap Out");
			if(!mGameCompleted){//if game lost, display reg score no speeed
				endGameScore.setText("you're outtie");
			}else{//game is won
				endGameScore.setText(newScore + "/s");
			}
			break;
		case 2:
			gameModeName.setText("Time Crunch");
			if(!mGameCompleted){
				
			}
			break;
		}
	}
}

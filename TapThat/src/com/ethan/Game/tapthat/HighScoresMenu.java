package com.ethan.Game.tapthat;

import java.text.NumberFormat;

import com.ethan.Game.tapthat.globals.Globals;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HighScoresMenu {
	private MainMenu mAppContext;
	private Globals	 mGlobalVar;
	private TextView[] tapHighScore = new TextView[6];
	private TextView[] timeHighScore = new TextView[6];
	private static boolean highScoresLoaded = false;
	public HighScoresMenu(MainMenu appContext, Globals globalVar){
		mAppContext = appContext;
		mGlobalVar = globalVar;
	}
	public void loadObjects(){
		Button tapOut = (Button)mAppContext.findViewById(R.id.highscore_menu_launch_tapout);
		Button timeCrunch = (Button)mAppContext.findViewById(R.id.highscore_menu_launch_timecrunch);
		tapHighScore[0] = (TextView)mAppContext.findViewById(R.id.tapout_highscore1);
		tapHighScore[1] = (TextView)mAppContext.findViewById(R.id.tapout_highscore2);
		tapHighScore[2] = (TextView)mAppContext.findViewById(R.id.tapout_highscore3);
		tapHighScore[3] = (TextView)mAppContext.findViewById(R.id.tapout_highscore4);
		tapHighScore[4] = (TextView)mAppContext.findViewById(R.id.tapout_highscore5);
		tapHighScore[5] = (TextView)mAppContext.findViewById(R.id.tapout_highscore6);
		timeHighScore[0] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore1);
		timeHighScore[1] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore2);
		timeHighScore[2] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore3);
		timeHighScore[3] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore4);
		timeHighScore[4] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore5);
		timeHighScore[5] = (TextView)mAppContext.findViewById(R.id.timecrunch_highscore6);
		tapOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("HighScores Menu", "Tap Out Pressed");
			}
		});
		timeCrunch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("HighScores Menu", "Time Crunch Pressed");
			}
		});
		loadHighScores();
	}
	private void loadHighScores(){
		Log.d("HighScores Menu", "Loading High Scores");
		double[] tapOutHighScores = new double[6];
		double[] timeCrunchHighScores = new double[6];
		for(int i=0;i<6;i++){
			tapOutHighScores[i] = Double.parseDouble(mGlobalVar.getUserHighScoreData().getString(""+i, "0"));
			timeCrunchHighScores[i] = Double.parseDouble(mGlobalVar.getUserHighScoreData().getString(""+(i+6), "0"));
		}
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(3);
		for(int i=0;i<6;i++){
			tapHighScore[i].setText(nf.format(tapOutHighScores[i]/1000));
			timeHighScore[i].setText(nf.format(timeCrunchHighScores[i]/1000));
		}
		Log.d("HighScores Menu", "High Scores Loaded Completely");
	}
}

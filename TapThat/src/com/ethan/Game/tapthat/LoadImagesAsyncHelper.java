package com.ethan.Game.tapthat;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import com.ethan.Game.tapthat.globals.Globals;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class LoadImagesAsyncHelper extends AsyncTask<Integer, Void, Bitmap[][]>{
	private WeakReference<Context> mAppContext;
	private String[] backgroundsImages;
	private String[] tileImages;
	public LoadImagesAsyncHelper(Context appContext, AppEntrance appActivity, SharedPreferences highScoreData,
			SharedPreferences userPrefData, SharedPreferences userBackgroundImageImportsData, SharedPreferences userTileImageImportsData,
			String[] imageBackgroundsToLoadFromFile, String[] imageTilesToLoadFromFile)
	{
		mAppContext = new WeakReference<Context>(appContext);
		Globals.mUserHighScoreData = highScoreData;
		Globals.mUserPrefData = userPrefData;
		Globals.mUserBackgroundImageImportsData = userBackgroundImageImportsData;
		Globals.mUserTileImageImportsData = userTileImageImportsData;
		backgroundsImages = imageBackgroundsToLoadFromFile;
		tileImages = imageTilesToLoadFromFile;
	}
	
	@Override
	protected Bitmap[][] doInBackground(Integer... params) 
	{
		Bitmap[] defaultImagesToLoad = new Bitmap[params.length];
		Bitmap[] userBackgroundImages = null;
		Bitmap[] userTileImages = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		for(int i=0;i<defaultImagesToLoad.length;i++){
			defaultImagesToLoad[i] = BitmapFactory.decodeResource(mAppContext.get().getResources(), params[i], opts);
		}
		if(backgroundsImages != null){
			opts.inTargetDensity = 1;//Phone Density
			userBackgroundImages = loadImagesFromFile(backgroundsImages.length, opts);
		}
		if(tileImages != null){
			userTileImages = loadImagesFromFile(tileImages.length, opts);
		}
		Bitmap[][] returnedBitmaps = new Bitmap[3][];
		returnedBitmaps[0] = defaultImagesToLoad;
		returnedBitmaps[1] = userBackgroundImages;
		returnedBitmaps[2] = userTileImages;
		return returnedBitmaps;
	}
	/**
	 * Loads the images from a private file only accessible to the application
	 * @param arraySize the size of the array if any
	 * @return the decoded images
	 */
	private Bitmap[] loadImagesFromFile(int arraySize, BitmapFactory.Options opts){
		//Creates a new bitmap array based on the size passed in
		Bitmap[] images = new Bitmap[arraySize];
		//instantiates the input stream
		FileInputStream fis = null;
		try{
			//runs through a loop to decode the stream
			for(int i=0;i<arraySize;i++){
				fis = mAppContext.get().openFileInput("" + i);
				images[i] = BitmapFactory.decodeStream(fis, null, opts);
			}
		}catch(IOException e){
			Log.wtf("IOEXCEPTION", "holy shit you done goof now");
		}finally{
			//finally close the stream of course
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//return the freshly decoded images
		return images;
	}
	
	@Override
	protected void onPostExecute(Bitmap[][] result) {
		super.onPostExecute(result);
		Globals.mDefaultGameImages = result[0];
		Globals.mImportedBackgroundImages = result[1]; 
		Globals.mImportedTileImages = result[2];
		Intent mainGame = new Intent(mAppContext.get(), MainMenu.class);
		mainGame.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mAppContext.get().startActivity(mainGame);
	}
	
}

package com.ethan.Game.tapthat.globals;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.app.Application;

public class Globals extends Application{
	private Bitmap[] mDefaultGameImages = null;
	private Bitmap[] mImportedBackgroundImages = null;
	private Bitmap[] mImportedTileImages = null;
	private SharedPreferences mUserHighScoreData = null;
	private SharedPreferences mUserPrefData = null;
	private SharedPreferences mUserBackgroundImageImportsData = null;
	private SharedPreferences mUserTileImageImportsData = null;
	
	public Bitmap[] getDefaultGameImages() {
		return mDefaultGameImages;
	}
	public void setDefaultGameImages(Bitmap[] mDefaultGameImages) {
		this.mDefaultGameImages = mDefaultGameImages;
	}
	public Bitmap[] getImportedBackgroundImages() {
		return mImportedBackgroundImages;
	}
	public void setImportedBackgroundImages(
			Bitmap[] mImportedBackgroundImages) {
		this.mImportedBackgroundImages = mImportedBackgroundImages;
	}
	public Bitmap[] getImportedTileImages() {
		return mImportedTileImages;
	}
	public void setImportedTileImages(Bitmap[] mImportedTileImages) {
		this.mImportedTileImages = mImportedTileImages;
	}
	public SharedPreferences getUserHighScoreData() {
		return mUserHighScoreData;
	}
	public void setUserHighScoreData(SharedPreferences mUserHighScoreData) {
		this.mUserHighScoreData = mUserHighScoreData;
	}
	public SharedPreferences getUserPrefData() {
		return mUserPrefData;
	}
	public void setUserPrefData(SharedPreferences mUserPrefData) {
		this.mUserPrefData = mUserPrefData;
	}
	public SharedPreferences getUserBackgroundImageImportsData() {
		return mUserBackgroundImageImportsData;
	}
	public void setUserBackgroundImageImportsData(
			SharedPreferences mUserBackgroundImageImportsData) {
		this.mUserBackgroundImageImportsData = mUserBackgroundImageImportsData;
	}
	public SharedPreferences getUserTileImageImportsData() {
		return mUserTileImageImportsData;
	}
	public void setUserTileImageImportsData(
			SharedPreferences mUserTileImageImportsData) {
		this.mUserTileImageImportsData = mUserTileImageImportsData;
	}
	
}

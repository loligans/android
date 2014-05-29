package com.ethan.Game.tapthat.menus;

import com.ethan.Game.tapthat.MainMenu;
import com.ethan.Game.tapthat.globals.Globals;

public class OptionsMenu {
	private MainMenu mAppContext;
	private Globals	mGlobalVar;
	public OptionsMenu(MainMenu mainContext, Globals globalVar){
		mAppContext = mainContext;
		mGlobalVar = globalVar;
	}
	public void loadObjects(){
		//Objects to load if any
	}
}

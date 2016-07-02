package com.mygdx.game;

import com.badlogic.gdx.Gdx;

public class Time {
	
	public static double time = 1.d;
	
	
	private static int defaultFPS = 60;
	
	public static void upDate() {
		
		int actualFPS = Gdx.graphics.getFramesPerSecond();
		
		actualFPS = (actualFPS == 0) ? 3000:actualFPS;
		
		time = (double) defaultFPS/actualFPS;
		
	}

}

package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class TextManager {
	
	private static BitmapFont bfont = new BitmapFont();
	private static SpriteBatch batcher;
	
	public static void setSpriteBatch(SpriteBatch batch) {
		batcher = batch;
	}
	
	public static void draw(CharSequence msg, OrthographicCamera cam) {
		Vector3 position = new Vector3(10, 20, 0);
		cam.unproject(position);
		bfont.draw(batcher, msg, position.x, position.y);
	}

}

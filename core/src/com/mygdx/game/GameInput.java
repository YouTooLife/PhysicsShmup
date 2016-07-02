package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class GameInput {
	
	public static Vector2 keyForce = new Vector2();
	
	public static void update() {
		keyForce.x = 0;
		if (Gdx.input.isKeyPressed(Keys.A)) {
			keyForce.x -= 1;
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			keyForce.x+=1;
		}
		keyForce.y = 0;
		if (Gdx.input.isKeyPressed(Keys.W)) {
			keyForce.y+=1;
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			keyForce.y-=1;
		}
	}

}

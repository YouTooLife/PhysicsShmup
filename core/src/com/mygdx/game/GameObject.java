package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.GameInput.keyForce;

public class GameObject {
	
	
	
	public float x = 0;
	public float y = 0;
	
	private Texture texture;
	private SpriteBatch spriteBatchHandle;
	
	public GameObject(String texture_path, SpriteBatch batch,float posX, float posY) {
		spriteBatchHandle = batch;
		texture = new Texture(Gdx.files.internal(texture_path));
		x = posX;
		y = posY;
	}
	
	public void update() {
		int speed = 5;
		x += keyForce.x*speed*Time.time;
		y += keyForce.y*speed*Time.time;
	}
	public void draw() {
		spriteBatchHandle.draw(texture, x, y);
		update();
	}

}

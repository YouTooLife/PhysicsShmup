package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player  extends Sprite {
	
	//private float momentum = 0;
	
	Vector2 gravityVector = new Vector2(0, -1);
	Vector2 jumpVector = new Vector2(0, 1);
	Vector2 momentum = new Vector2(0, 0);
	
	public Player(Texture texture, float x, float y) {
		this.setTexture(texture);
		this.setPosition(x, y);
	}
	
	public void update() {
		
		int speed = 5;
		float frames = Gdx.graphics.getFramesPerSecond();
		frames = (frames == 0) ? 60: frames;
		float gravity = 19.6f / frames;
		
		if (getY() <= 50) {
			momentum.y = 0;
		} else {
			momentum.y -= gravity;
		}
		
		if (momentum.y == 0 && Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			momentum.y = gravity * 20;
		}
		
		//momentum = Math.min(gravity * frames, momentum + gravity);
		momentum.x = GameInput.keyForce.x * speed;
		this.translateX(momentum.x);
		this.translateY(momentum.y);
		
	}
	@Override
	public void draw(Batch batch) {
		update();
		batch.draw(getTexture(), getX(), getY());
	}

}

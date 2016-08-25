package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet extends PhysicsActor {
	
	
	PhysicPlayer player;

	public Bullet(Texture texture, float x, float y, World world, PhysicPlayer player) {
		super(texture, world);
		setTexture(texture);
		setPosition(x, y);
		
		this.player = player;
		
	}

}

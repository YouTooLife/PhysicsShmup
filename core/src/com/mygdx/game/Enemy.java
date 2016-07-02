package com.mygdx.game;

import static com.mygdx.game.PhysicsManager.METERS_TO_PIXELS;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy extends PhysicsActor {

	private Body body;
	LevelCollisionGeneratorWorking collisionGenerator;
	
	public Enemy(Texture texture, float x, float y, World world) {
		
		super(texture, world);
		
		this.setPosition(x, y);
		this.setSize(50.f, 100.f);
		
		body = getHumanoidBody();
	}
	
	public Body getBody() {
		return body;
	}
	
	public void update() {

		setPosition(body.getPosition().x*METERS_TO_PIXELS-getWidth()/2, 
				body.getPosition().y*METERS_TO_PIXELS-0*getHeight()-15.f);
		
	}
	@Override
	public void draw(Batch batch) {
		update();
		super.draw(batch);
	}
}

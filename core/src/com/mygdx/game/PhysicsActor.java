package com.mygdx.game;

import static com.mygdx.game.PhysicsManager.PIXELS_TO_METERS;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsActor extends Sprite {
	
	protected Body body;
	protected World world;
	
	public PhysicsActor(Texture texture, World world) {
		super(texture);
		
		this.world = world;
	}
	
	public Body getHumanoidBody() {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(
				(getX()+getWidth()/2)*PIXELS_TO_METERS,
				(getY()+getHeight()/2)*PIXELS_TO_METERS);
		bodyDef.fixedRotation = true;
		
		body = world.createBody(bodyDef);
	
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(3*PIXELS_TO_METERS, 
				15f*PIXELS_TO_METERS,
				new Vector2(1.f*PIXELS_TO_METERS,
						(30f)*PIXELS_TO_METERS),0.0f);
		
		//fixtureDef.friction = 0.01f * PIXELS_TO_METERS;
		
		CircleShape footCirlce = new CircleShape();
		footCirlce.setRadius(15.f*PIXELS_TO_METERS);
		footCirlce.setPosition(new Vector2(1f*PIXELS_TO_METERS,
				1f*PIXELS_TO_METERS));
		
		CircleShape headCircle = new CircleShape();
		headCircle.setRadius(10.f*PIXELS_TO_METERS);
		headCircle.setPosition(new Vector2(1f*PIXELS_TO_METERS,
				(30f+25f)*PIXELS_TO_METERS));
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = footCirlce;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 15.f * PIXELS_TO_METERS;
		
		body.createFixture(shape,1.0f);
		body.createFixture(fixtureDef);
		body.createFixture(headCircle,1.0f);
		//FixtureDef fixtureDef = new FixtureDef();
		//fixtureDef.shape = shape;
		//fixtureDef.density = 1.0f;
		
		//Fixture fixture = body.createFixture(fixtureDef);
		
		System.out.println("s1");
		
		shape.dispose();
		headCircle.dispose();
		footCirlce.dispose();
		
		return body;
	}

}

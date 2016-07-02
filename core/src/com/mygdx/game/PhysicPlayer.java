package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import static com.mygdx.game.PhysicsManager.METERS_TO_PIXELS;
import static com.mygdx.game.PhysicsManager.PIXELS_TO_METERS;

public class PhysicPlayer  extends PhysicsActor {
	
	private World world;
	private Body body;
	private TiledMapTileLayer tileLayer;
	LevelCollisionGeneratorWorking collisionGenerator;
	
	private Probe leftProbe;
	private Probe rightProbe;
	private Probe headProbe;
	private Probe footProbe;
	
	public PhysicPlayer(Texture texture, float x, float y, TiledMap tiledMap,
			TiledMapTileLayer tileMapTileLayer, World world) {
		
		super(texture, world);
		
		this.setPosition(x, y);
		this.setSize(50.f, 100.f);
		//tileLayer = tileMapTileLayer;
		
		body = getHumanoidBody();
		
		collisionGenerator = new LevelCollisionGeneratorWorking(world);
		collisionGenerator.createPhysics(tiledMap);
		
		
		/*footProbe = new Probe(this, new Texture("footprobe.png"), 
				tileMapTileLayer, new Vector2(0, -1));
		headProbe = new Probe(this, new Texture("headprobe.png"), 
				tileMapTileLayer, new Vector2(0, 1));
		leftProbe = new Probe(this, new Texture("leftprobe.png"), 
				tileMapTileLayer, new Vector2(-1, 0));
		rightProbe = new Probe(this, new Texture("rigtprobe.png"), 
				tileMapTileLayer, new Vector2(1, 0));*/

		
		/*BodyDef bodyDef = new BodyDef();
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
		footCirlce.dispose();*/
		
		
	}
	
	public Body getBody() {
		return body;
	}
	
	public void update() {
		
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			body.applyForceToCenter(0.f, 
					35.f, false);
		}
		
		body.setLinearVelocity(GameInput.keyForce.x*2, body.getLinearVelocity().y);
		/*body.applyForceToCenter(GameInput.keyForce.x*25*PIXELS_TO_METERS, 
				0f, true);
		*/
		setPosition(body.getPosition().x*METERS_TO_PIXELS-getWidth()/2, 
				body.getPosition().y*METERS_TO_PIXELS-0*getHeight()-15.f);
		
	}
	@Override
	public void draw(Batch batch) {
		update();
		//batch.draw(getTexture(), getX(), getY());
		super.draw(batch);
		/*headProbe.draw(batch);
		footProbe.draw(batch);
		leftProbe.draw(batch);
		rightProbe.draw(batch);*/
	}

}

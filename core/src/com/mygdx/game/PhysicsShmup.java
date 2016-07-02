package com.mygdx.game;

import javax.jws.Oneway;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class PhysicsShmup extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	GameObject object;
	
	TiledMap tiledMap = new TiledMap();
	OrthographicCamera camera;
	TiledMapRenderer tiledMapRenderer;
	
	int levelPixelWidth;
	int levelPixelHeight;
	
	float width;
	float height;
	
	PhysicPlayer player;
	Enemy enemy;
	
	World world;
	Box2DDebugRenderer b2dr;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		
		width = Gdx.graphics.getWidth();
	    height = Gdx.graphics.getHeight();
		
		//object = new GameObject("badlogic.jpg", batch, 0, 0);
		TextManager.setSpriteBatch(batch);
		
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		camera.update();
		
		tiledMap = new TmxMapLoader().load("t2.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		MapProperties properties = tiledMap.getProperties();
		int levelWidth = properties.get("width", Integer.class);
		int levelHeight = properties.get("height", Integer.class);
		int tilePixelWidth = properties.get("tilewidth", Integer.class);
		int tilePixelHeight = properties.get("tileheight", Integer.class);
		
		levelPixelWidth = levelWidth * tilePixelWidth;
		levelPixelHeight = levelHeight * tilePixelHeight;
		
		
		world = new World(new Vector2(0.f, 
				-500.f * PhysicsManager.PIXELS_TO_METERS), false);
		
		b2dr = new Box2DDebugRenderer();
		
		player = new PhysicPlayer(
				new Texture(Gdx.files.internal("player.png")), 50, 200, tiledMap,
				(TiledMapTileLayer) tiledMap.getLayers().get(0), world);
		
		enemy = new Enemy(
				new Texture(Gdx.files.internal("player.png")), 400, 200, world);
		
		Lighting.createLights(world, tiledMap);
		
	}

	@Override
	public void render () {
		
		world.step(1.f/60.f, 6, 2);
		
		Matrix4 debugMatrix = batch.getProjectionMatrix().cpy().scale(PhysicsManager.METERS_TO_PIXELS, 
				PhysicsManager.METERS_TO_PIXELS, 0);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		
		
		Time.upDate();
		GameInput.update();
		
		//camera.position.set(object.x, object.y, 0);
		camera.position.x = Math.min(Math.max(player.getX(), width/2), 
				levelPixelWidth - (width/2));
		camera.position.y = Math.min(Math.max(player.getY(), height/2), 
				levelPixelHeight - (height/2));
		camera.update();
		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		player.draw(batch);
		enemy.draw(batch);
		//TextManager.draw("X:"+player.getX()+" Y:"+player.getY(), camera);
		batch.end();
		Lighting.updateAndDrawLights(camera);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		TextManager.draw("X:"+player.getX()+" Y:"+player.getY(), camera);
		batch.end();
		
		
		b2dr.render(world, debugMatrix/*camera.combined.scl(PhysicsManager.METERS_TO_PIXELS)*/);
		
		
	}
	
	@Override
	public void dispose() {
		world.dispose();
		batch.dispose();
		Lighting.dispose();
	}
}

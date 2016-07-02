package com.mygdx.game;


import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.sun.corba.se.impl.ior.GenericTaggedProfile;
import com.sun.org.apache.regexp.internal.recompile;

public class LevelCollisionGeneratorWorking {
	
	private World world;
	private Array<Body> bodies = new Array<Body>();
	
	/*private ObjectMap<String, FixtureDef> 
	materials = new ObjectMap<String, FixtureDef>();*/
	
	public LevelCollisionGeneratorWorking(World world) {
		
		this.world = world;
		
		/*FixtureDef defaultFixture = new FixtureDef();
		defaultFixture.density = 1.0f;
		defaultFixture.friction = 0.8f;
		defaultFixture.restitution = 0.0f;*/
	}
	
	public void createPhysics(TiledMap map) {
		createPhysics(map, "CollisionLayer");
	}
	
	public void createPhysics(TiledMap map, String layerName) {
		
		MapLayer layer = map.getLayers().get(layerName);
		
		MapObjects objects = layer.getObjects();
	    Iterator<MapObject> objectIt = objects.iterator();
	    
	    while (objectIt.hasNext()) {
	    	MapObject object = objectIt.next();
	    	
	    	if ((object instanceof TextureMapObject)) {
	    		continue;
	    	}
	    	
	    	Shape shape;
	    	
	    	BodyDef bodyDef = new BodyDef();
	    	bodyDef.type = BodyDef.BodyType.StaticBody;
	    	
	    	if (object instanceof RectangleMapObject) {
	    		RectangleMapObject rectangle = (RectangleMapObject) object;
	    		shape = getRectangle(rectangle);
	    	}
	    	else if (object instanceof PolygonMapObject) {
	    		shape = getPolygon((PolygonMapObject)object);
	    	}
	    	else if (object instanceof PolylineMapObject) {
	    		shape = getPolyline((PolylineMapObject)object);
	    	}
	    	else if (object instanceof CircleMapObject) {
	    		shape = getCircle((CircleMapObject)object);
	    	}
	    	else {
	    		Gdx.app.log("Unrecognized shape", ""+object.toString());
	    		continue;
	    	}
	    	
	    	FixtureDef fixtureDef = new FixtureDef();
	    	fixtureDef.shape = shape;
	    	
	    	Body body = world.createBody(bodyDef);
	    	body.createFixture(fixtureDef);
	    	
	    	fixtureDef.shape = null;
	    	shape.dispose();
	    }	
	}
	
	public void destroyPhysics() {
		for (Body body: bodies) {
			world.destroyBody(body);
		}
		bodies.clear();
	}
	
	private Shape getRectangle(RectangleMapObject rectangleObject) {
		Rectangle rectangle = rectangleObject.getRectangle();
		PolygonShape polygon = new PolygonShape();
		Vector2 size = new Vector2((rectangle.x+rectangle.width*0.5f)
				*PhysicsManager.PIXELS_TO_METERS,
				(rectangle.y + rectangle.height*0.5f)
				*PhysicsManager.PIXELS_TO_METERS);
		polygon.setAsBox(rectangle.width*0.5f*PhysicsManager.PIXELS_TO_METERS,
				rectangle.height * 0.5f *PhysicsManager.PIXELS_TO_METERS,
				size, 0.0f);
		return polygon;
	}
	
	private Shape getCircle(CircleMapObject circleObject) {
		Circle circle = circleObject.getCircle();
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(circle.radius*PhysicsManager.PIXELS_TO_METERS);
		circleShape.setPosition(
				new Vector2(circle.x*PhysicsManager.PIXELS_TO_METERS,
						circle.y*PhysicsManager.PIXELS_TO_METERS));

		return circleShape;
	}
	
	private Shape getPolygon(PolygonMapObject polygonObject) {
		PolygonShape polygon = new PolygonShape();
		float[] vertices = polygonObject.getPolygon().getTransformedVertices();
		
		float[] worldVertices = new float[vertices.length];
		
		for (int i = 0; i < vertices.length; ++i) {
			worldVertices[i] = vertices[i] * PhysicsManager.PIXELS_TO_METERS;
		}
		polygon.set(worldVertices);
		return polygon;
	}
	
	private Shape getPolyline(PolylineMapObject polilineObject) {
		float[] vertices = polilineObject.getPolyline().getTransformedVertices();
				
		Vector2[] worldVertices = new Vector2[vertices.length/2];
		
		for (int i = 0; i < vertices.length/2; ++i) {
			worldVertices[i] = new Vector2();
			worldVertices[i].x = vertices[i*2] * PhysicsManager.PIXELS_TO_METERS;
			worldVertices[i].y = vertices[i*2+1] * PhysicsManager.PIXELS_TO_METERS;
		}
		ChainShape chain = new ChainShape();
		chain.createChain(worldVertices);
		return chain;
	}

}

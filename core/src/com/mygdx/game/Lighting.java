package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Lighting {

	private static World world;
	private static TiledMap tileMap;
	private static Array<Light> lights = new Array<Light>();
	private static final int MAX_LIGHTS = 8;
	private static final int MAX_RAYS = 1000;
	private static RayHandler rayHandler;
	
	public static void createLights(World w, TiledMap t) {
		
		world = w;
		tileMap = t;
		
		rayHandler = new RayHandler(world);
		
		int numLights = 0;
		
		MapObjects objects = tileMap.getLayers().get("Lighting").getObjects();
		Iterator<MapObject> objectIterator = objects.iterator();

		while (objectIterator.hasNext() && numLights < MAX_LIGHTS) {
			Light light = null;
			MapObject object = objectIterator.next();
			
			
			String result = (String) object.getProperties().get("Type");
			
			if (result == "Point") {
				light = new PointLight(rayHandler, MAX_RAYS);
			} else {
				light = new PointLight(rayHandler, MAX_RAYS);
			}
			
			float distance = Float.parseFloat((String) 
					object.getProperties().get("Distance"));
			
			if (object instanceof RectangleMapObject) {
				Rectangle rectangle = ((RectangleMapObject)object).getRectangle();
				/*light = new PointLight(rayHandler, 1000, Color.WHITE, 8, 
						rectangle.x*PhysicsManager.PIXELS_TO_METERS,
						rectangle.y*PhysicsManager.PIXELS_TO_METERS);*/
				light.setDistance(distance);
				light.setPosition((rectangle.x + rectangle.getWidth()/2)*
						PhysicsManager.PIXELS_TO_METERS,
						(rectangle.y + rectangle.getHeight()/2)*
						PhysicsManager.PIXELS_TO_METERS);
			}
			else if (object instanceof CircleMapObject) {
				Circle circle = ((CircleMapObject) object).getCircle();
				light.setDistance(distance);
				light.setPosition((circle.x)*
						PhysicsManager.PIXELS_TO_METERS,
						(circle.y)*
						PhysicsManager.PIXELS_TO_METERS);
			}
			lights.add(light);
		}
		
	}
	

	@SuppressWarnings("deprecation")
	public static void updateAndDrawLights(Camera camera) {
		rayHandler.setCombinedMatrix(camera.combined.cpy().scale(PhysicsManager.METERS_TO_PIXELS, 
		PhysicsManager.METERS_TO_PIXELS, 1.f));
		rayHandler.updateAndRender();
	}
	
 	public static void dispose() {
		/*for (Light light: lights)
			light.dispose();*/
		lights.clear();
	}
}

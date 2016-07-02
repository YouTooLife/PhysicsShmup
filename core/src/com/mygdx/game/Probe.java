package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Probe extends Sprite {
	
	protected PhysicPlayer player;
	protected Texture texture;
	protected TiledMapTileLayer tileLayer;
	protected final int  TILE_LOOKAHEAD = 3;
	
	private Vector2 lookaheadAxis = new Vector2(0, 0);
	
	public Probe(PhysicPlayer p, Texture t, TiledMapTileLayer tmtl,
			Vector2 axis) {
		super(t);
		player = p;
		tileLayer = tmtl;
		lookaheadAxis.set(axis);
		
		setX(player.getX());
		setY(player.getY());
		
	}
	
	@Override
	public void draw(Batch batch) {
		update();
		batch.draw(getTexture(), getX(), getY());
		
	}

	private void update() {
		boolean playerIsOnTwoTiles = false;
		int tileToFind = 1;
		TiledMapTileLayer.Cell cell;
		TiledMapTileLayer.Cell cell2 = null;
		
		setX(player.getX());
		
		if (player.getX() % tileLayer.getTileWidth() != 0) {
			playerIsOnTwoTiles = true;
		}
		
		while (true) {
			if (tileToFind > TILE_LOOKAHEAD) {
				setY(player.getY() - 50);
				break;
			}
			cell = tileLayer.getCell((int)(player.getX()/tileLayer.getTileWidth())
					+ (int) (tileToFind*lookaheadAxis.x), 
					(int)(player.getY()/tileLayer.getHeight())
					+(int)(tileToFind*lookaheadAxis.y));
			if (playerIsOnTwoTiles) {
				cell2 = tileLayer.getCell(
						(int)(player.getX()/tileLayer.getWidth())
						+(int)(tileToFind*lookaheadAxis.x)
						+(int) Math.abs(lookaheadAxis.y), 
						(int)(player.getY()/tileLayer.getHeight())
						+ (int) (tileToFind*lookaheadAxis.y)
						+ (int) Math.abs(lookaheadAxis.x));
			}
			if (cell != null) {
				int heightOfTile = (int)(player.getY()
						/tileLayer.getHeight())-tileToFind; 
				setY(heightOfTile * tileLayer.getTileHeight() + 45);
				break;
			}
			else {
				if (cell2 != null) {
					int heightOfTile = (int)(player.getY()
							/tileLayer.getHeight())-tileToFind; 
					setY(heightOfTile * tileLayer.getTileHeight() + 45);
					break;
				}
			}
			tileToFind++;
		}
	}

}

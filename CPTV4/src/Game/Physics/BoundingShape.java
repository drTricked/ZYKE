package Game.Physics;

import java.util.ArrayList;

import Game.Level.Tile.Tile;

/**
 * Abstract class defining the bounding shape
 * @author Mark Sabado
 */
public abstract class BoundingShape {

	/**
	 * checks if they colliding
	 * 
	 * @param bv the shape
	 * @return if they colliding
	 */
	public boolean checkCollision(BoundingShape bv) {
		if (bv instanceof AABoundingRect)
			return checkCollision((AABoundingRect) bv);
		return false;
	}

	/**
	 * checks if they colliding
	 * 
	 * @param box the shape
	 * @return if they colliding
	 */
	public abstract boolean checkCollision(AABoundingRect box);

	/**
	 * changes position of box
	 * 
	 * @param newX The x location of the object
	 * @param newY The y location of the object
	 */
	public abstract void updatePosition(float newX, float newY);

	/**
	 * changes the position of the colliding shape
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 */
	public abstract void movePosition(float x, float y);

	/**
	 * gets the tiles around it - 4 tiles around the object
	 * 
	 * @param tiles the tiles for checking
	 * @return the tiles
	 */
	public abstract ArrayList<Tile> getTilesOccupying(Tile[][] tiles);

	/**
	 * gets the tiles below - 2 tiles below
	 * 
	 * @param tiles the tiles for checking
	 * @return the tiles
	 */
	public abstract ArrayList<Tile> getGroundTiles(Tile[][] tiles);

}

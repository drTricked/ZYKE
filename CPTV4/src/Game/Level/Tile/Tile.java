package Game.Level.Tile;

import Game.Physics.BoundingShape;

/**
 * Tile object
 * 
 * @author Mark Sabado
 */
public class Tile {

	/** The x location of the object */
	protected int x;
	/** The y location of the object */
	protected int y;
	/** Whether the object is blocked */
	protected boolean blocked;

	protected BoundingShape boundingShape;

	/**
	 * Creates a tile object
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @param blocked whether the bloc has collision
	 */
	public Tile(int x, int y, boolean blocked) {
		this.x = x;
		this.y = y;
		this.blocked = blocked;
	}

	/**
	 * Gets the x position
	 * 
	 * @return The x location of the object
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y position
	 * 
	 * @return The y location of the object
	 */
	public int getY() {
		return y;
	}

	/**
	 * gets the bounding shape object
	 * 
	 * @return the bounding shape object
	 */
	public BoundingShape getBoundingShape() {
		return boundingShape;
	}

	/**
	 * gets if the tile has a colllsion property
	 * 
	 * @return if the tile is blocked
	 */
	public boolean isBlocked() {
		return blocked;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
}

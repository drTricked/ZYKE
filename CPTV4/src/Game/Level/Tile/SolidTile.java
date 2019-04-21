package Game.Level.Tile;

import Game.Physics.AABoundingRect;

/**
 * A tile representing a solidtile
 * 
 * @author Mark Sabado
 */
public class SolidTile extends Tile {

	/**
	 * Creates a solid tile
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @param b if the tile has collision
	 */
	public SolidTile(int x, int y, boolean b) {
		super(x, y, b);
		boundingShape = new AABoundingRect(x * 32, y * 32, 32, 32);

	}
}

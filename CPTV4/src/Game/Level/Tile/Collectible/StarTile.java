package Game.Level.Tile.Collectible;

import Game.Level.Tile.Tile;
import Game.Physics.AABoundingRect;

/**
 * A tile representing a star
 * 
 * @author Mark Sabado
 */
public class StarTile extends Tile {

	/**
	 * Creates a star tile
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @param blocked if the tile has collision
	 */
	public StarTile(int x, int y, boolean blocked) {
		super(x, y, blocked);
		boundingShape = new AABoundingRect(x * 32, y * 32, 32, 32);

	}

}

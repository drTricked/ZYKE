package Game.Physics;

import java.awt.Rectangle;
import java.util.ArrayList;

import Game.Level.Tile.Tile;

/**
 * The bounding shape
 * 
 * @author Mark Sabado
 */
public class AABoundingRect extends BoundingShape {
	
	/** The x location of the object */
	public float x;	
	/** The y location of the object */
	public float y;
	/** The width of the object */
	public float width;
	/** The height of the object */
	public float height;

	/**
	 * Creates a bounding shape
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @param width The width of the object
	 * @param height The height of the object
	 */
	public AABoundingRect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * changes position of box
	 * 
	 * @param newX The x location of the object
	 * @param newY The y location of the object
	 */
	public void updatePosition(float newX, float newY) {
		this.x = newX;
		this.y = newY;
	}
	
	/**
	 * changes the position of the colliding shape
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 */
	public void movePosition(float x, float y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Gets the rectangle
	 * 
	 * @return the rectangle for collision detection
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	/**
	 * checks if they colliding
	 * 
	 * @param rect the rectangle object for collision detection
	 * @return if they colliding
	 */
	public boolean checkCollision(AABoundingRect rect) {
		// return !(rect.x > this.x + width || rect.x + rect.width < this.x || rect.y >
		// this.y + height
		// || rect.y + rect.height < this.y);

		Rectangle r1 = getRectangle();
		Rectangle r2 = rect.getRectangle();
		return r1.intersects(r2);
	}

	/**
	 * gets the tiles around it - 4 tiles around the object
	 * 
	 * @param tiles the tiles for checking
	 * @return the tiles
	 */
	public ArrayList<Tile> getTilesOccupying(Tile[][] tiles) {
		ArrayList<Tile> occupiedTiles = new ArrayList<Tile>();

		// we go from the left of the rect towards to right of the rect, making sure we
		// round upwards to a multiple of 32 or we might miss a few tiles
		for (int i = (int) x; i <= (x + width + (32 - width % 32)); i += 32) {
			for (int j = (int) y; j <= (y + height + (32 - height % 32)); j += 32) {
				occupiedTiles.add(tiles[i / 32][j / 32]);
			}
		}
		return occupiedTiles;
	}

	/**
	 * gets the tiles below - 2 tiles below
	 * 
	 * @param tiles the tiles for checking
	 * @return the tiles
	 */
	public ArrayList<Tile> getGroundTiles(Tile[][] tiles) {
		ArrayList<Tile> tilesUnderneath = new ArrayList<Tile>();
		int j = (int) (y + height + 1);

		for (int i = (int) x; i <= x + width + (32 - width % 32); i += 32) {
			tilesUnderneath.add(tiles[i / 32][j / 32]);
		}

		return tilesUnderneath;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "X: " + x + " Y: " + y + " W: " + width + " H: " + height;
	}

}

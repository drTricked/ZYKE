package Game.Level;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.tiled.TiledMap;

import Collectibles.DreamStar;
import Collectibles.Star;
import Game.BaseGame;
import Game.Character.GameCharacter;
import Game.Character.Player;
import Game.Level.Tile.SolidTile;
import Game.Level.Tile.Tile;
import Game.Level.Tile.Collectible.StarTile;

/**
 * The logic inside one level
 * 
 * @author Mark Sabado
 */

public class Level {

	/** The tile map */
	private TiledMap map;

	// list of all characters
	/** list of characters */
	private ArrayList<GameCharacter> characters;
	/** list of levelobjects */
	private ArrayList<LevelObject> levelObjects;
	/** list of checkpoints */
	private ArrayList<CheckPoint> checkPoints;
	/** list of enemies */
	private ArrayList<Enemy> enemies;
	/** list of colliding tiles */
	private Tile[][] tiles;

	/** x location of last checkpoint */
	private float lastCheckpointX;
	/** y location of last checkpoint */
	private float lastCheckpointY;

	/** the player object */
	private Player player;

	/** background image */
	private Image background;

	/** boolean for if the player is able to go to next level */
	private boolean enterNextLevel;

	/** boolean if player is out of bounds */
	private boolean playerOOB;

	/** sound for when getting the star */
	private Sound starSound;

	/**
	 * Creates a new level
	 * 
	 * @param levelName
	 *            The name for the tilemap file
	 * @param player
	 *            The player object
	 * @throws SlickException
	 *             Failure to load images or load tile map
	 */
	public Level(String levelName, Player player) throws SlickException {

		// create the tiled map
		try {
			map = new TiledMap("TileMap" + levelName + ".tmx");
		} catch (Exception e) {
			System.err.print(e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}

		// initialize lists of objects
		characters = new ArrayList<GameCharacter>();
		levelObjects = new ArrayList<LevelObject>();
		checkPoints = new ArrayList<CheckPoint>();
		enemies = new ArrayList<Enemy>();

		// load objects
		this.player = player;
		loadTileMap();
		loadCollectibles();
		loadCheckPoints();
		loadEnemies();

		background = new Image("Background/BG.png");

		enterNextLevel = false;

		starSound = new Sound("Resources/Sound Clips/Star.ogg");
	}

	/**
	 * sets the background
	 * 
	 * @param backgroundTitle
	 *            the reference for the background title
	 */
	public void setBackground(String backgroundTitle) {
		try {
			background = new Image(backgroundTitle);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gets if player is oob
	 * 
	 * @return if player is oob
	 */
	public boolean isPlayerOOB() {
		return playerOOB;
	}

	/**
	 * sets if player is oob
	 * 
	 * @param playerOOB
	 *            if he oob
	 */
	public void setPlayerOOB(boolean playerOOB) {
		this.playerOOB = playerOOB;
	}

	/**
	 * gets the x location of the last checkpoint passed
	 * 
	 * @return the x location of last checkpoint
	 */
	public float getLastCheckpointX() {
		return lastCheckpointX;
	}

	/**
	 * sets the x location of the last checkpoint passed
	 * 
	 * @param lastCheckpointX
	 *            the x location of last checkpoint
	 */
	public void setLastCheckpointX(float lastCheckpointX) {
		this.lastCheckpointX = lastCheckpointX;
	}

	/**
	 * gets the y of checkpoint
	 * 
	 * @return the y location of checkpoint
	 */
	public float getLastCheckpointY() {
		return lastCheckpointY;
	}

	/**
	 * sets the y of last checkpoint
	 * 
	 * @param lastCheckpointY
	 *            the y of the checkpoint
	 */
	public void setLastCheckpointY(float lastCheckpointY) {
		this.lastCheckpointY = lastCheckpointY;
	}

	/**
	 * adds a game character
	 * 
	 * @param c
	 *            the game character you want like to add
	 */
	public void addCharacter(GameCharacter c) {
		characters.add(c);
	}

	/**
	 * adds a level object
	 * 
	 * @param obj
	 *            the level object you want to add
	 */
	public void addLevelObject(LevelObject obj) {
		levelObjects.add(obj);
	}

	/**
	 * removes a level object
	 * 
	 * @param obj
	 *            the level object you want to remove
	 */
	public void removeLevelObject(LevelObject obj) {
		levelObjects.remove(obj);
	}

	/**
	 * removes all level objects from a list
	 * 
	 * @param objects
	 *            the list of objects you want to remove
	 */
	public void removeLevelObjects(ArrayList<LevelObject> objects) {
		levelObjects.removeAll(objects);
	}

	/**
	 * gets a list of level objects
	 * 
	 * @return the list of level objects
	 */
	public ArrayList<LevelObject> getLevelObjects() {
		return levelObjects;
	}

	/**
	 * adds a checkpoint object
	 * 
	 * @param obj
	 *            the checkpoint obj you want to add
	 */
	public void addCheckPoint(CheckPoint obj) {
		checkPoints.add(obj);
	}

	/**
	 * removes a checkpoint object
	 * 
	 * @param obj
	 *            the checkpoint you want to remove
	 */
	public void removeCheckPoint(CheckPoint obj) {
		checkPoints.remove(obj);
	}

	/**
	 * 
	 * removes a list of objects
	 *
	 * @param objects
	 *            the list of checkpoints you want to remove
	 */
	public void removeCheckPoint(ArrayList<CheckPoint> objects) {
		checkPoints.removeAll(objects);
	}

	/**
	 * gets a list of checkpoints
	 * 
	 * @return a list of checkpoints
	 */
	public ArrayList<CheckPoint> getCheckPoints() {
		return checkPoints;
	}

	/**
	 * gets the list of enemy objects
	 * 
	 * @return the list of enemy objects
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * sets the new list of enemies
	 * 
	 * @param enemies
	 *            the list of enemies
	 */
	public void addEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	/**
	 * adds an enemy object
	 * 
	 * @param enemy
	 *            the enemy to add
	 */
	public void addEnemy(Enemy enemy) {
		enemies.add(enemy);
	}

	/**
	 * removes an enemy object from the list
	 * 
	 * @param enemy
	 *            the enemy to remove
	 */
	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	/**
	 * removes a list of enemies
	 * 
	 * @param enemies
	 *            the enemies you want to remove
	 */
	public void removeAllEnemies(ArrayList<Enemy> enemies) {
		enemies.removeAll(enemies);
	}

	/**
	 * plays the audio clip for interacting with the star collectible
	 */
	public void playStarSound() {
		starSound.play(1, 0.35f);
	}

	/**
	 * Renders all of the graphics for the level
	 */
	public void render() {

		int offset_x = getXOffset();
		int offset_y = getYOffset();

		// render the background
		renderBackground();

		// then renders the map, doesnt draw any of the layers for level objects
		for (int i = 0; i < map.getLayerCount(); i++) {
			// loop through the layers but if its one of the object layers then skip it
			if (i == map.getLayerIndex("Collectibles") || i == map.getLayerIndex("CheckPoints")
					|| i == map.getLayerIndex("Enemies")) {
				continue;
			}

			// renders the current layer
			map.render(-(offset_x % 32), -(offset_y % 32), offset_x / 32, offset_y / 32, 33, 19, i, false);
		}

		// and then render the characters on top of the map
		for (GameCharacter c : characters) {
			c.render(offset_x, offset_y);
		}

		for (LevelObject obj : levelObjects) {
			obj.render(offset_x, offset_y);
		}

		for (CheckPoint checkPoint : checkPoints) {
			checkPoint.render(offset_x, offset_y);
		}

		for (Enemy enemy : enemies) {
			enemy.render(offset_x, offset_y);
		}
	}

	/**
	 * Renders the background image and scrolls it as well
	 */
	private void renderBackground() {

		// first calculate the maximum amount we can "scroll" the background image
		// before we have the rightmore or bottom most pixel on the screen
		float backgroundXScrollValue = (background.getWidth() - BaseGame.WINDOW_WIDTH / BaseGame.SCALE);
		float backgroundYScrollValue = (background.getHeight() - BaseGame.WINDOW_HEIGHT / BaseGame.SCALE);

		// we do the same for the map
		float mapXScrollValue = ((float) map.getWidth() * 32 - BaseGame.WINDOW_WIDTH / BaseGame.SCALE);
		float mapYScrollValue = ((float) map.getHeight() * 32 - BaseGame.WINDOW_HEIGHT / BaseGame.SCALE);

		// and now calculate the factor we have to multiply the offset with, making sure
		// we multiply the offset by -1 to get it to negative
		float scrollXFactor = backgroundXScrollValue / mapXScrollValue * -1;
		float scrollYFactor = backgroundYScrollValue / mapYScrollValue * -1;

		// and now draw it using the factor and the offset to see where we start drawing
		if (scrollYFactor == Float.NEGATIVE_INFINITY)
			scrollYFactor = 0;

		background.draw(this.getXOffset() * scrollXFactor, this.getYOffset() * scrollYFactor);
	}

	/**
	 * gets the list of characters
	 * 
	 * @return the list of charactes
	 */
	public ArrayList<GameCharacter> getCharacters() {
		return characters;
	}

	/**
	 * gets the list of tiles for the collision layer
	 * 
	 * @return a list of tiles
	 */
	public Tile[][] getTiles() {
		return tiles;
	}

	/**
	 * changes the tile of the object to a different tile
	 * 
	 * @param x
	 *            The x location of the object
	 * @param y
	 *            The y location of the object
	 */
	public void changeTile(int x, int y) {
		tiles[x][y] = new SolidTile(x, y, false);
	}

	/**
	 * Loads the tile map for the level
	 */
	private void loadTileMap() {
		// create an array to hold all the tiles in the map
		tiles = new Tile[map.getWidth()][map.getHeight()];

		int layerIndex = 0;

		// checks to find if layer is there or not
		try {
			layerIndex = map.getLayerIndex("CollisionLayer");
			if (layerIndex == -1) {
				throw new LayerNotExistException();
			}
		} catch (LayerNotExistException e) {
			System.err.println("Map does not have the layer \"CollisionLayer\"");
			System.exit(0);
		}

		// loop through the whole map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				// get the tile
				int tileID = map.getTileId(x, y, layerIndex);
				Tile tile = null;

				// and check what kind of tile it is (
				switch (map.getTileProperty(tileID, "Blocked", "Solid")) {
				case "whaterver":
					tile = new StarTile(x, y, false);
					System.out.println("whatever - loadtile");
					break; // 11,9
				default:
					if (tileID == 0 || tileID == 97) {
						tile = new SolidTile(x, y, false);
					} else if (tileID == 257) {
						tile = new StarTile(x, y, false);
						try {
							addLevelObject(new Star(x * 32, y * 32));
						} catch (SlickException e) {
							e.printStackTrace();
						}
					} else {
						tile = new SolidTile(x, y, true);
						// counter++;
						// System.out.println(counter);
					}
					break;
				}
				tiles[x][y] = tile;
			}
		}
	}

	/**
	 * Loads the collectibles for the level
	 */
	public void loadCollectibles() {
		int layerIndex = 0;

		try {
			layerIndex = map.getLayerIndex("Collectibles");

			if (layerIndex == -1) {
				throw new LayerNotExistException();
			}
		} catch (LayerNotExistException e) {
			System.err.println("Map does not have the layer \"Collectibles\"");
			System.err.println(e.getStackTrace());
			System.exit(0);
		}

		// loop through the whole map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				// get the tile
				int tileID = map.getTileId(x, y, layerIndex);

				try {
					// System.out.println(map.getTileProperty(tileID, "Blocked", "Solid"));
					// and check what kind of tile it is (
					switch (map.getTileProperty(tileID, "Blocked", "x")) {

					default:

						if (tileID != 0) { // either 257 - normal star or 262 for dream star
							// System.out.println("Tile: " + tileID);
							try {
								if (tileID == 257 || tileID == 260 || tileID == 261) {
									addLevelObject(new Star(x * 32, y * 32));
								} else {
									addLevelObject(new DreamStar(x * 32, y * 32));
								}
							} catch (SlickException e) {
								e.printStackTrace();
							}
							break;
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println(e.getStackTrace());

				}
			}
		}
	}

	/**
	 * Loads the checkpoints for the level
	 */
	public void loadCheckPoints() {
		int layerIndex = 0;

		try {
			layerIndex = map.getLayerIndex("CheckPoints");

			if (layerIndex == -1) {
				throw new LayerNotExistException();
			}
		} catch (LayerNotExistException e) {
			System.err.println("Map does not have the layer \"CheckPoints\"");
			System.err.println(e.getStackTrace());
			System.exit(0);
		}

		// loop through the whole map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				// get the tile
				int tileID = map.getTileId(x, y, layerIndex);

				// System.out.println(map.getTileProperty(tileID, "Blocked", "Solid"));
				// and check what kind of tile it is (
				switch (map.getTileProperty(tileID, "Blocked", "x")) {

				default:
					if (tileID != 0) {
						try {
							addCheckPoint(new CheckPoint(x * 32 + 15, y * 32 - 14));
						} catch (SlickException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}
	}

	/**
	 * Loads the enemies for the level
	 */
	public void loadEnemies() {
		int layerIndex = 0;

		try {
			layerIndex = map.getLayerIndex("Enemies");

			if (layerIndex == -1) {
				throw new LayerNotExistException();
			}
		} catch (LayerNotExistException e) {
			System.err.println("Map does not have the layer \"Enemies\"");
			System.err.println(e.getStackTrace());
			System.exit(0);
		}

		// loop through the whole map
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				// get the tile
				int tileID = map.getTileId(x, y, layerIndex);

				// System.out.println(map.getTileProperty(tileID, "Blocked", "Solid"));
				// and check what kind of tile it is (
				switch (map.getTileProperty(tileID, "Blocked", "x")) {

				default:
					if (tileID != 0) {
						addEnemy(new MushroomEnemy(x * 32 + 15, y * 32 - 14));
						break;
					}
				}
			}
		}
	}

	/**
	 * gets the x offset
	 * 
	 * @return the x offset
	 */
	public int getXOffset() {
		int offset_x = 0;

		// the first thing we are going to need is the half-width of the screen, to
		// calculate if the player is in the middle of our screen
		int half_width = (int) (BaseGame.WINDOW_WIDTH / BaseGame.SCALE / 2);

		// next up is the maximum offset, this is the most right side of the map, minus
		// half of the screen offcourse
		int maxX = (int) (map.getWidth() * 32) - half_width;

		// now we have 3 cases here
		if (player.getX() < half_width) {
			// the player is between the most left side of the map, which is zero and half a
			// screen size which is 0+half_screen
			offset_x = 0;
		} else if (player.getX() > maxX) {
			// the player is between the maximum point of scrolling and the maximum width of
			// the map
			// the reason why we substract half the screen again is because we need to set
			// our offset to the topleft position of our screen
			offset_x = maxX - half_width;
		} else {
			// the player is in between the 2 spots, so we set the offset to the player,
			// minus the half-width of the screen
			offset_x = (int) (player.getX() - half_width);
		}

		return offset_x;
	}

	/**
	 * gets the y offset
	 * 
	 * @return the y offset
	 */
	public int getYOffset() {
		int offset_y = 0;

		int half_height = (int) (BaseGame.WINDOW_HEIGHT / BaseGame.SCALE / 2);

		int maxY = (int) (map.getHeight() * 32) - half_height;

		if (player.getY() < half_height) {
			offset_y = 0;
		} else if (player.getY() > maxY) {
			offset_y = maxY - half_height;
		} else {
			offset_y = (int) (player.getY() - half_height);
		}

		return offset_y;
	}

	/**
	 * sets whether it can enter the next level
	 * 
	 * @param b
	 *            the value for whether it can enter the next level
	 */
	public void setEnterNextLevel(boolean b) {
		enterNextLevel = b;
	}

	/**
	 * gets the boolean for entering next level
	 * 
	 * @return gets the boolean for entering next level
	 */
	public boolean getEnterNextLevel() {
		return enterNextLevel;
	}

}
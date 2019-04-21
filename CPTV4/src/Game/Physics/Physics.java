package Game.Physics;

import java.util.ArrayList;

import Collectibles.DreamStar;
import Collectibles.Star;
import Game.Character.GameCharacter;
import Game.Character.Player;
import Game.Level.CheckPoint;
import Game.Level.Enemy;
import Game.Level.Level;
import Game.Level.LevelObject;
import Game.Level.Tile.Tile;

/**
 * THe logic behind all physics of the level
 * 
 * @author Mark
 *
 */
public class Physics {

	/** The value for what gravity is supposed to be apllied to movement */
	private final float GRAVITY = 0.0015f;

	/** score*/
	private int score;
	/** health */
	private int health;

	/**
	 * Creates a Physics object
	 */
	public Physics() {
		score = 0;
		health = 0;
	}

	/**
	 * gets the score value
	 * 
	 * @return the score value
	 */
	public int getScore() {
		return score;
	}

	/**
	 * sets the new score value
	 * 
	 * @param score the new score value
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * gets the health value
	 * 
	 * @return the value of health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * sets the health value
	 * 
	 * @param health the new health value
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Checks collision between an object and surrounding tiles. Takes the tiles around it and compares position 
	 * between object and tile to see if there is collision
	 * 
	 * @param obj the object used for testing collision on
	 * @param mapTiles the tiles used for collision
	 * @return whether the object is colliding
	 */
	private boolean checkCollision(LevelObject obj, Tile[][] mapTiles) {
		// get only the tiles that matter
		ArrayList<Tile> tiles = obj.getBoundingShape().getTilesOccupying(mapTiles);

		for (Tile t : tiles) {
			// if this tile has a bounding shape
			if (t.getBoundingShape() != null) {
				if (t.getBoundingShape().checkCollision(obj.getBoundingShape()) && t.isBlocked()) {
					return true;
				}
			}
		}
		return false;
	}

	/** 
	 * Determines if the object is on the ground by checking the tiles below it, if these tiles are not boundaries
	 * then it will return false
	 * 
	 * @param obj the object used for testing collision on
	 * @param mapTiles the tiles used for collision
	 * @return whether its on the ground
	 */
	private boolean isOnGround(LevelObject obj, Tile[][] mapTiles) {
		// we get the tiles that are directly "underneath" the characters, also known as
		// the ground tiles
		ArrayList<Tile> tiles = obj.getBoundingShape().getGroundTiles(mapTiles);
		// we lower the the bounding object a bit so we can check if we are actually a
		// bit above the ground
		obj.getBoundingShape().movePosition(0, 1);
		// System.out.println(obj.getBoundingShape());
		// System.out.println(tiles);

		for (Tile t : tiles) {
			// System.out.println(t);
			// not every tile has a bounding shape (air tiles for example)
			if (t.getBoundingShape() != null) {
				// if the ground and the lowered object collide, then we are on the ground
				if (t.getBoundingShape().checkCollision(obj.getBoundingShape())) {
					// don't forget to move the object back up even if we are on the ground!
					if (t.isBlocked()) {
						obj.getBoundingShape().movePosition(0, -1);
						return true;
					} else {
						// return false;
					}
				}
			}
		}

		// and obviously we have to move the object back up if we don't hit the ground
		obj.getBoundingShape().movePosition(0, -1);

		return false;
	}

	/**
	 * Checks if the character is moving and its interaction between other objects.
	 * 
	 * @param level the level used to change variables
	 * @param delta the difference in time for frames
	 */
	private void handleCharacters(Level level, int delta) {
		for (GameCharacter c : level.getCharacters()) {

			// and now decelerate the character if he is not moving anymore
			if (!c.isMoving()) {
				c.decelerate(delta);
			}

			handleGameObject(c, level, delta);
			// handlePlayerAttacks(c, level, delta);

			// special cases for the player
			if (c instanceof Player) {

				ArrayList<LevelObject> removeQueue = new ArrayList<LevelObject>();

				// we have to check if he collides with anything special, such as objectives for
				// example
				for (LevelObject obj : level.getLevelObjects()) {

					if (obj instanceof Star) {

						// in case its an objective and its collides
						if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
							// we have to remove the object from the level, and add something to the score
							// ADD TO SCORE
							// SCORE++
							int tileX = ((int) c.getX()) / 32;
							int tileY = ((int) c.getY()) / 32;
							level.changeTile(tileX, tileY);
							score += 50;
							level.playStarSound();
							// level.setEnterNextLevel(true);
							removeQueue.add(obj);

						}
					} else if (obj instanceof DreamStar) {
						if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
							level.setEnterNextLevel(true);

						}
					} else if (obj instanceof Enemy) {
						if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
							System.out.println("COLLIDE");
							health++;
						}
					}
				}

				for (LevelObject obj : level.getCheckPoints()) {
					if (obj.getBoundingShape().checkCollision(c.getBoundingShape())) {
						((CheckPoint) obj).setCleared(true);
						level.setLastCheckpointX(obj.getX());
						level.setLastCheckpointY(obj.getY());
					}
				}

				level.removeLevelObjects(removeQueue);
			}

		}

	}

	/**
	 * calls the handle game object for each of the checkpoints
	 * 
	 * @param level the level used to change variables
	 * @param delta the difference in time for frames
	 */
	private void handleCheckPoints(Level level, int delta) {
		for (CheckPoint checkpoint : level.getCheckPoints()) {
			handleGameObject(checkpoint, level, delta);
		}
	}

	/**
	 * calls the handle game object for each of the enemies
	 * 
	 * @param level the level used to change variables
	 * @param delta the difference in time for frames
	 */
	private void handleEnemies(Level level, int delta) {
		for (Enemy enemy : level.getEnemies()) {
			handleGameObject(enemy, level, delta);
		}
	}

	/**
	 * calls the logic for each object - characters, checkpoints enemies etc
	 * @param level the level used to change variables
	 * @param delta the difference in time for frames
	 */
	public void handlePhysics(Level level, int delta) {
		handleCharacters(level, delta);
		// handleLevelObjects(level, delta);
		handleCheckPoints(level, delta);
		handleEnemies(level, delta);
	}

	/**
	 * applies movement and gravity to an object
	 * 
	 * @param obj the level object used
	 * @param level the level used to change variables
	 * @param delta the difference in time for frames
	 */
	private void handleGameObject(LevelObject obj, Level level, int delta) {

		// first update the onGround of the object
		obj.setOnGround(isOnGround(obj, level.getTiles()));
		
		//if player is not on ground or jumping then apply gravity
		if ((!obj.isOnGround() || obj.getYVelocity() < 0)) {
			obj.applyGravity(GRAVITY * delta);
		} else {
			obj.setYVelocity(0);
		}

		// calculate how much we actually have to move
		// based on frames and velocity
		float x_movement = obj.getXVelocity() * delta;
		float y_movement = obj.getYVelocity() * delta;

		// we have to calculate the step we have to take
		float step_y = 0;
		float step_x = 0;

		if (x_movement != 0) {
			step_y = Math.abs(y_movement) / Math.abs(x_movement);
			if (y_movement < 0)
				step_y = -step_y;

			if (x_movement > 0)
				step_x = 1;
			else
				step_x = -1;

			if ((step_y > 1 || step_y < -1) && step_y != 0) {
				step_x = Math.abs(step_x) / Math.abs(step_y);
				if (x_movement < 0)
					step_x = -step_x;
				if (y_movement < 0)
					step_y = -1;
				else
					step_y = 1;
			}
		} else if (y_movement != 0) {
			// if we only have vertical movement, we can just use a step of 1
			if (y_movement > 0)
				step_y = 1;
			else
				step_y = -1;
		}

		// and then do little steps until we are done moving
		while (x_movement != 0 || y_movement != 0) {

			// we first move in the x direction
			if (x_movement != 0) {
				// when we do a step, we have to update the amount we have to move after this
				if ((x_movement > 0 && x_movement < step_x) || (x_movement > step_x && x_movement < 0)) {
					step_x = x_movement;
					x_movement = 0;
				} else
					x_movement -= step_x;

				// then we move the object one step
				obj.setX(obj.getX() + step_x);

				// if (((GameCharacter) obj).isPlayerOOB())
				// System.out.println("out of bounds");

				// if we collide with any of the bounding shapes of the tiles we have to revert
				// to our original position
				if (checkCollision(obj, level.getTiles())) {

					// undo our step, and set the velocity and amount we still have to move to 0,
					// because we can't move in that direction
					obj.setX(obj.getX() - step_x);
					obj.setXVelocity(0);
					x_movement = 0;
				}

			}
			// same thing for the vertical, or y movement
			if (y_movement != 0) {
				if ((y_movement > 0 && y_movement < step_y) || (y_movement > step_y && y_movement < 0)) {
					step_y = y_movement;
					y_movement = 0;
				} else
					y_movement -= step_y;

				obj.setY(obj.getY() + step_y);

				if (obj.getY() < 500) {

					if (checkCollision(obj, level.getTiles())) {
						obj.setY(obj.getY() - step_y);
						obj.setYVelocity(0);
						y_movement = 0;
						break;
					}
				} else {
					if (obj instanceof GameCharacter && obj.getYVelocity() != 0) {
						level.setPlayerOOB(true);
						obj.setYVelocity(0);
						health++;
						y_movement = 0;
					}
				}

			}
		}
	}
}

package Game.Level;

import org.newdawn.slick.Animation;

import Game.Physics.AABoundingRect;
import Game.Physics.BoundingShape;

/**
 * Super class for all level objects
 * 
 * @author Mark
 *
 */
public abstract class LevelObject {

	protected float x;
	protected float y;
	protected BoundingShape boundingShape;

	protected float x_velocity = 0;
	protected float y_velocity = 0;
	protected float maximumFallSpeed = 1;

	protected boolean onGround = true;

	protected Animation animation;

	/**
	 * @param x The x location of the object
	 * @param y The y location of the object
	 */
	public LevelObject(float x, float y) {
		this.x = x;
		this.y = y;

		// default bounding shape is a 32 by 32 box
		boundingShape = new AABoundingRect(x, y, 32, 32);
	}

	/**
	 * applies gravity to a moving object
	 * 
	 * @param gravity the value of gravity that will be applied
	 */
	public void applyGravity(float gravity) {
		// if we aren't already moving at maximum speed
		if (y_velocity < maximumFallSpeed) {
			// accelerate
			y_velocity += gravity;
			if (y_velocity > maximumFallSpeed) {
				// and if we exceed maximum speed, set it to maximum speed
				y_velocity = maximumFallSpeed;
			}
		}
	}

	/**
	 * gets the y velocity
	 * 
	 * @return the value of y velocity
	 */
	public float getYVelocity() {
		return y_velocity;
	}

	/**
	 * sets the y velocity
	 * 
	 * @param f the value for the y velocity
	 */
	public void setYVelocity(float f) {
		y_velocity = f;
	}

	/**
	 * gets the x velocity
	 * 
	 * @return the value of the x velocity
	 */
	public float getXVelocity() {
		return x_velocity;
	}

	/** 
	 * sets the x velocity
	 * 
	 * @param f the value of the x velocity
	 */
	public void setXVelocity(float f) {
		x_velocity = f;
	}

	/**
	 * gets the x value
	 * 
	 * @return the x value
	 */
	public float getX() {
		return x;
	}

	/**
	 * gets the y value
	 * 
	 * @return the y value
	 */
	public float getY() {
		return y;
	}

	/**
	 * sets the value of x
	 * 
	 * @param f the value for x
	 */
	public void setX(float f) {
		x = f;
		updateBoundingShape();
	}

	/**
	 * sets the value of y 
	 * 
	 * @param f the value of y 
	 */
	public void setY(float f) {
		y = f;
		updateBoundingShape();
	}

	/**
	 * changes the position of the bounding shape
	 */
	public void updateBoundingShape() {
		boundingShape.updatePosition(x, y);
	}

	/**
	 * checks if object is on the ground based on collision
	 * 
	 * @return if it is on the ground
	 */
	public boolean isOnGround() {
		return onGround;
	}

	/**
	 * sets the boolean for if object is on ground
	 * 
	 * @param b value for if it on ground
	 */
	public void setOnGround(boolean b) {
		onGround = b;
	}

	/**
	 * gets the bounding shape
	 * 
	 * @return the bounding shape
	 */
	public BoundingShape getBoundingShape() {
		return boundingShape;
	}

	/**
	 * @param offset_x the offset for x based on pixels / delta
	 * @param offset_y the offset for y based on pixels / delta
	 */
	public void render(float offset_x, float offset_y) {
		animation.draw(x - 2 - offset_x, y - 2 - offset_y);
	}

}

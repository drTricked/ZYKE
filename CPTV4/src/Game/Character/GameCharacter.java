package Game.Character;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.BaseGame;
import Game.Enums.Facing;
import Game.Level.LevelObject;

/**
 * A game character
 * 
 * @author Mark Sabado
 */
public abstract class GameCharacter extends LevelObject {
	
	/** The sprites all facing right */
	protected HashMap<Facing, Image> sprites;	
	/** The List for moving animations both left and right */
	protected HashMap<Facing, Animation> movingAnimations;	
	/** Which way the character is moving */
	protected Facing facing;
	
	/** If character is moving */
	protected boolean moving = false;	
	/** The accelerationSpeed */
	protected float accelerationSpeed = 1;	
	/** The decelerationSpeed */
	protected float decelerationSpeed = 1;	
	/** The maximumSpeed */
	protected float maximumSpeed = 1;	


	/**
	 * Creates a game character
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @throws SlickException it can't load the sprite
	 */
	public GameCharacter(float x, float y) throws SlickException {
		super(x, y);
		// in case we forget to set the image, we don't want the game to crash, but it
		// still has to be obvious that something was forgotten
		setSprite(new Image("Player/Idle.png"));

		// default direction will be right
		facing = Facing.RIGHT;

	}

	/**
	 * sets the moving animation
	 * 
	 * @param images The pictures for moving
	 * @param frameDuration The frame duration
	 */
	protected void setMovingAnimation(Image[] images, int frameDuration) {
		movingAnimations = new HashMap<Facing, Animation>();

		// we can just put the right facing in with the default images
		movingAnimations.put(Facing.RIGHT, new Animation(images, frameDuration));

		Animation facingLeftAnimation = new Animation();
		for (Image i : images) {
			facingLeftAnimation.addFrame(i.getFlippedCopy(true, false), frameDuration);
		}
		movingAnimations.put(Facing.LEFT, facingLeftAnimation);

	}

	/**
	 * sets the sprite images
	 * 
	 * @param i The image for the sprite
	 */
	protected void setSprite(Image i) {
		sprites = new HashMap<Facing, Image>();
		sprites.put(Facing.RIGHT, i);
		sprites.put(Facing.LEFT, i.getFlippedCopy(true, false));
	}

	/**
	 * gets if moving
	 * 
	 * @return if it is moving
	 */
	public boolean isMoving() {
		return moving;
	}

	/**
	 * sets moving
	 * 
	 * @param b Boolean
	 */
	public void setMoving(boolean b) {
		moving = b;
	}

	/**
	 *  move towards x_velocity = 0
	 *  
	 * @param delta The number of frames
	 */
	public void decelerate(int delta) {
		if (x_velocity > 0) {
			x_velocity -= decelerationSpeed * delta;
			if (x_velocity < 0)
				x_velocity = 0;
		} else if (x_velocity < 0) {
			x_velocity += decelerationSpeed * delta;
			if (x_velocity > 0)
				x_velocity = 0;
		}
	}

	/**
	 * Applies logic for jumping
	 */
	public void jump() {
		if (onGround)
			y_velocity = -0.5f; // -0.4 == ONE BLOCK, -0.5 = TWO BLOCKS???
	}

	/**
	 * Applies logic for moving left
	 * 
	 * @param delta The number of frames
	 */
	public void moveLeft(int delta) {
		// if we aren't already moving at maximum speed
		if (x_velocity > -maximumSpeed) {
			// accelerate
			x_velocity -= accelerationSpeed * delta;
			if (x_velocity < -maximumSpeed) {
				// and if we exceed maximum speed, set it to maximum speed
				x_velocity = -maximumSpeed;
			}
		}
		moving = true;
		facing = Facing.LEFT;
	}

	/**
	 * Applies logic for moving right
	 * 
	 * @param delta The number of frames
	 */
	public void moveRight(int delta) {
		if (x_velocity < maximumSpeed) {
			x_velocity += accelerationSpeed * delta;
			if (x_velocity > maximumSpeed) {
				x_velocity = maximumSpeed;
			}
		}
		moving = true;
		facing = Facing.RIGHT;
	}

	/* (non-Javadoc)
	 * @see Game.Level.LevelObject#render(float, float)
	 */
	public void render(float offset_x, float offset_y) {

		// draw a moving animation if we have one and we moved within the last 150
		// miliseconds
		if (movingAnimations != null && moving) {
			movingAnimations.get(facing).draw(x - 2 - offset_x, y - 2 - offset_y);
		} else {
			sprites.get(facing).draw(x - 2 - offset_x, y - 2 - offset_y);
		}

	}

}

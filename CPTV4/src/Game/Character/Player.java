package Game.Character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Physics.AABoundingRect;

/**
 * The player class
 * 
 * @author Mark Sabado
 */
public class Player extends GameCharacter {

	/** The Health */
	private int health;
	
//	/** Is he punching */
//	private boolean punching;
//	/** The width of the map */
//	private boolean fireball;

	/**
	 * Creates a player
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @throws SlickException Indicates a failure to load the animations
	 */
	public Player(float x, float y) throws SlickException {
		super(x, y);

		setSprite(new Image("Player/Idle.png"));
		setMovingAnimation(
				new Image[] { new Image("Player/Walk1.png"), new Image("Player/Walk2and5.png"),
						new Image("Player/Walk3.png"), new Image("Player/Walk4.png") },
				125);
		boundingShape = new AABoundingRect(x + 3, y, 26, 46);

		accelerationSpeed = 0.001f;
		maximumSpeed = 0.15f;
		maximumFallSpeed = 0.3f;
		decelerationSpeed = 0.001f;

		health = 3;

//		punching = false;
//		fireball = false;
	}

	/* (non-Javadoc)
	 * @see Game.Level.LevelObject#updateBoundingShape()
	 */
	public void updateBoundingShape() {
		boundingShape.updatePosition(x + 3, y);
	}

	/**
	 * gets the health
	 * 
	 * @return The health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * sets the health
	 * 
	 * @param health The health
	 */
	public void setHealth(int health) {
		this.health = health;
	}


	/**
	 * gets if he dead
	 * 
	 * @return if he dead
	 */
	public boolean isDead() {
		if (health == 0)
			return true;
		else
			return false;
	}

}

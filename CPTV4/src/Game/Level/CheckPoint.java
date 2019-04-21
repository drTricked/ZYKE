package Game.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Physics.AABoundingRect;

/**
 * Object to hold checkpoint logic
 * 
 * @author Mark Sabado
 */
public class CheckPoint extends LevelObject {

	private Animation normalAnimation;
	private Animation clearingAnimation;

	// debugging
	private Image shape;

	private boolean cleared;

	/**
	 * Creates a checkpoint
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @throws SlickException Indicates a failure to load the animations
	 */
	public CheckPoint(float x, float y) throws SlickException {
		super(x, y);
		// add the right animation for this objective

		setCleared(false);

		normalAnimation = new Animation(new Image[] { new Image("CheckPoints/CheckPoint_a1.png"),
				new Image("CheckPoints/CheckPoint_a2.png") }, new int[] { 200, 200 });

		clearingAnimation = new Animation(
				new Image[] { new Image("CheckPoints/CheckPoint_Already_Passed.png") }, new int[] { 200 });

		animation = normalAnimation;

		animation.setPingPong(true);

		boundingShape = new AABoundingRect(x, y, 30, 51);

		// debugging
		shape = new Image("Player/Rectangle.png");

	}

	/**
	 * gets the cleared boolean
	 * 
	 * @return if it is cleared
	 */
	public boolean isCleared() {
		return cleared;
	}

	/**
	 * Sets the cleared boolean
	 * 
	 * @param cleared is it cleared
	 */
	public void setCleared(boolean cleared) {
		this.cleared = cleared;
		animation = clearingAnimation;
	}

	/* (non-Javadoc)
	 * @see Game.Level.LevelObject#render(float, float)
	 */
	public void render(float offset_x, float offset_y) {
		animation.draw(x - 2 - offset_x, y - 2 - offset_y);
		// shape.draw(x, y);
	}

}

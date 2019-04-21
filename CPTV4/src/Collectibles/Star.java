package Collectibles;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Level.LevelObject;

/**
 * Normal collectible star object to represent logic behind star.
 *
 * @author Mark Sabado
 */

public class Star extends LevelObject {

	/**
	 * Create a new Star 
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @throws SlickException Indicates a failure to load the animations
	 */
	public Star(float x, float y) throws SlickException {
		super(x, y);

		// add the right animation for this objective
		animation = new Animation(new Image[] { new Image("Collectibles/Star 1.png"),
				new Image("Collectibles/Star 2.png"), new Image("Collectibles/Star 3.png") },
				new int[] { 200, 200, 200 });

		animation.setPingPong(true);
	}

	/**
	 * Calls the draw method to draw the animation
	 * 
	 * @param offset_x The x location of the object
	 * @param offset_y The y location of the object
	 */
	public void render(float offset_x, float offset_y) {
		animation.draw(x - 2 - offset_x, y - 2 - offset_y);
	}
}
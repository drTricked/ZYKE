package Collectibles;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Level.LevelObject;

/**
 * DreamStar object to represent logic behind star.
 *
 * @author Mark Sabado
 */

public class DreamStar extends LevelObject {

	/**
	 * Create a new Sound 
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 * @throws SlickException Indicates a failure to load the animations
	 */
	public DreamStar(float x, float y) throws SlickException {
		super(x, y);

		// add the right animation for this objective
		animation = new Animation(new Image[] { new Image("Collectibles/Dream_Star_a1.png"),
				new Image("Collectibles/Dream_Star_a2.png"),
				new Image("Collectibles/Dream_Star_a3.png") }, new int[] { 200, 200, 200 });

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

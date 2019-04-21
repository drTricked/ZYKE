package Game.Level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Game.Physics.AABoundingRect;

/**
 * Object representing logic of mushroom enemy
 * 
 * @author Mark Sabado
 */
public class MushroomEnemy extends Enemy {

	
	/**
	 * Creates a mushroom enemy
	 * 
	 * @param x The x location of the object
	 * @param y The y location of the object
	 */
	public MushroomEnemy(float x, float y) {
		super(x, y);
		try {
			animation = new Animation(new Image[] { new Image("Enemies/Mushroom_Enemy_a1.png"),
					new Image("Enemies/Mushroom_Enemy_a2.png") }, new int[] { 1000, 200 });
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		boundingShape = new AABoundingRect(x, y, 24, 16);

	}
	
	/* (non-Javadoc)
	 * @see Game.Level.LevelObject#render(float, float)
	 */
	public void render(float offset_x, float offset_y) {
		animation.draw(x - 2 - offset_x, y - 2 - offset_y);
	}

}

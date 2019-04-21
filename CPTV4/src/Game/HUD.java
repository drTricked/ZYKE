package Game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * HUD for displaying score and health
 * 
 * @author Mark Sabado
 */
public class HUD {

	/** An array of different health PNG */
	private Image[] hud;
	/** The PNG file of the Score HUD */
	private Image scorePNG;
	/** The health */
	private int currentHealth;
	
	/**
	 * Creates a new HUD
	 * 
	 * @throws SlickException it cant load the images
	 */
	public HUD() throws SlickException {
		hud = new Image[] { new Image("HUD/Life_Bar_1.png"), new Image("HUD/Life_Bar_2.png"),
				new Image("HUD/Life_Bar_3.png"), new Image("HUD/Life_Bar_Dead.png") };
		scorePNG = new Image("HUD/Score.png");
		currentHealth = 0;
	}

	public void render() {
		hud[currentHealth].draw(0, 0, 0.25f);   
		scorePNG.draw(0, 32, 0.25f);
	}

	public void update() {

	}

	/**
	 * sets the current health
	 * 
	 * @param currentHealth The health of the player
	 */
	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
		if (this.currentHealth >= 3)
			this.currentHealth = 3;
	}

}

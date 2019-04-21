package Game.GameState;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Game.BaseGame;

/**
 * The state for all the options stuff
 *    
 * @author Mark Sabado
 */
public class OptionState extends BasicGameState {

	/** The image for the help */
	private Image bg;
	/** The image for the background screen */
	private Image bgSquare;
	
	/** The number for which option is being selected */
	private int currentChoice = 0;	
	/** The list of options */
	private String[] options = { "Continue", "Save Game", "High Score", "Quit" };
	
	/** The color red */
	private org.newdawn.slick.Color red;	
	/** The color white */
	private org.newdawn.slick.Color white;
	
	/** The font we use */
	private TrueTypeFont font;
	
	/** Boolean showing if the player is trying to load */
	private boolean loadOption;
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		bg = new Image("Background/Help Menu.png");
		bgSquare = new Image("Background/Big Black Rectangle.png");

		red = new org.newdawn.slick.Color(255, 0, 0); // use this because eclipse has error
		white = new org.newdawn.slick.Color(255, 255, 255); // and uses java.awt instead of slick

		font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 22), true);

		loadOption = false;
	
	}
	

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		bgSquare.draw();
		bg.draw(95, 45, 0.38f);

		g.setFont((org.newdawn.slick.Font) font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(red);
			} else {
				g.setColor(white);
			}

			g.drawString(options[i], 700, 285 + i * 70);

		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		if (loadOption == true) { // play
			loadOption = false;
			if (currentChoice == 0) {
				sbg.enterState(1);
			} else if (currentChoice == 1) { // save game
				((BaseGame) sbg).setSaving(true);
				System.out.println("Game Is saved");
			} else if (currentChoice == 2) { // high score
				sbg.enterState(6);
			} else if (currentChoice == 3) { // quit
				System.exit(0);
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#keyPressed(int, char)
	 */
	public void keyPressed(int key, char code) {
		// if the key is escape, close our application

		if (key == Input.KEY_ENTER) {
			loadOption = true;
		} else if (key == Input.KEY_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		} else if (key == Input.KEY_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		} else if (key == Input.KEY_ESCAPE) {
			System.exit(0);
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return 5;
	}

}

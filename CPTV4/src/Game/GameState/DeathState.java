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

/**
 * The state for when the player dies
 * 
 * @author Mark Sabado
 */
public class DeathState extends BasicGameState {
	private Image died;
	private Image bgSquare;
	private int currentChoice = 0;
	private String[] options = { "Continue", "Quit" };

	private org.newdawn.slick.Color red;
	private org.newdawn.slick.Color white;

	private TrueTypeFont font;

	private boolean loadOption;

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		bgSquare = new Image("Background/Big Black Rectangle.png");
		died = new Image("Background/died.png");

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
		died.draw(250, 200);

		g.setFont((org.newdawn.slick.Font) font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(red);
			} else {
				g.setColor(white);
			}

			g.drawString(options[i], 495, 410 + i * 65);

		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		
		
		if (loadOption == true) { // play
			loadOption = false;
			if (currentChoice == 0) { //continue
				sbg.enterState(1);
			} else if (currentChoice == 1) {
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
		return 7;
	}
}

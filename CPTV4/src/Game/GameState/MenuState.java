package Game.GameState;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Game.BaseGame;

/**
 * A state to hold logic for the menu
 * 
 * @author Mark Sabado
 */
public class MenuState extends BasicGameState {

	private Image bg;

	private int currentChoice = 0;
	private String[] options = { "Start", "Load Game", "High Scores", "Quit" };

	private org.newdawn.slick.Color titleColor;
	private TrueTypeFont titleFont;
	private TrueTypeFont font;

	private org.newdawn.slick.Color black;
	private org.newdawn.slick.Color white;

	private boolean loadOption;

	private Music menuSong;
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		bg = new Image("Background/menubg.jpg");
		black = new org.newdawn.slick.Color(0, 0, 0); // use this because eclipse has error
		white = new org.newdawn.slick.Color(255, 255, 255); // and uses java.awt instead of slick
		titleColor = new org.newdawn.slick.Color(255, 255, 255);

		titleFont = new TrueTypeFont(new Font("Century Gothic", Font.PLAIN, 58), true);
		font = new TrueTypeFont(new Font("Arial", Font.PLAIN, 22), true);

		loadOption = false;
		
		menuSong = new Music("Resources/Sound Clips/Menu.ogg");
		menuSong.play();

	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(BaseGame.SCALE, BaseGame.SCALE);

		bg.draw(0, 0, 0.6f);

		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(BaseGame.getGameTitle(), 420, 170);

		// draw menu options
		g.setFont((org.newdawn.slick.Font) font);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(black);
			} else {
				g.setColor(white);
			}
			if (i == 10) {
				g.drawString(options[i], 428, 285 + i * 34);

			} else {
				g.drawString(options[i], 445, 285 + i * 34);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		if (loadOption == true) { //play
			loadOption = false;
			if (currentChoice == 0) {
				menuSong.stop();
				sbg.enterState(1);
			} else if (currentChoice == 1) {
				menuSong.stop();
				((BaseGame) sbg).setSavedGame(true);
				sbg.enterState(1);
			} else if (currentChoice == 2) { //high scores
				sbg.enterState(6);
			} else if (currentChoice == 3) {
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
		return 0;
	}

}

package Game.GameState;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Supposed to show the high scores
 * 
 * @author Mark Sabado
 */
public class HighScoreState extends BasicGameState {
	/** The Image for background screen color */
	private Image bgSquare;
	private Image highScoreLogo;

	private boolean loadOption;
//	private boolean canSetScore;
//	private boolean setScore;
	
	private int score;
	private File file;
	private Scanner inp;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		bgSquare = new Image("Background/Big Black Rectangle.png");
		highScoreLogo = new Image("HUD/highscore.png");
		try {
			file = new File("SCORE.txt");
			inp = new Scanner(file);
			score = inp.nextInt();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		bgSquare.draw();
		highScoreLogo.draw(300, 250);
		g.setFont(new TrueTypeFont(new Font("Castellar", Font.BOLD, 65), false));
		g.drawString(String.valueOf(score), 540, 400);
	}


	/**
	 * sets the score to print
	 * 
	 * @param score2 the score that is gonna be updated to
	 */
	public void setScore(int score2) {
		score = score2;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		if (loadOption) {
			sbg.enterState(0);
			loadOption = false;
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#keyPressed(int, char)
	 */
	public void keyPressed(int key, char code) {
		// if the key is escape, close our application

		if (key == Input.KEY_ENTER || key == Input.KEY_ESCAPE || key == Input.KEY_SPACE) {
			loadOption = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return 6;
	}

}

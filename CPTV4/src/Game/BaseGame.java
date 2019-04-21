package Game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Game.GameState.DeathState;
import Game.GameState.EndState;
import Game.GameState.HighScoreState;
import Game.GameState.LevelState;
import Game.GameState.MenuState;
import Game.GameState.OptionState;

/**
 * @author Mark Sabado
 *
 */
/**
 * @author Mark Sabado
 *
 */
public class BaseGame extends StateBasedGame {

	/** The Title of the Game */
	private final static String GAME_TITLE = "ZYKE";

	// set the window width and then the height according to a aspect ratio
	/** The window width */
	public static final int WINDOW_WIDTH = 1280;
	/** The window height */
	public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 16 * 9;
	/** Boolean for if game is in fullscreen */
	public static final boolean FULLSCREEN = false;
	/** Determines if the game is a saved game */
	private boolean savedGame;
	/** Determines if the player is trying to save */
	private boolean isSaving;

	// 1280x720 is our base, we use 32x32 tiles but we want it to be 40x40 at
	// 1280x720
	// so our base scale is not 1 but 1.25 actually (1.25 allows 40 x 40, 1280 / 40
	// = 32, 720 / 40 = 18
	// 32 Tiles wide and 18 tiles high at 1280 x 720
	public static final float SCALE = (float) (1.25 * ((double) WINDOW_WIDTH / 1280));

	/**
	 * Creates a BaseGame
	 *
	 * @author Mark Sabado
	 */
	
	public BaseGame() {
		super(GAME_TITLE);
		
		savedGame = false;
	}
	
	/**
	 * Gets if is saved Game
	 * 
	 * @return savedGame
	 */
	public boolean isSavedGame() {
		return savedGame;
	}
	
	/**
	 * gets if is currently saving
	 * 
	 * @return isSaving
	 */
	public boolean isSaving() {
		return isSaving;
	}

	/**
	 * sets the saving boolean
	 * 
	 * @param isSaving boolean for if game is gonna save
	 */
	public void setSaving(boolean isSaving) {
		this.isSaving = isSaving;
	}

	/**
	 * sets the saved game boolean
	 * 
	 * @param savedGame boolean for if game is a saved game
	 */
	public void setSavedGame(boolean savedGame) {
		this.savedGame = savedGame;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.StateBasedGame#initStatesList(org.newdawn.slick.GameContainer)
	 */
	public void initStatesList(GameContainer gc) throws SlickException {

		// create a level state, this state will do the whole logic and rendering for
		// individual level
		// addState(new LevelState("Level1"));
		addState(new MenuState());
		addState(new OptionState());
		//addState(new LevelState("/Level 1/Level 2/W1L2"));
		addState(new HighScoreState());
		addState(new LevelState("/Level 2/W2L1"));
		// addState(new LevelState("p"));
		addState(new DeathState());
		addState(new EndState());
		this.enterState(0);

	}

	/**
	 * @return GAME_TITLE
	 */
	public static String getGameTitle() {
		return GAME_TITLE;
	}

	public static void main(String[] args) {
		AppGameContainer app;

		try {
			app = new AppGameContainer(new BaseGame());
			app.setDisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT, false);
			app.setTargetFrameRate(60); // goal of 60 fps
			app.setShowFPS(false);
			app.start();

		} catch (SlickException e1) {
			e1.printStackTrace();
		}

	}

}

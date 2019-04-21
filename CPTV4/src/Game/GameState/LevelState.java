package Game.GameState;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Controls.MouseAndKeyboardController;
import Controls.PlayerController;
import Game.BaseGame;
import Game.HUD;
import Game.HighScore;
import Game.Character.Player;
import Game.Level.Level;
import Game.Physics.Physics;

/**
 * The state for all the level logic 
 * 
 * @author Mark
 *
 */
public class LevelState extends BasicGameState {

	private Level level;
	protected Player player;
	private PlayerController playerController;
	protected Physics physics;
	private String[] levelList;
	private int currLevel;

	private int score;
	private int oldScore;
	private int health;

	protected HUD hud;
	protected HighScore highscore;

	private Music music;
	private Sound deathSound;

	private boolean loadOptions;
//	private boolean savedGame;

	private File savedGameFile;
	private File highScoresFile;
	private Scanner inp;
	private Scanner inp2;
	private FileWriter fw;
	private BufferedWriter bw;
	private FileWriter fw2;
	private BufferedWriter bw2;

	/**
	 * creates a new level state
	 * 
	 * @param startingLevel the level name for when the state is first created
	 */
	public LevelState(String startingLevel) {
	}

	/**
	 * gets the score
	 * 
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * updates the score value
	 * 
	 * @param score the new score value
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * gets the health
	 * 
	 * @return the health of the player
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * sets the health value
	 * 
	 * @param health the new health value
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		// fileIo
		try {
			savedGameFile = new File("SavedGame.txt");
			inp = new Scanner(savedGameFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// once we initialize our level, we want to load the right level
		player = new Player(128, 50);

		// hardcoded for now
		levelList = new String[] { "/Level 2/W2L1", "/Level 2/W1L3", "/Level 2/W1L4", "/Level 2/W1L5" };

		currLevel = 1;

		level = new Level(levelList[currLevel - 1], player);

		level.addCharacter(player);

		// create a controller
		playerController = new MouseAndKeyboardController(player);
		physics = new Physics();

		hud = new HUD();
		highscore = new HighScore();

		music = new Music("Resources/Sound Clips/Peaceful .ogg");
		deathSound = new Sound("Resources/Sound Clips/Die 1.ogg");

		loadOptions = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		playerController.handleInput(container.getInput(), delta);
		physics.handlePhysics(level, delta);

		//checks if possible to enter next level
		if (level.getEnterNextLevel() == true) {
			level.setEnterNextLevel(false);
			currLevel++;
			if (currLevel == 4) { // there is no level 4
				music.pause();
				sbg.enterState(3);
			} else {
				if (currLevel == 4) {
					currLevel = 1;
					level = new Level(levelList[currLevel - 1], player);
					player.setX(128);
					player.setY(128);
					level.setLastCheckpointX(128);
					level.setLastCheckpointY(128);
					level.addCharacter(player);
				} else {
					level = new Level(levelList[currLevel - 1], player);
					player.setX(128);
					player.setY(128);
					level.setLastCheckpointX(128);
					level.setLastCheckpointY(128);
					level.addCharacter(player);
				}
			}
		}

		//plays music if not playing
		if (sbg.getCurrentStateID() == 1 && music.playing() == false && currLevel != 4) {
			music.play(1, 0.7f);
		}

		//updates variables
		score = physics.getScore();
		health = physics.getHealth();
		hud.setCurrentHealth(health);
		highscore.setScore(score);

		//checks if player went oob or health is gone
		if (health >= 3 || level.isPlayerOOB()) {
			music.pause();
			deathSound.play();
			player.setX(level.getLastCheckpointX());
			player.setY(level.getLastCheckpointY());
			level.setPlayerOOB(false);

			if (health >= 3) {
				music.pause();
				//updating high score
				try {
					highScoresFile = new File("SCORE.txt");
					inp2 = new Scanner(highScoresFile);
					oldScore = inp2.nextInt();

					if (oldScore < score) {
						fw2 = new FileWriter(highScoresFile, false);
						bw2 = new BufferedWriter(fw2);
						bw2.write(String.valueOf(score));
						bw2.close();
						fw2.close();
					}

					((HighScoreState) sbg.getState(6)).setScore(score);

					System.out.println("PRINTED");

				} catch (IOException e) {
					e.printStackTrace();
				}
				sbg.enterState(3); // no health left
			} else {
				sbg.enterState(7); // health lost
			}
		}

		//checks if player is trying to load options
		if (loadOptions == true) {
			loadOptions = false;
			sbg.enterState(5);
		}

		//checks if game is a saved game
		if (((BaseGame) sbg).isSavedGame()) {
			currLevel = inp.nextInt();
			player.setX(inp.nextInt());
			player.setY(inp.nextInt());
			health = inp.nextInt();
			physics.setHealth(health);
			score = inp.nextInt();
			physics.setScore(score);
			level = new Level(levelList[currLevel - 1], player);
			level.addCharacter(player);
			((BaseGame) sbg).setSavedGame(false);
			try {
				inp = new Scanner(savedGameFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}

		//checks if player tried to save
		if (((BaseGame) sbg).isSaving()) {
			try {
				savedGameFile = new File("SavedGame.txt");
				fw = new FileWriter(savedGameFile);
				bw = new BufferedWriter(fw);

			} catch (IOException e) {
				e.printStackTrace();
			}

			if (savedGameFile.exists()) {
				savedGameFile.delete();
			}

			try {
				//printing variables to textfile for saved game
				bw.write(String.valueOf(currLevel));
				bw.newLine();
				bw.write(String.valueOf((int) player.getX()));
				bw.newLine();
				bw.write(String.valueOf((int) player.getY()));
				bw.newLine();
				bw.write(String.valueOf(health));
				bw.newLine();
				bw.write(String.valueOf(score));
				bw.close();
				fw.close();
				System.out.println(score);

			} catch (IOException e) {
				e.printStackTrace();
			}

			((BaseGame) sbg).setSaving(false);
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
		g.scale(BaseGame.SCALE, BaseGame.SCALE);
		level.render();
		hud.render();
		g.drawString(String.valueOf(score), 82, 45);
	}

	// this method is overriden from basicgamestate and will trigger once you press
	// any key
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#keyPressed(int, char)
	 */
	public void keyPressed(int key, char code) {
		// if the key is escape, close our application
		if (key == Input.KEY_ESCAPE) {
			loadOptions = true;
		}
	}

	/**
	 * gets the current level number
	 * 
	 * @return the current level number
	 */
	public int getCurrLevel() {
		return currLevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return 1;
	}

}

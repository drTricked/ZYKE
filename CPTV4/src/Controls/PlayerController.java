package Controls;

import org.newdawn.slick.Input;

import Game.Character.Player;

/**
 * Abstract class holding logic for playerControllers
 *
 * @author Mark Sabado
 */

public abstract class PlayerController {
	/** player used for logic */
	protected Player player;

	/**
	 * Create a new PlayerController
	 *
	 * @param player the player object that the keyinput applies logic to
	 */
	public PlayerController(Player player) {
		this.player = player;
	}

	/**
	 * Source method to handleKeyBoard Input
	 * 
	 * @param i The input for input
	 * @param delta The number of frames
	 */
	public abstract void handleInput(Input i, int delta);

}
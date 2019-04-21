package Controls;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Game.Character.Player;

/**
 * A controller to handle input from Mouse and Keyboard
 *
 * @author Mark Sabado
 */

public class MouseAndKeyboardController extends PlayerController {

	/** The sound for when jumping */
	private Sound jumpSound;
	
	/**
	 * Create a new MouseAndKeyboardController 
	 * 
	 * @param player the player object that the keyinput applies logic to
	 */
	public MouseAndKeyboardController(Player player) {
		super(player);
		
		try {
			jumpSound = new Sound("Resources/Sound Clips/jump_2.ogg");
		} catch (SlickException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Source method to handleKeyBoard Input
	 * 
	 * @param inp The input for input
	 * @param delta The number of frames
	 */
	public void handleInput(Input inp, int delta) {
		// handle any input from the keyboard
		handleKeyboardInput(inp, delta);
	}

	/**
	 * Source method to handleKeyBoard Input
	 * 
	 * @param inp The input for input
	 * @param delta The number of frames
	 */
	private void handleKeyboardInput(Input inp, int delta) {
		// we can both use the WASD or arrow keys to move around, obviously we can't
		// move both left and right simultaneously
		if (inp.isKeyDown(Input.KEY_A) || inp.isKeyDown(Input.KEY_LEFT)) {
			player.moveLeft(delta);
		} else if (inp.isKeyDown(Input.KEY_D) || inp.isKeyDown(Input.KEY_RIGHT)) {
			player.moveRight(delta);
		} else {
			player.setMoving(false);
		}

//		if (inp.isKeyDown(Input.KEY_R)) {
//			player.setPunching(true);
//		} else if (inp.isKeyDown(Input.KEY_E)) {
//			player.setFireball(true);
//		}

		if (inp.isKeyDown(Input.KEY_SPACE) || inp.isKeyDown(Input.KEY_UP) || inp.isKeyDown(Input.KEY_W)) {
			player.jump();
			//jumpSound.play();
		}

	}

}
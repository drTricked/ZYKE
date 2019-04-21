package Game.Level;

/**
 * Exception if tile layer returns -1
 * @author Mark Sabado
 *
 */
@SuppressWarnings("serial")
public class LayerNotExistException extends Exception{
	
	public LayerNotExistException() {
		super("This layer doesn't exist.");
	}
}

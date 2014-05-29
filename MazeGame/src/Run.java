/**
 * Runs the maze game.
 * Logic behind screens shown is dealt by Game.
 * @see Game
 */
public class Run {
	
	/**
	 * Makes a new game.
	 * It regularly queries Game for updates in game state.
	 * @param args unused
	 */
	public static void main (String args[]) {
		GameManager gameManager = new GameManager();
		gameManager.run();
	}
}

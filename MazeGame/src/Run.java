/**
 * The Maze Runner
 * (May 2014).
 * Runs the maze game.
 * Logic behind screens shown is dealt by GameManager.
 * @author Mohammad Ghasembeigi, Gavin Tam, Jeremy Ma, Calvin Ko
 * @see GameManager
 */
public class Run {	
	/**
	 * Makes a new game manager which manages the GUI.
	 * It regularly queries Game for updates in game state.
	 * @param args unused
	 */
	public static void main (String args[]) {
		GameManager gameManager = new GameManager();
		gameManager.run();	//run the game manager
	}
}

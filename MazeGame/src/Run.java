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
		//Make new game
		Game g = new Game();
		while (true) {
			//If not in game, do nothing (wait for play button to be clicked)
			if (!g.isInGame())
				continue;
			
			//Once made, initialise the frame of the game
			g.initMazeFrame();			//initialise maze
			
			long lastUpdateTime = System.currentTimeMillis();		
			//While still in game
			while (!g.isGameOver()) {
				if (System.currentTimeMillis() - lastUpdateTime > 20) {	//update every 20ms
					 g.updateMazeFrame(); //update maze
					 g.updateScore();	//update player score
				
					 lastUpdateTime = System.currentTimeMillis(); //update time
				}
			}
			//We have finished game, show game UI and repeat
			g.showUI();	
		}
	}
}

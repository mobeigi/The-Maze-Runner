
public class Run 
{
	public static void main (String args[])
	{
		//Make game
		Game g = new Game();
		
		//Make 1 player
		Player player = new Player("Default", "link");
		g.setPlayer(player);		//set player
		
		while (true)
		{
			//If not in game, do nothing (wait for play button to be clicked)
			if (!g.isInGame())
				continue;
			
			//Once made, init the frame of the game
			g.initMazeFrame();			//init maze
			g.setController(new Controller(g));
			g.setMazeKeyListener();
			
			long lastUpdateTime = System.currentTimeMillis();
			
			//While still in game
			while (!g.isGameOver()) 
			{
				if (System.currentTimeMillis() - lastUpdateTime > 50) {	//update every 250ms
					 g.updateMazeFrame(); //update maze
					 g.getMazeFrame().repaint(); //paint
					 g.updateScore();
					 
					 //If level is complete
					 if (g.getMaze().exitedMaze()) {
						 g.nextLevel();
						 if (g.getLevel() == Game.MAX_LEVEL) {
							 g.setIsGameOver(true);	//end game if passed all levels
							 g.setIsInGame(false);
						 } else {
							 g.createMaze(g.getLevel());
							 g.setMazeKeyListener();
						 }
					 }
				
					 lastUpdateTime = System.currentTimeMillis(); //update time
				}
				
			}
			
			//We have finished game, show game UI and repeat
			g.showUI();	
		}
	}
}

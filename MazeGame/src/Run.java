
public class Run 
{
	public static void main (String args[])
	{
		//Make game
		Game g = new Game();
		
		//Make 1 player
		Player player = new Player("Test User #17", "link");
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
			
			//While still in game
			while (!g.isGameOver()) 
			{
				//Do nothing as we wait for game to be completed
			}
			
			//We have finished game, show game UI and repeat
			g.showUI();
			
		}
		
		
		
	}

}

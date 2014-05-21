
public class Run 
{
	public static void main (String args[])
	{
		//Make game
		Game g = new Game();
		
		//Make 1 player
		Player player = new Player("joe", "link");
		
		while (true)
		{
			System.out.flush(); //Needed WTF???
			
			//If not in game, do nothing (wait for play button to be clicked)
			if (!g.isInGame())
				continue;
			
			//Once made, init the frame of the game
			g.initMazeFrame();
			g.setMazeKeyListerner();	//set up a new controller
			g.setPlayer(player);		//set player
			
			//While still in game
			while (!g.isGameOver()) 
			{
				//Do nothing
			}
			
			//We have left game
			g.showUI();
			
		}
		
		
		
	}

}

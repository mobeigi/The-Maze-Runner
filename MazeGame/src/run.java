import java.util.Scanner;

public class run 
{
	public static void main (String args[])
	{
		
		GameFrame GF = new GameFrame(30,30);
		
		Maze m = new Maze(30, 30);
		m.createMaze();
		
		long second = 0;
		

		while (true) 
		{
			System.out.flush(); //flush buffer
			
			if (GF.isMazeSet())
			{
				if (System.currentTimeMillis() - second >= 5000) 
				{
					GF.getMazeFrame().draw(m);
					second = System.currentTimeMillis();
				}
			} 
		}
		
		
		
		/*
		Game g = new Game();
		
		while (g.playAgain)
		{
			//g.createGame();
			
			while (!g.isGameOver())
			{
				//g.updateGame();
				//g.drawGame();
			}
			
			Scanner s = new Scanner(System.in);
			
			//if (s.next().equalsIgnoreCase("Y") || s.next().equalsIgnoreCase("YES"))
				//g.setPlayAgain(s.next());
		}
		
		*/
	}

}


public class Run 
{
	public static void main (String args[])
	{
		/*
		
		Maze m = new Maze(13, 13);
		
		MazeFrame MF = new MazeFrame(13, 13);
		Player p = new Player("joe", "link");
		Controller c = new Controller(m, MF, p);
		MF.addKeyListener(c);
		
		long second = 0;
		
		int x = 1;
		int y = 1;
		
		Tile old, t;
		
		//Init first maze
		MF.init(m);
		
		
		
		//This while loop can be used to redraw maze every X ms if needed
		//Otherwise it would manipulate game class to ask user to play again, progress levels, etc
		while (true) 
		{
			if (System.currentTimeMillis() - second >= 125) 
			{
				
				/* random move player
			
			double random = Math.random();
			
			if (random <=0.25) {
				x++;
				if (x>=0 && y>=0 && !m.grid[x][y].isWalkable()) {
					x--;
					continue;
				}
			} else if (random <= 0.5) {
				y++;
				if (x>=0 && y>=0 && !m.grid[x][y].isWalkable()) {
					y--;
					continue;
				}
			} else if (random <= 0.75){
				x--;
				if (x>=0 && y>=0 && !m.grid[x][y].isWalkable()) {
					x++;
					continue;
				}
			} else {
				y--;
				if (x>=0 && y>=0 && !m.grid[x][y].isWalkable()) {
					y++;
					continue;
				}
			}
 			
			
				
				old = new Tile(true, m.playerLoc.getX(), m.playerLoc.getY());
				m.grid[m.playerLoc.getX()][m.playerLoc.getY()] = old;
				
				t = new Tile(true, x, y);
				m.playerLoc = t;
				
				
				
				//MF.init(m);
				//MF.repaint();
				
				second = System.currentTimeMillis();
			} 
		}
		
		*/
		
		
		Game g = new Game();
		
		while (true)
		{
			//Make new GameFrame
			
			while (!g.isInGame()) //While not in a game, wait for actions
			{
				//Do nothing
			}
			
			//Now we are in game, update maze frame until game is over
			while (!g.isGameOver()) 
			{
				
			}
			
			//We have left game
			g.showUI();
			
			
		}
		
		
		
	}

}

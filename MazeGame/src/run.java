import java.util.Scanner;

public class run 
{
	public static void main (String args[])
	{
		
		Maze m = new Maze(15,15);
		m.createMaze();
		
		MazeFrame MF = new MazeFrame(17, 17);
		Player p = new Player("joe", "link");
		Controller c = new Controller(m, p);
		MF.addKeyListener(c);
		
		long second = 0;
		
		int x = 1;
		int y = 1;
		
		Tile old, t;
		
		while (true) 
		{
			System.out.flush(); //flush buffer
			
			if (System.currentTimeMillis() - second >= 100) 
			{
				
				/*
			
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
				
				*/
				MF.update(m);
				MF.repaint();
				
				second = System.currentTimeMillis();
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

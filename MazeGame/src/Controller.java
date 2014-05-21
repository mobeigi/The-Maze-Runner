import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;



public class Controller implements KeyListener {
	private Maze maze;
	private Player p1;
	private Player p2;
	private MazeFrame mazeFrame;
	private Game game;
	
	
	public Controller(Maze m, MazeFrame mazeFrame, Player p1, Game g){
		this.p1 = p1;
		this.maze = m;
		this.mazeFrame = mazeFrame;
		this.game = g;
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		 double dx = 0;
		 double dy = 0;
		 
		 if (e.getKeyCode() == KeyEvent.VK_D){
			 System.out.println("right");
			 dx = 1;
			 dy = 0;
		 } else if (e.getKeyCode() == KeyEvent.VK_A){
			 System.out.println("left");
			 dx = -1;
			 dy = 0;
		 } else if (e.getKeyCode() == KeyEvent.VK_W) {
			 System.out.println("up");
			 dx = 0;
			 dy = -1;
		 } else if (e.getKeyCode() == KeyEvent.VK_S){
			 System.out.println("down");
			 dx = 0;
			 dy = +1;
		 } else {	//Ignore other presses
			 return;
		 }
		 
		 //Update the player location
		 if (maze.isValid((int)dx,(int)dy)) {
			 maze.updatePlayerLoc((int)dx, (int)dy);
			 mazeFrame.init(maze);	//update maze
			 mazeFrame.repaint(); //paint
		 }
		 if (maze.exitedMaze()){
			 System.out.println("hello!");
			 game.setIsGameOver(true);
			 game.setIsInGame(false);
		 }
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

}

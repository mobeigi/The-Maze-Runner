import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;



public class Controller implements KeyListener {
	private Maze m;
	private Player p1;
	private Player p2;
	private MazeFrame mazeFrame;
	
	
	public Controller(Maze m, MazeFrame mazeFrame, Player p1){
		this.p1 = p1;
		this.m = m;
		this.mazeFrame = mazeFrame;
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		 double dx = 0;
		 double dy = 0;
		 if (e.getKeyCode() == KeyEvent.VK_D){
			 System.out.println("right");
			 dx = 1;
			 dy = 0;
			// s.translate((int) dx, (int) dy);
		 } else if (e.getKeyCode() == KeyEvent.VK_A){
			 System.out.println("left");
			 dx = -1;
			 dy = 0;
			 //s.translate((int) dx, (int) dy);
		 } else if (e.getKeyCode() == KeyEvent.VK_W) {
			 System.out.println("up");
			 dx = 0;
			 dy = -1;
			// s.translate((int) dx, (int) dy);
		 } else if (e.getKeyCode() == KeyEvent.VK_S){
			 System.out.println("down");
			 dx = 0;
			 dy = +1;
			// s.translate((int) dx, (int) dy);
		 } else {
			// System.out.println("Key Pressed!!!");
			 return;
		 }
		 
		 //Update the player location
		 if (m.isValid((int)dx,(int)dy)){
			 m.updatePlayerLoc((int)dx, (int)dy);
			 mazeFrame.init(m);	//update maze
			 mazeFrame.repaint(); //paint
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

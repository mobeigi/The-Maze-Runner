import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;



public class Controller implements KeyListener {
	private Game game;
	
	
	public Controller(Game g){
		this.game = g;
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		 int dx = 0;
		 int dy = 0;
		 
		 if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT){
			 //System.out.println("right");
			 dx = 1;
			 dy = 0;
		 } else if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT){
			 //System.out.println("left");
			 dx = -1;
			 dy = 0;
		 } else if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			 //System.out.println("up");
			 dx = 0;
			 dy = -1;
		 } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN){
			 //System.out.println("down");
			 dx = 0;
			 dy = +1;
		 } else {	//Ignore other presses
			 return;
		 }
		 
		 //Update the player location
		 if (game.getMaze().isValid(dx, dy)) {
			 game.getMaze().updatePlayerLoc(dx, dy);
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

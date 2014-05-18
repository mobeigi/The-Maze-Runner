import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Player {
	private String name;
	private int x;
	private int y;
	private Maze maze;
	
	
	public Player(String name, Maze maze){
		this.name = name;
		this.maze = maze;
	}
	
	//returns the name object
	public String getName(){
		return name;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	
	public void draw(Graphics2D g2){
		// make the player an ellipse for now
		//have to call repaint to actually display the player
		Ellipse2D.Double player = new Ellipse2D.Double(x,y,5,5);
		g2.draw(player);
	}
	
	public void translate(int dx, int dy){
		int tempx = x+dx;
		int tempy = y+dy;
		if (maze.isValid(tempx,tempy)){
			x = tempx;
			y = tempy;
		}
	}
	
	
	
	
}

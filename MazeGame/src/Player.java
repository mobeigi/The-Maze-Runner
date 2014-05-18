
public class Player {
	private String name;
	private int x;
	private int y;
	
	public Player(String name){
		this.name = name;
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
}

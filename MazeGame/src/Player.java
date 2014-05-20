
public class Player {
	private String name;
	private String character;
	private int x;
	private int y;
	
	
	public Player(String name, String character){
		this.name = name;
		this.character = character;
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

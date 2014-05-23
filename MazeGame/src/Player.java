
public class Player {
	private String name;
	private String character;
	private int x;
	private int y;

	public Player(String name, String character){
		this.name = name;
		this.character = character;
	}
	
	//Return the name of the player
	public String getName(){
		return this.name;
	}
	
	//Return the character this player has selected
	public String getCharacter(){
		return this.character;
	}
	
	//Return the character this player has selected
	public void setCharacter(String character){
		this.character = character;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}

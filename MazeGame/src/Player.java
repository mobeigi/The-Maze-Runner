
public class Player {
	private String name;
	private String character;
	private Tile location;
	private boolean isDead;

	public Player(String name, String character){
		this.name = name;
		this.character = character;
	}
	
	public Player() {
		//nothing, used by maze when character and name is not important
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
	
	public Tile getLocation () {
		return location;
	}
	
	public void setLocation (Tile t) {
		location = t;
	}
	
	public void setDead () {
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}
}

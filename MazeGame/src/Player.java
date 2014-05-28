import java.util.ArrayList;

/**
 * Player of the maze game.
 * @author Gavin Tam
 */
public class Player {
	private String name;
	private String character;
	private Tile location;
	private boolean isDead;
	
	private ArrayList<Boolean> inventory;
	private int numTreasureCollected;
	private int enemyKilled;
	
	public static final int KEY = 0;
	public static final int SWORD = 1;
	public static final int ICE_POWER = 2;
	public static final int NUM_INVENTORY_ITEMS = 3;

	public Player(String name, String character){
		this.name = name;
		this.character = character;
		inventory = new ArrayList<Boolean>();
		for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
			inventory.add(false);	//no item at start
		}
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
	
	public void setDead (boolean dead) {
		isDead = dead;
	}

	public boolean isDead() {
		return isDead;
	}

	public boolean isItemCollected(int itemNum) {
		return inventory.get(itemNum);
	}

	public void setItemCollected(int itemNum, boolean collected) {
		inventory.set(itemNum,collected);
	}

	public int getNumTreasureCollected() {
		return numTreasureCollected;
	}

	public void addNumTreasureCollected() {
		this.numTreasureCollected++;
	}
	
	public int getEnemyKilled() {
		return enemyKilled;
	}

	public void addEnemyKilled() {
		this.enemyKilled++;
	}
	
	public void clearStats() {
		location = null;
		isDead = false;	
		for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
			inventory.set(i,false);	//clear inventory
		}
		numTreasureCollected = 0;
		enemyKilled = 0;
	}
	
	public void setName(String name){
		this.name = name;
	}
}

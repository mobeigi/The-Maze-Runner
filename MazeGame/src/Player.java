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
	
	private static final int KEY = 0;
	private static final int SWORD = 1;
	private static final int ICE_POWER = 2;
	private static final int NUM_INVENTORY_ITEMS = 3;

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

	public boolean isKeyCollected() {
		return inventory.get(KEY);
	}

	public void setKeyCollected(boolean keyCollected) {
		inventory.set(KEY,keyCollected);
	}

	public boolean isSwordCollected() {
		return inventory.get(SWORD);
	}

	public void setSwordCollected(boolean swordCollected) {
		inventory.set(SWORD,swordCollected);
	}
	
	public boolean isIcePowerCollected() {
		return inventory.get(ICE_POWER);
	}

	public void setIcePowerCollected(boolean icePowerCollected) {
		inventory.set(ICE_POWER,icePowerCollected);
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
}

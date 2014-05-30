import java.util.ArrayList;

/**
 * Player of the maze game.
 * Contains all player information including name,
 * character, location, status (dead or alive), inventory items,
 * and statistics involving number of enemies killed and number of
 * treasure collected.
 * @author Gavin Tam
 */
public class Player {
	private String name;
	private String character;
	private Tile location;
	private boolean isDead;	//status, determines if enemy has killed player or not
	
	private ArrayList<Boolean> inventory;	//inventory with items ordered by item number
	private int numTreasureCollected;
	private int enemyKilled;
	
	//describes the item number of each item in the inventory
	/**Constant for key identifying position in inventory.*/
	public static final int KEY = 0;
	/**Constant for sword identifying position in inventory.*/
	public static final int SWORD = 1;
	/**Constant for ice power identifying position in inventory.*/
	public static final int ICE_POWER = 2;
	/**Constant for number of items in inventory.*/
	public static final int NUM_INVENTORY_ITEMS = 3; //total number of inventory items available

	/**
	 * Constructor for a player of the maze.
	 * @param name the name of the player.
	 * @param character the character chosen by the player.
	 * Character options are shown in the option panel.
	 */
	public Player(String name, String character){
		this.name = name;
		this.character = character;
		inventory = new ArrayList<Boolean>();
		for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
			inventory.add(false);	//no item at start
		}
	}
	
	/**
	 * Gets the name of the player.
	 * @return the name of the player.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Sets the name of the player.
	 * @param name the desired name for the player.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Gets the character chosen by the player.
	 * Characters are chosen at the start of the game.
	 * @return the name of the character.
	 */
	public String getCharacter(){
		return this.character;
	}
	
	/**
	 * Set the character chosen by the player.
	 * Characters are chosen at the start of the game.
	 * @param character the character chosen by the player.
	 */
	public void setCharacter(String character){
		this.character = character;
	}
	
	/**
	 * Get the current location of the player in the maze.
	 * @return the tile where the player is currently located.
	 */
	public Tile getLocation () {
		return location;
	}
	
	/**
	 * Update the location of the player in the maze.
	 * @param t the tile where the player location is to be updated to.
	 */
	public void setLocation (Tile t) {
		location = t;
	}
	
	/**
	 * Update the status of the player.
	 * The player can be dead or alive.
	 * @param dead if true, the player is set to dead;
	 * if false, the player is set to alive.
	 */
	public void setDead (boolean dead) {
		isDead = dead;
	}

	/**
	 * Checks the status of the player.
	 * @return true if the player is dead.
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * Checks if an item has been collected by player.
	 * Items that expire their time limit will not
	 * exist in the player's inventory.
	 * @param itemNum the ID of the item defined in Player.
	 * @return true if the item is in the player's inventory.
	 */
	public boolean isItemCollected(int itemNum) {
		return inventory.get(itemNum);
	}

	/**
	 * Update whether an item is collected or not.
	 * @param itemNum the ID of the item defined in Player.
	 * @param collected if true, the item is set to collected;
	 * if false, the item is set to not collected.
	 */
	public void setItemCollected(int itemNum, boolean collected) {
		inventory.set(itemNum,collected);
	}

	/**
	 * Get the total number of treasure the player has collected in the game.
	 * This includes those collected in all levels of the game.
	 * @return the total number of treasure collected by the player in the game.
	 */
	public int getNumTreasureCollected() {
		return numTreasureCollected;
	}

	/**
	 * Increase the total number of treasure collected by the player by one.
	 */
	public void addNumTreasureCollected() {
		this.numTreasureCollected++;
	}
	
	/**
	 * Get the total number of enemies killed by the player.
	 * This includes those killed in all levels of the game.
	 * @return the total number of enemies killed by the player.
	 */
	public int getEnemyKilled() {
		return enemyKilled;
	}

	/**
	 * Increase the total number of enemies killed by the player by one.
	 */
	public void addEnemyKilled() {
		this.enemyKilled++;
	}
	
	/**
	 * Clear all player stats, including location, status, inventory,
	 * number of treasure collected, and number of enemies killed.
	 */
	public void clearStats() {
		location = null;
		isDead = false;	
		clearInventory();
		numTreasureCollected = 0;
		enemyKilled = 0;
	}
	
	/**
	 * Clear all inventory items of the player.
	 */
	public void clearInventory() {
		for (int i = 0; i < NUM_INVENTORY_ITEMS; i++) {
			inventory.set(i,false);	//clear each item
		}
	}
}

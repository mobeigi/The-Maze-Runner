/**
 * Enemy against player of the maze.
 * The enemy is able to kill the player under
 * circumstances outlined in Maze.
 * @author Gavin Tam
 *
 */
public class Enemy {
	boolean isDead;	//status (dead or alive)
	Tile location;	//where enemy is located in the maze
	
	/**
	 * Constructor for enemy.
	 * Creates an enemy on a specific tile on the maze.
	 * @param t a tile in the maze.
	 */
	public Enemy (Tile t) {
		location = t;
	}
	
	/**
	 * Empty constructor for enemy.
	 * Creates an enemy (with default field values)
	 * if location is not decided yet.
	 */
	public Enemy () {
		//nothing, used by maze when no location is decided yet
	}
	
	/**
	 * Updates the status of the enemy.
	 * @param dead if true, the enemy is now dead,
	 * else, the enemy is alive.
	 */
	public void setDead (boolean dead) {
		isDead = dead;
	}

	/**
	 * Checks if the enemy is dead.
	 * @return true if the enemy is dead.
	 */
	public boolean isDead() {
		return isDead;
	}
	
	/**
	 * Gets the location of the enemy in the maze.
	 * @return the tile where the enemy is located.
	 */
	public Tile getLocation () {
		return location;
	}
	
	/**
	 * Update the location of the enemy in the maze.
	 * @param t the tile where the enemy is to be located.
	 */
	public void setLocation (Tile t) {
		location = t;
	}
}

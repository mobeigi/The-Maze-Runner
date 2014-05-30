
/**
 * Tile Class for maze representation.
 * Contains constants for its type.
 * @author Calvin Ko, Gavin Tam
 *
 */
public class Tile {
	//Public constants for tile type
	/**Constant for wall tile type.*/
	public static final int WALL = 0;
	/**Constant for path tile type.*/
	public static final int PATH = 1;
	/**Constant for key tile type.*/
	public static final int KEY = 2;
	/**Constant for treasure tile type.*/
	public static final int TREASURE = 3;
	/**Constant for door tile type.*/
	public static final int DOOR = 4;
	/**Constant for sword tile type.*/
	public static final int SWORD = 5;
	/**Constant for ice power tile type.*/
	public static final int ICE_POWER = 6;
	
	private int x;	//coordinates of the tile
	private int y;
	private boolean isWalkable;	//walkability
	private int type;	//tile type
	
	/**
	 * Constructor for Tile, takes in its walkability and its x-y coordinates.
	 * The default type is a wall.
	 * @param isWalkable whether or not the player can walk over the tile.
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile (boolean isWalkable, int x, int y){
		this.x = x;
		this.y = y;
		this.isWalkable = isWalkable;
		this.type = WALL; 	//default type is WALL
	}
	
	/**
	 * Empty Constructor for Tile.
	 * Used when no coordinates, type or walkablility for the tile has been decided.
	 */
	public Tile() {
		//do nothing
	}
	
	/**
	 * Gets the type of the tile.
	 * Constants representing the tile type are defined in the class.
	 * @return the type of the tile.
	 */
	public int getType () {
		return type;
	}
	
	
	/**
	 * Sets the type of the tile.
	 * @param newType the new type the tile will be set with.
	 * @return true if setting tile type was successful, false otherwise.
	 */
	public boolean setType (int newType) {
		if (newType >= 0 && newType <= 6) {
			this.type = newType;
			return true;
		}
		return false;
	}

	/**
	 * Sets the tile to be walkable and its type to path, the default walkable type.
	 */
	public void setWalkable (){
		this.isWalkable = true;
		this.type = PATH;	//default type is path if walkable
	}
	
	/**
	 * Checks if the tile is walkable.
	 * @return true if the tile is walkable.
	 */
	public boolean isWalkable (){
		return isWalkable;
	}
	
	/**
	 * Get the x coordinate of the tile in the maze.
	 * @return the x coordinate of the tile.
	 */
	public int getX(){
		return this.x;
	}

	/**
	 * Get the y coordinate of the tile in the maze.
	 * @return the y coordinate of the tile.
	 */
	public int getY(){
		return this.y;
	}

	/**
	 * Override method for hashCode.
	 * Two equal tiles must have the same hash code.
	 * For details on equality, see equals.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Override method for equals.
	 * Two tiles are equal if they have the same
	 * x and y coordinates.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
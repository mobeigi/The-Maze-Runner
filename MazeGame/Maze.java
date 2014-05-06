/**
 * Contains the board of the maze.
 * It contains the maze-generating algorithm.
 * @author Gavin Tam
 * @see Tile
 * @see Player
 */
public class Maze {
	private Tile[][] grid;
	private int width;
	private int height;
			
	public Maze (int width, int height) {
		grid = new Tile[width][height];
		this.width = width;
		this.height = height;
	}
	
	public void createMaze() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile t; 
				//if border tile
				if (i == 0 || i == width-1 ||
					j == 0 || j == height-1) {
					t = new Tile(0); // 0 denotes walkable tile
				} else {
					t = new Tile(1); // 1 denotes wall/non-walkable tile
				}
				grid[i][j] = t;
			}
		}
	}
	
	/**
	 * Finds the tile on the maze that the player is situated in
	 * @param p the player
	 * @return the tile in which the player is on
	 */
	public Tile findPlayer (Player p) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (grid[i][j].containsPlayer(p)) {
					return grid[i][j];
				}
			}
		}
		return null;
	}
}

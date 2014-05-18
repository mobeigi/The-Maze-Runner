import java.util.ArrayList;

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
	private Tile playerLoc;	//location of the current player
			
	public Maze (int width, int height) {
		grid = new Tile[width][height];
		this.width = width;
		this.height = height;
		createMaze();	//initialise all tiles
		playerLoc = grid[0][1];	//origin at (0,1), 
								//width and height should be big enough to allow this to be valid
	}
	
	public void createMaze() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile t; 
				//if border tile
				//not origin which is (0,1)
				//not destination which is (width-1, height)
				if ((i == 0 && j != 1) || (i == width-1 && j != height) ||
					j == 0 || j == height-1) {
					t = new Tile(false,i,j); // false denotes walkable tile
				} else {
					t = new Tile(true,i,j); // true denotes wall/non-walkable tile
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
		return playerLoc;
	}
	
	public void updatePlayerLoc (int x, int y) {
		//only update player location if the move is valid
		//disallow teleportation? (check here or elsewhere?) 
		//e.g. limit x to 0,1,-1 and y to 0,1,-1 and one of x,y must be 0
		//as we can only move in one direction at a time
		if (playerLoc.getX()+x < width && playerLoc.getY()+y < height
			&& grid[playerLoc.getX()+x][playerLoc.getY()+y].isWalkable()) {
			playerLoc = grid[playerLoc.getX()+x][playerLoc.getY()+y];
		}
	}
	
	public boolean reachedEnd (Tile currLoc) {
		//destination is at (width-1, height)
		return (currLoc.getX() == width-1 && currLoc.getY() == height);
	}
	
	//double check syntax please, my eclipse isnt giving me
	//any errors
	// -Calvin
	public ArrayList<Tile> getAdjacentTiles(Tile t){
		ArrayList<Tile> a = new ArrayList<Tile>();
		int x = t.getX();
		int y = t.getY();
		
		if(x != 0){
			a.add(grid[x-1][y]);
		}
		
		if(x != (width-1)){
			a.add(grid[x+1][y]);
		}
		
		if(y!=0){
			a.add(grid[x][y-1]);
		}
		
		if(y!=(height-1)){
			a.add(grid[x][y+1]);
		}
		
		return a;
	}
	
	//Check if there is a wall at these coordinates
	public boolean isValid(int x, int y){
		//NEEDS TO BE IMPLEMENTED
		return true;
	}
}

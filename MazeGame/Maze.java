import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
/**
 * Contains the board of the maze.
 * It contains the maze-generating algorithm
 * and the maze solving algorithm.
 * @author Gavin Tam
 * @see Tile
 * @see Player
 */

/*
 * IDEAS:
 * Enemy player
 * Weapons for player to collect to kill enemy
 * Collecting items for the next level (powerups)
 * Treasure chest to get high scores for a maze
 * Score dependent on time taken to finish maze
 * Have a plot with dialogue boxes coming up for each level with picture displayed
 */

public class Maze {
	private Tile[][] grid;
	private int width;
	private int height;
	private Tile playerLoc;	//location of the current player
	private HashSet<Tile> mazeSolution;
			
	public Maze (int width, int height) {
		//width and height should be an odd number
		//width and height is assumed to be greater than 1
		grid = new Tile[width+2][height+2];
		this.width = width+2;
		this.height = height+2;
		createMaze();	//initialise all tiles
		playerLoc = grid[1][1];	//origin at (1,1), 
								//width and height should be big enough to allow this to be valid
		mazeSolution = new HashSet<Tile>();
		boolean[][] visited = new boolean[width+2][height+2];
		boolean solvable = solveMaze(1,1,visited);
		if (solvable) {	//maze should always be solvable
			showMaze();
		}
	}
	
	public void createMaze() {
		//initialise maze as all non-walkable tiles
		//and assign a random weight to each tile
		final double weights[][] = new double[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Tile t = new Tile(false,i,j); // false denotes non-walkable tile
				grid[i][j] = t;
				weights[i][j] = Math.random();
			}
		}
		//Use Prim's algorithm on a randomly weighted grid graph representation of the maze
		//vertices are represented by tiles with odd coordinates
		//and edges are represented by two adjacent vertices (i.e. a series of 3 consecutive tiles)
		PriorityQueue<TileEdge> edges = new PriorityQueue<TileEdge>(100, new Comparator<TileEdge>() { 
			public int compare (TileEdge e1, TileEdge e2) {
				double weightDiff = (weights[e1.getTile0().getX()][e1.getTile0().getY()] +
						weights[e1.getTile1().getX()][e1.getTile1().getY()] +
						weights[e1.getTile2().getX()][e1.getTile2().getY()])
						-(weights[e2.getTile0().getX()][e2.getTile0().getY()]
						+ weights[e2.getTile1().getX()][e2.getTile1().getY()]
						+ weights[e2.getTile2().getX()][e2.getTile2().getY()]);
				if (weightDiff > 0) return 1;
				if (weightDiff < 0) return -1;
				return 0;
			}
		});
		HashSet<TileEdge> edgesAdded = new HashSet<TileEdge>();
		HashSet<Tile> visited = new HashSet<Tile>();
		ArrayList<TileEdge> neighbours = getNeighbouringEdges(grid[1][1]);
		for (int i = 0; i < neighbours.size(); i++) {
			edges.add(neighbours.get(i));
			edgesAdded.add(neighbours.get(i));
		}
		visited.add(grid[1][1]);
		int numVertices = ((width-1)/2)*((height-1)/2);
		//while not all vertices have been visited
		while (visited.size() < numVertices) {
			TileEdge curr = edges.remove();
			if ((visited.contains(curr.getTile0()) && visited.contains(curr.getTile2()))
				|| (!visited.contains(curr.getTile0()) && !visited.contains(curr.getTile2()))) {
				continue;
			}
			//only one of the tiles of the edge is unvisited
			if (visited.contains(curr.getTile0())) {
				neighbours = getNeighbouringEdges(curr.getTile2());
				visited.add(curr.getTile2());	//if Tile0 has been visited, Tile2 must not have been visited
			} else {
				neighbours = getNeighbouringEdges(curr.getTile0());
				visited.add(curr.getTile0());	//if Tile2 has been visited, Tile0 must not have been visited
			}
			for (int i = 0; i < neighbours.size(); i++) {
				if (!edgesAdded.contains(neighbours.get(i))) {
					edges.add(neighbours.get(i));
					edgesAdded.add(neighbours.get(i));
				}
			}
			curr.getTile0().setWalkable();		//set the edge as a walkable path
			curr.getTile1().setWalkable();
			curr.getTile2().setWalkable();
		}
	}
	
	public void showMaze () {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (grid[i][j].isWalkable()) {
					//order of printing is important as path contains start and dest
					if (playerLoc.equals(grid[i][j])) {
						System.out.print("P");
					} else if (i == (width-1)-1 && j == (height-1)-1) {
						System.out.print("D");
					} else if (mazeSolution.contains(grid[i][j])) {
						System.out.print("*");
					} else {
						System.out.print("0");
					}
				} else {
					System.out.print("-");	//represents wall
				}
			}
			System.out.println();
		}
	}
	
	//checking within a 2 tile radius
	public ArrayList<TileEdge> getNeighbouringEdges (Tile curr) {
		ArrayList<TileEdge> neighbouringEdges = new ArrayList<TileEdge>();
		//vertices exist only on tiles with odd x and y coordinates
		for (int i = Math.max(curr.getX()-2,1); i <= Math.min(curr.getX()+2,width-1); i+=2) {
			for (int j = Math.max(curr.getY()-2,1); j <= Math.min(curr.getY()+2,height-1); j+=2) {
				TileEdge newEdge = null;
				if (curr.getX()-i == 2 && curr.getY()-j == 0) {
					newEdge = new TileEdge(grid[i][curr.getY()],
										   grid[i+1][curr.getY()],curr);
				} else if (curr.getX()-i == -2 && curr.getY()-j == 0) {
					newEdge = new TileEdge(curr,grid[i-1][curr.getY()],
												grid[i][curr.getY()]);
				} else if (curr.getX()-i == 0 && curr.getY()-j == 2) {
					newEdge = new TileEdge(grid[curr.getX()][j],
										   grid[curr.getX()][j+1], curr);
				} else if (curr.getX()-i == 0 && curr.getY()-j == -2) {
					newEdge = new TileEdge(curr, grid[curr.getX()][j-1],
												 grid[curr.getX()][j]);
				} else {
					continue;
				}
				neighbouringEdges.add(newEdge);
			}
		}
		return neighbouringEdges;
	}
	
	/**
	 * Credit to http://en.wikipedia.org/wiki/Maze_solving_algorithm
	 * Adapted from "Recursive algorithm"
	 */
	public boolean solveMaze (int x, int y, boolean[][] visited) {
		if (x == width-2 && y == height-2) return true;
		if (!grid[x][y].isWalkable() || visited[x][y]) return false;
		visited[x][y] = true;
		if (x != 0) {
			if (solveMaze(x-1,y,visited)) {
				mazeSolution.add(grid[x][y]);
				return true;
			}
		}
		if (x != width-1) {
			if (solveMaze(x+1,y,visited)) {
				mazeSolution.add(grid[x][y]);
				return true;
			}
		}
		if (y != 0) {
			if (solveMaze(x,y-1,visited)) {
				mazeSolution.add(grid[x][y]);
				return true;
			}
		}
		if (y != height-1) {
			if (solveMaze(x,y+1,visited)) {
				mazeSolution.add(grid[x][y]);
				return true;
			}
		}
		return false;
	}
	
	public void giveHint() {
		//TODO change colour of tiles such that path from current player position
		//to the nearest tile part of the solution is given
	}
	
	/**
	 * Finds the tile on the maze that the player is situated in
	 * @param p the player
	 * @return the tile in which the player is on
	 */
	public Tile findPlayer () {
		return playerLoc;
	}
	
	public void updatePlayerLoc (int x, int y) {
		if (isValid(x,y)) {
			playerLoc = grid[playerLoc.getX()+x][playerLoc.getY()+y];
		}
	}
	
	//checks if a player move is valid
	private boolean isValid (int x, int y) {
		if (x > 1 || x < -1 || y > 1 || y < -1) {	//can only move at most one tile per turn
			return false;
		} else if (x != 0 && y != 0) {	//can only move in one direction
			return false;
		} else if ((playerLoc.getX()+x) >= (width-1) || (playerLoc.getY()+y) >= (height-1)
			|| (playerLoc.getX()+x) <= 0 || (playerLoc.getY()+y) <= 0) {	//cannot move over border
			return false;
		} else if (!grid[playerLoc.getX()+x][playerLoc.getY()+y].isWalkable()) { //cannot move to wall
			return false;
		}
		return true;
	}
	
	public boolean reachedEnd () {
		//destination is at (width-2, height-2)
		return (playerLoc.getX() == (width-2) && playerLoc.getY() == (height-2));
	}
}

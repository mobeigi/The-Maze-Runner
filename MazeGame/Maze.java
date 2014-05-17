import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
		//width and height should be an odd number
		//width and height is assumed to be greater than 3
		grid = new Tile[width][height];
		this.width = width;
		this.height = height;
		createMaze();	//initialise all tiles
		playerLoc = grid[1][1];	//origin at (1,1), 
								//width and height should be big enough to allow this to be valid
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
		HashSet<Tile> visited = new HashSet<Tile>();
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
					System.out.print("0");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
	}
	
	//can improve efficiency by only checking within a 2 tile radius
	public ArrayList<TileEdge> getNeighbouringEdges (Tile curr) {
		ArrayList<TileEdge> neighbouringEdges = new ArrayList<TileEdge>();
		for (int i = 1; i < width; i+=2) {
			for (int j = 1; j < height; j+=2) {
				if (curr.getX()-i == 2 && curr.getY()-j == 0) {
					TileEdge newEdge = new TileEdge(grid[i][curr.getY()],
													grid[i+1][curr.getY()],curr);
					neighbouringEdges.add(newEdge);
				} else if (curr.getX()-i == -2 && curr.getY()-j == 0) {
					TileEdge newEdge = new TileEdge(curr,grid[i-1][curr.getY()],
													grid[i][curr.getY()]);
					neighbouringEdges.add(newEdge);
				} else if (curr.getX()-i == 0 && curr.getY()-j == 2) {
					TileEdge newEdge = new TileEdge(grid[curr.getX()][j],
													grid[curr.getX()][j+1], curr);
					neighbouringEdges.add(newEdge);
				} else if (curr.getX()-i == 0 && curr.getY()-j == -2) {
					TileEdge newEdge = new TileEdge(curr, grid[curr.getX()][j-1],
													grid[curr.getX()][j]);
					neighbouringEdges.add(newEdge);
				}
			}
		}
		return neighbouringEdges;
	}
	
	/**
	 * Finds the tile on the maze that the player is situated in
	 * @param p the player
	 * @return the tile in which the player is on
	 */
	/*public Tile findPlayer (Player p) {
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
		return (currLoc.getX() == width-1 && currLoc.getY() == height-1);
	}*/
}

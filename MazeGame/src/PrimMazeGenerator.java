import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Maze generator using Prim's algorithm.
 * It applies Prim's algorithm on a randomly weighted grid graph representation of the maze.
 * Vertices are represented by tiles with odd coordinates,
 * and edges are represented by two adjacent vertices (i.e. a series of 3 consecutive tiles)
 * @author Gavin Tam 
 * @see MazeGenerator
 */
public class PrimMazeGenerator implements MazeGenerator {
	/**
	 * Generates a maze of a certain width and height.
	 * @param width the desired width (including border).
	 * @param height the desired height (including border).
	 * @return a grid of tiles characterising the maze.
	 * @see MazeGenerator
	 */
	public Tile[][] genMaze(int width, int height) {
		Tile[][] grid = new Tile[width][height];
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
		ArrayList<TileEdge> neighbours = getNeighbouringEdges(grid[1][1],grid);
		for (int i = 0; i < neighbours.size(); i++) {
			edges.add(neighbours.get(i));	//add all neighbouring edges to the origin
			edgesAdded.add(neighbours.get(i));	//update edges added
		}
		visited.add(grid[1][1]);	//visited origin
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
				neighbours = getNeighbouringEdges(curr.getTile2(),grid);
				visited.add(curr.getTile2());	//if Tile0 has been visited, Tile2 must not have been visited
			} else {
				neighbours = getNeighbouringEdges(curr.getTile0(),grid);
				visited.add(curr.getTile0());	//if Tile2 has been visited, Tile0 must not have been visited
			}
			for (int i = 0; i < neighbours.size(); i++) {
				if (!edgesAdded.contains(neighbours.get(i))) {
					edges.add(neighbours.get(i));	//add all neighouring edges that haven't been added
					edgesAdded.add(neighbours.get(i));	//update edges added
				}
			}
			curr.getTile0().setWalkable();		//set the edge as a walkable path
			curr.getTile1().setWalkable();
			curr.getTile2().setWalkable();
		}
		return grid;
	}

	/**
	 * Getting neighbouring edges to a tile on the maze
	 * @param curr the tile whose neighbouring edges is desired
	 * @return the list of neighbouring edges to the tile
	 */
	public ArrayList<TileEdge> getNeighbouringEdges (Tile curr, Tile[][] grid) {
		ArrayList<TileEdge> neighbouringEdges = new ArrayList<TileEdge>();
		//vertices exist only on tiles with odd x and y coordinates
		//check within a two tile radius
		int width = grid[0].length;
		int height = grid.length;
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
				neighbouringEdges.add(newEdge);	//add edge to list of neighbouring edges
			}
		}
		return neighbouringEdges;
	}
}

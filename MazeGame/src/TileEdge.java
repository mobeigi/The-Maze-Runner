/**
 * Edge consisting of three adjacent tiles on the board of the maze.
 * It is required for the maze generation algorithm.
 * @author Gavin Tam
 */
public class TileEdge {
	private Tile t0;	//the three tiles in a tile edge
	private Tile t1;	//t0 and t2 are the endpoints of the edge
	private Tile t2;	//t1 is the middle tile of the edge
	
	/**
	 * Constructs a tile edge.
	 * @param t0 the tile marking one of the two endpoints of the edge.
	 * @param t1 the middle tile of the tile edge.
	 * @param t2 the tile marking the other endpoint of the edge.
	 */
	public TileEdge (Tile t0, Tile t1, Tile t2) {
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/**
	 * Checks if the tile edge contains a particular tile.
	 * @param t the tile of interest.
	 * @return true if the tile is contained in the tile edge.
	 */
	public boolean containsTile (Tile t) {
		return this.t0.equals(t) || this.t1.equals(t) || this.t2.equals(t);
	}
	
	/**
	 * Gets the tile marking one of the two endpoints of the edge.
	 * @return the tile making one of the two endpoints of the edge.
	 */
	public Tile getTile0 () {
		return t0;
	}
	
	/**
	 * Gets the middle tile of the edge.
	 * @return the middle tile of the edge.
	 */
	public Tile getTile1 () {
		return t1;
	}
	
	/**
	 * Gets the tile marking the other endpoint of the edge.
	 * See getTile0().
	 * @return the tile marking the other endpoint of the edge.
	 */
	public Tile getTile2 () {
		return t2;
	}
	
	/**
	 * Display the edge by printing the coordinates
	 * of the two endpoint tiles, t0 and t2.
	 */
	public void showEdge () {
		System.out.println(t0.getX() + " " + t0.getY() + " " +
							t2.getX() + " " + t2.getY());
	}

	/**
	 * Override method for hashCode.
	 * Two tile edges have the same hashcode if they are equal.
	 * See override method for equals.
	 * @return the hashcode of the tile edge.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		if (t0.hashCode() > t2.hashCode()) {
			result = prime * result + ((t0 == null) ? 0 : t0.hashCode());
			result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
			result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
		} else {
			result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
			result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
			result = prime * result + ((t0 == null) ? 0 : t0.hashCode());
		}
		return result;
	}

	/**
	 * Override method for equals.
	 * Two tile edges are equals if they share the same tiles, that is
	 * the tiles on the endpoints are the same (regardless of order),
	 * and the middle tile is identical.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileEdge other = (TileEdge) obj;
		if (t0 == null) {			//if one of the tiles on the endpoints is different
			if (other.t0 != null && other.t2 != null)
				return false;
		} else if (!t0.equals(other.t0) && !t0.equals(other.t2))
			return false;
		if (t1 == null) {		//if the middle tile is different
			if (other.t1 != null)
				return false;
		} else if (!t1.equals(other.t1))
			return false;
		if (t2 == null) {		//if the tile on the other endpoint is different
			if (other.t2 != null && other.t0 != null)
				return false;
		} else if (!t2.equals(other.t2) && !t2.equals(other.t2))
			return false;
		return true;
	}
}

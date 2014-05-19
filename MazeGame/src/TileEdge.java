/**
 * Edge between three adjacent tiles on the board of the maze.
 * It is required for the maze generation algorithm.
 * @author Gavin Tam
 *
 */
public class TileEdge {
	private Tile t0;
	private Tile t1;
	private Tile t2;
	
	public TileEdge (Tile t0, Tile t1, Tile t2) {
		this.t0 = t0;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public boolean containsTile (Tile t) {
		return this.t0.equals(t) || this.t1.equals(t) || this.t2.equals(t);
	}
	
	public Tile getTile0 () {
		return t0;
	}
	
	public Tile getTile1 () {
		return t1;
	}
	
	public Tile getTile2 () {
		return t2;
	}
	
	public void showEdge () {
		System.out.println(t0.getX() + " " + t0.getY() + " " +
							t2.getX() + " " + t2.getY());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((t0 == null) ? 0 : t0.hashCode());
		result = prime * result + ((t1 == null) ? 0 : t1.hashCode());
		result = prime * result + ((t2 == null) ? 0 : t2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TileEdge other = (TileEdge) obj;
		if (t0 == null) {
			if (other.t0 != null)
				return false;
		} else if (!t0.equals(other.t0))
			return false;
		if (t1 == null) {
			if (other.t1 != null)
				return false;
		} else if (!t1.equals(other.t1))
			return false;
		if (t2 == null) {
			if (other.t2 != null)
				return false;
		} else if (!t2.equals(other.t2))
			return false;
		return true;
	}
}

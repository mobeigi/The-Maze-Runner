/**
 * Edge between two adjacent tiles on the board of the maze.
 * It is required for the maze generation algorithm.
 * @author Gavin Tam
 *
 */
public class TileEdge {
	private Tile t1;
	private Tile t2;
	
	public TileEdge (Tile t1, Tile t2) {
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public boolean containsTile (Tile t1) {
		return this.t1.equals(t1) || this.t2.equals(t2);
	}
}

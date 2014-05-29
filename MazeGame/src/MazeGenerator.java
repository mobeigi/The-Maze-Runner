/**
 * Interface for maze generators.
 * @author Gavin Tam
 *
 */
public interface MazeGenerator {
	/**
	 * Generates a maze of a certain width and height.
	 * @param width the desired width (including border).
	 * @param height the desired height (including border).
	 * @return a grid of tiles characterising the maze.
	 */
	public Tile[][] genMaze(int width, int height);
}

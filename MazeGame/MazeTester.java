
public class MazeTester {
	public static void main (String[] args) {
		final long startTime = System.currentTimeMillis();
		Maze maze = new Maze(51,51);
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms" );
	}
}

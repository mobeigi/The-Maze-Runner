
public class Tile {
	private int x;
	private int y;
	private boolean isWalkable;
	
	public Tile (boolean isWalkable, int x, int y){
		this.x = x;
		this.y = y;
		this.isWalkable = isWalkable;
	}

	public void setWalkable (boolean isWalkable){
		this.isWalkable = isWalkable;
	}
	
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public boolean isWalkable() {
		// TODO Auto-generated method stub
		return false;
	}
}
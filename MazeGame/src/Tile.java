
public class Tile {
	private int x;
	private int y;
	private boolean isWalkable;
	
	public Tile (boolean isWalkable, int x, int y){
		this.x = x;
		this.y = y;
		this.isWalkable = isWalkable;
	}

	public void setWalkable (){
		this.isWalkable = true;
	}
	
	public boolean isWalkable (){
		return isWalkable;
	}
	
	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Tile other = (Tile) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
package algorithms.mazeGenerators;

/**
 * The Class Position.
 */
public class Position {
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The z. */
	private int z;
	
	/**
	 * Instantiates a new position by x,y,z values.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Position(int x, int y, int z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	/**
	 * Instantiates a new position by a given position.
	 *
	 * @param p the position
	 */
	public Position (Position p) {
		this.setX(p.getX());
		this.setY(p.getY());
		this.setZ(p.getZ());
	}
	
	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y the new y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * Sets the z.
	 *
	 * @param z the new z
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * Converts the position into a readable string.
	 *
	 * @return the position as a string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{"+x+","+y+","+z+"}";
	}
	
	/**
	 * Checks if two positions are the same.
	 *
	 * @param obj the position to be compared
	 * @return true, if obj and this position are the same, else returns false.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Position p = (Position) obj;
		return (this.x==p.getX() && this.y==p.getY() && this.z==p.getZ());
	}
	
	/**
	 * Uses this position's string representation hash code.
	 *
	 * @return the hash code
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

}

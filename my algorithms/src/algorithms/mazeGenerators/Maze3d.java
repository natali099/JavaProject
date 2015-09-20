package algorithms.mazeGenerators;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Class Maze3d.
 */
public class Maze3d {
	
	/** The 3D maze represented by a 3 dimensional array. */
	private int[][][] maze3d;
	
	/** The number of rows. */
	private int x /* rows */;
	
	/** The number of floors. */
	private int y /* floors */;
	
	/** The number of columns. */
	private int z /* columns */;
	
	/** The start position. */
	private Position startPosition;
	
	/** The goal position. */
	private Position goalPosition;
	
	
	/**
	 * Instantiates a new maze3d.
	 *
	 * @param rows the number of rows
	 * @param floors the number of floors
	 * @param columns the number of columns
	 * @throws Exception if number of rows or floors or columns is illegal
	 */
	public Maze3d(int rows, int floors, int columns) throws Exception {
		if (rows < 3 || floors < 3 || columns < 3) {
			throw new Exception("maze size should be at least 3x3x3!");
		}
		this.setX(rows);
		this.setY(floors);
		this.setZ(columns);
		this.setMaze3d(new int[this.getY()][this.getX()][this.getZ()]);
	}
	
	/**
	 * Instantiates a new maze3d from a byte array.
	 *
	 * @param array the byte array representing the maze
	 */
	public Maze3d(byte[] array) {
		ByteBuffer bb = ByteBuffer.wrap(array);
		bb.position(0);
		this.setX(bb.getInt());
		this.setY(bb.getInt());
		this.setZ(bb.getInt());
		this.setMaze3d(new int[this.getY()][this.getX()][this.getZ()]);
		this.setStartPosition(new Position(bb.getInt(), bb.getInt(), bb.getInt()));
		this.setGoalPosition(new Position(bb.getInt(), bb.getInt(), bb.getInt()));
		
		for (int i=0; i<y; i++) {
			for (int j=0; j<x; j++) {
				for (int k=0; k<z; k++) {
					maze3d[i][j][k] = Byte.valueOf(bb.get()).intValue();
				}
			}
		}
	}
	
	/**
	 * Gets the maze3d.
	 *
	 * @return the maze3d
	 */
	public int[][][] getMaze3d() {
		return maze3d;
	}

	/**
	 * Sets the maze3d.
	 *
	 * @param maze3d the new maze3d
	 */
	public void setMaze3d(int[][][] maze3d) {
		this.maze3d = maze3d;
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
	 * Gets the start position.
	 *
	 * @return the start position
	 */
	public Position getStartPosition() {
		return this.startPosition;
		
	}
	
	/**
	 * Sets the start position.
	 *
	 * @param startPosition the new start position
	 */
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}
	
	/**
	 * Gets the goal position.
	 *
	 * @return the goal position
	 */
	public Position getGoalPosition() {
		return this.goalPosition;
		
	}
	
	/**
	 * Sets the goal position.
	 *
	 * @param goalPosition the new goal position
	 */
	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}
	
	/**
	 * Gets all possible moves from a position.
	 *
	 * @param p the position
	 * @return the possible moves
	 */
	public String[] getPossibleMoves(Position p) {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		if(p.getY() < this.y-1 && maze3d[p.getY()+1][p.getX()][p.getZ()] == 0) {
			possibleMoves.add("up");
		}
		
		if(p.getY() > 0 && maze3d[p.getY()-1][p.getX()][p.getZ()] == 0) {
			possibleMoves.add("down");
		}
		
		if(p.getX() < this.x-1 && maze3d[p.getY()][p.getX()+1][p.getZ()] == 0) {
			possibleMoves.add("right");
		}
		
		if(p.getX() > 0 && maze3d[p.getY()][p.getX()-1][p.getZ()] == 0) {
			possibleMoves.add("left");
		}
		
		if(p.getZ() < this.z-1 && maze3d[p.getY()][p.getX()][p.getZ()+1] == 0) {
			possibleMoves.add("forward");
		}
		
		if(p.getZ() > 0 && maze3d[p.getY()][p.getX()][p.getZ()-1] == 0) {
			possibleMoves.add("back");
		}
		
		return possibleMoves.toArray(new String[possibleMoves.size()]);		
	}
	
	/**
	 * Gets the cross section by x.
	 *
	 * @param x the x
	 * @return the cross section by x, where x==0
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public int[][] getCrossSectionByX(int x) throws IndexOutOfBoundsException {
		if (x > this.x || x < 0) {
			throw new IndexOutOfBoundsException();
		}
			
		int[][] crossSectionByX = new int[this.y][this.z];
		for (int i=0; i<crossSectionByX.length; i++) {
			for (int j=0; j<crossSectionByX[0].length; j++) {
				crossSectionByX[i][j] = maze3d[i][x][j];
			}
		}
		
		return crossSectionByX;
	}
	
	/**
	 * Gets the cross section by y.
	 *
	 * @param y the y
	 * @return the cross section by y, where y==0
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public int[][] getCrossSectionByY(int y) throws IndexOutOfBoundsException {
		if (y > this.y || y < 0) {
			throw new IndexOutOfBoundsException();
		}
			
		int[][] crossSectionByY = new int[this.x][this.z];
		for (int i=0; i<crossSectionByY.length; i++) {
			for (int j=0; j<crossSectionByY[0].length; j++) {
				crossSectionByY[i][j] = maze3d[y][i][j];
			}
		}
		
		return crossSectionByY;
	}
	
	/**
	 * Gets the cross section by z.
	 *
	 * @param z the z
	 * @return the cross section by z, where z==0
	 * @throws IndexOutOfBoundsException the index out of bounds exception
	 */
	public int[][] getCrossSectionByZ(int z) throws IndexOutOfBoundsException {
		if (z > this.z || z < 0) {
			throw new IndexOutOfBoundsException();
		}
			
		int[][] crossSectionByZ = new int[this.x][this.y];
		for (int i=0; i<crossSectionByZ.length; i++) {
			for (int j=0; j<crossSectionByZ[0].length; j++) {
				crossSectionByZ[i][j] = maze3d[j][i][z];
			}
		}
		
		return crossSectionByZ;
	}

	/**
	 * Prints the cross section.
	 *
	 * @param maze2d the cross section
	 * @return the cross section, represented by a string
	 */
	public String printCrossSection(int[][] maze2d) {
		StringBuilder array = new StringBuilder();
		
		for (int i=0; i<maze2d.length; i++) {
			for (int j=0; j<maze2d[0].length; j++) {
				array.append(maze2d[i][j]);
			}
			array.append("\n");
		}
		
		return array.toString();
	}
		
	/**
	 * Prints the maze.
	 */
	public void print() {
		for (int i=0; i<y; i++) {
			for (int j=0; j<x; j++) {
				for (int k=0; k<z; k++) {
					System.out.print(maze3d[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}
	
	/**
	 * A byte array representation of the maze.
	 *
	 * @return the maze, represented by a byte array 
	 */
	public byte[] toByteArray() {
		int num = x*y*z+36;
		ByteBuffer bb =	ByteBuffer.allocate(num);
		
		bb.putInt(x);
		bb.putInt(y);
		bb.putInt(z);
		bb.putInt(startPosition.getX());
		bb.putInt(startPosition.getY());
		bb.putInt(startPosition.getZ());
		bb.putInt(goalPosition.getX());
		bb.putInt(goalPosition.getY());
		bb.putInt(goalPosition.getZ());
		
		for (int i=0; i<y; i++) {
			for (int j=0; j<x; j++) {
				for (int k=0; k<z; k++) {
					bb.put(Integer.valueOf(maze3d[i][j][k]).byteValue());
				}
			}
		}
		
		return bb.array(); 
	}
	
	/**
 	 * Checks if two mazes are the same
	 * 
	 * @param obj the maze to be compared
     * @return true, if obj and this maze are the same, else returns false.	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return Arrays.deepEquals(this.maze3d, ((Maze3d)obj).getMaze3d());
	}
	
	/**
	 * Converts the maze into a readable string.
	 *
	 * @return the maze as a string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<y; i++) {
			for (int j=0; j<x; j++) {
				for (int k=0; k<z; k++) {
					sb.append(maze3d[i][j][k]);
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * Uses this maze's 3d array's hash code.
	 *
	 * @return the hash code
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return maze3d.hashCode();
	}
}

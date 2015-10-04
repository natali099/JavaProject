package algorithms.mazeGenerators;

/**
 * The Interface Maze3dGenerator.
 */
public interface Maze3dGenerator {
	 
 	/**
 	 * Generates a 3D maze.
 	 *
 	 * @param x the x value (number of rows)
 	 * @param y the y value (number of floors)
 	 * @param z the z value (number of columns)
 	 * @return the 3D maze
 	 * @throws Exception if x or y or z are illegal inputs
 	 */
 	Maze3d generate(int x, int y, int z) throws Exception;
	 
 	/**
 	 * Measures algorithm time generating a 3D maze.
 	 *
 	 * @param x the x value (number of rows)
 	 * @param y the y value (number of floors)
 	 * @param z the z value (number of columns)
 	 * @return the time it takes to the algorithm to generate a 3D maze (in a string)
 	 * @throws Exception the exception
 	 */
 	String measureAlgorithmTime(int x, int y, int z) throws Exception;

}

package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

/**
 * The Interface Model.
 */
public interface Model {
	
	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	byte[] getData();
	
	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	String getErrorMessage();
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	Maze3d getMaze();
	
	/**
	 * Gets the solution.
	 *
	 * @return the solution
	 */
	Solution<Position> getSolution();
	
	/**
	 * Gets the cross section.
	 *
	 * @return the cross section
	 */
	int[][] getCrossSection();
	
	/**
	 * Displays all files and directories in the given path.
	 *
	 * @param path the path
	 */
	void dir(String path);
	
	/**
	 * Generates a 3d maze in a thread and adds it to mazes hash map under the given name.
	 * If a maze with the given name already exists, displays a message to the controller.
	 *
	 * @param mazeName the maze name
	 * @param x the number of rows
	 * @param y the number of floors
	 * @param z the number of columns
	 */
	void generate3dMaze(String mazeName, int x, int y, int z);
	
	/**
	 * Displays the maze with the given name, if it exists.
	 *
	 * @param mazeName the maze name
	 */
	void displayMaze(String mazeName);
	
	/**
	 * Displays cross section by the given axis and index.
	 *
	 * @param axis the axis
	 * @param index the index
	 * @param mazeName the maze name
	 */
	void displayCrossSection(char axis, int index, String mazeName);
	
	/**
	 * Saves the maze to a file under the given file name and adds it to mazes-files hash map.
	 *
	 * @param mazeName the maze name
	 * @param fileName the file name
	 */
	void saveMaze(String mazeName, String fileName);
	
	/**
	 * Loads the maze from the file fileName and adds it to mazes hash map under the given name.
	 *
	 * @param fileName the file name
	 * @param mazeName the maze name
	 */
	void loadMaze(String fileName, String mazeName);
	
	/**
	 * Displays the maze size in memory.
	 *
	 * @param mazeName the maze name
	 */
	void mazeSize(String mazeName);
	
	/**
	 * Displays the maze size in file.
	 * If a file for this maze name doesn't exist, creates a temporary file
	 *
	 * @param mazeName the maze name
	 */
	void fileSize(String mazeName);
	
	/**
	 * Solves the maze using the given algorithm.
	 *
	 * @param mazeName the maze name
	 * @param algorithm the algorithm
	 */
	void solveMaze(String mazeName, String algorithm);
	
	/**
	 * Displays the solution for the maze.
	 *
	 * @param mazeName the maze name
	 */
	void displaySolution(String mazeName);
	
	/**
	 * Exits carefully.
	 */
	void exit();
}

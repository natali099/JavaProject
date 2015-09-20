package algorithms.mazeGenerators;

import java.util.Random;

/**
 * The Class CommonMaze3dGenerator.
 */
public abstract class CommonMaze3dGenerator implements Maze3dGenerator {
	
	/**
	 * @see algorithms.mazeGenerators.Maze3dGenerator#generate(int, int, int)
	 */
	@Override
	public abstract Maze3d generate(int x, int y, int z) throws Exception;

	/**
	 * @see algorithms.mazeGenerators.Maze3dGenerator#measureAlgorithmTime(int, int, int)
	 */
	@Override
	public String measureAlgorithmTime(int x, int y, int z) throws Exception {
		long startTime = System.currentTimeMillis();
		this.generate(x, y, z);
		long endTime = System.currentTimeMillis();
		
		return String.valueOf(endTime-startTime);
	}

	/**
	 * Sets a random start position to a maze.
	 *
	 * @param maze the maze
	 */
	public void setRandomStartPosition(Maze3d maze) {
		Random rand = new Random();
		int side = rand.nextInt(3);
		switch (side) {
		case 0: /* start from bottom */
			maze.setStartPosition(new Position(rand.nextInt(maze.getX()-2)+1, 0, rand.nextInt(maze.getZ()-2)+1));
			break;
		case 1: /* start from left */
			maze.setStartPosition(new Position(0, rand.nextInt(maze.getY()-2)+1, rand.nextInt(maze.getZ()-2)+1));
			break;
		case 2: /* start from back */
			maze.setStartPosition(new Position(rand.nextInt(maze.getX()-2)+1, rand.nextInt(maze.getY()-2)+1, 0));
			break;
		}
		maze.getMaze3d()[maze.getStartPosition().getY()][maze.getStartPosition().getX()][maze.getStartPosition().getZ()] = 0;
	}
	
	/**
	 * Sets a random end position to a maze.
	 *
	 * @param maze the maze
	 */
	public void setRandomEndPosition(Maze3d maze) {
		Random rand = new Random();
		int side = rand.nextInt(3);
		switch (side) {
		case 0: /* end in top */
			maze.setGoalPosition(new Position(rand.nextInt(maze.getX()-2)+1, maze.getY()-1, rand.nextInt(maze.getZ()-2)+1));
			break;
		case 1: /* end in right */
			maze.setGoalPosition(new Position(maze.getX()-1, rand.nextInt(maze.getY()-2)+1, rand.nextInt(maze.getZ()-2)+1));
			break;
		case 2: /* end in front */
			maze.setGoalPosition(new Position(rand.nextInt(maze.getX()-2)+1, rand.nextInt(maze.getY()-2)+1, maze.getZ()-1));
			break;
		}
		maze.getMaze3d()[maze.getGoalPosition().getY()][maze.getGoalPosition().getX()][maze.getGoalPosition().getZ()] = 0;
	}
}

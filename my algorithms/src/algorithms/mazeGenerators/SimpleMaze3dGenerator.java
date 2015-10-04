package algorithms.mazeGenerators;

import java.util.Random;

/**
 * The Class SimpleMaze3dGenerator.
 */
public class SimpleMaze3dGenerator extends CommonMaze3dGenerator {

	/**
	 * Generates a 3D maze using randomness
	 * 
	 * @see algorithms.mazeGenerators.CommonMaze3dGenerator#generate(int, int, int)
	 */
	@Override
	public Maze3d generate(int x, int y, int z) throws Exception {
		Maze3d maze = new Maze3d(x, y, z);
		Random rand = new Random();
		
		//put random walls
		for (int i=0; i<maze.getY(); i++) {
			for (int j=0; j<maze.getX(); j++) {
				for (int k=0; k<maze.getZ(); k++) {
					if(i == 0 || i == maze.getY()-1 || j == 0 || j == maze.getX()-1 || k == 0 || k == maze.getZ()-1) {
						maze.getMaze3d()[i][j][k] = 1;
					}
					else
						maze.getMaze3d()[i][j][k] = rand.nextInt(2);
				}
			}
		}
		
		//select starting point
		setRandomStartPosition(maze);
		//create a path from start to a random end
		createPath(maze);
		return maze;
	}
	
	/**
	 * Creates a path, assuring there is a solution to the maze.
	 *
	 * @param maze the maze
	 */
	public void createPath(Maze3d maze) {
		Position current = new Position(maze.getStartPosition());
		Random rand = new Random();
		boolean endPositionIsSet = false;
		
		while (!endPositionIsSet) {
			int direction = rand.nextInt(6);
			switch (direction) {
			case 0: /* up */
				if (current.getY() < maze.getY()-1 && current.getX() > 0 && current.getZ() > 0) { /* not top floor, left wall or back wall */
					current.setY(current.getY()+1);
				}
				break;
			case 1: /* down */
				if (current.getY() > 1 && current.getX() > 0 && current.getZ() > 0) { /* not bottom floor, left wall or back wall */
					current.setY(current.getY()-1);
				}
				break;
			case 2: /* right */
				if (current.getX() < maze.getX()-1 && current.getY() > 0 && current.getZ() > 0) { /* not right wall, bottom floor or back wall */
					current.setX(current.getX()+1);
				}
				break;
			case 3: /* left */
				if (current.getX() > 1 && current.getY() > 0 && current.getZ() > 0) { /* not left wall, bottom floor or back wall */
					current.setX(current.getX()-1);
				}
				break;
			case 4: /* forward */
				if (current.getZ() < maze.getZ()-1 && current.getX() > 0 && current.getY() > 0) { /* not front wall, left wall or bottom floor */
					current.setZ(current.getZ()+1);
				}
				break;
			case 5: /* back */
				if (current.getZ() > 1 && current.getX() > 0 && current.getY() > 0) { /* not back wall, left wall or bottom floor */
					current.setZ(current.getZ()-1);
				}
				break;
			}
			maze.getMaze3d()[current.getY()][current.getX()][current.getZ()] = 0;
			if (current.getX()==maze.getX()-1 || current.getY()==maze.getY()-1 || current.getZ()==maze.getZ()-1) {
				maze.setGoalPosition(new Position(current.getX(), current.getY(), current.getZ()));
				endPositionIsSet = true;
			}
		}

	}

}

package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * The Class MyMaze3dGenerator.
 */
public class MyMaze3dGenerator extends CommonMaze3dGenerator {

	/**
	 * Generates a 3D maze using Recursive Backtracking algorithm
	 * 
	 * @see algorithms.mazeGenerators.CommonMaze3dGenerator#generate(int, int, int)
	 */
	@Override
	public Maze3d generate(int x, int y, int z) throws Exception {
		/* Recursive backtracking algorithm */
		Maze3d maze = new Maze3d(x, y, z);
		Random rand = new Random();
		Stack<Position> stack = new Stack<Position>();
		ArrayList<Position> unvisited = new ArrayList<Position>();
		boolean endPositionIsSet = false;
		
		initializeMazeAndUnvisitedCells(maze, unvisited);
		setRandomStartPosition(maze);
		Position current = new Position(maze.getStartPosition());
		
		//Move one step into the maze, make it current cell and mark as visited
		if (current.getX() == 0) {
			current.setX(current.getX()+1);
		} else if (current.getY() == 0){
			current.setY(current.getY()+1);
		} else {
			current.setZ(current.getZ()+1);
		}
		maze.getMaze3d()[current.getY()][current.getX()][current.getZ()] = 0;
		
		//While there are unvisited cells 
		while (!unvisited.isEmpty()) {
			//Get all current's unvisited neighbors
			ArrayList<Position> unvisitedNeighbors = new ArrayList<Position>();	
			
			ArrayList<Position> neighbors = getNeighbors(maze, current);
			while (neighbors.size() > 0) {
				Position p = neighbors.remove(rand.nextInt(neighbors.size()));
				//if p is an unvisited cell, add it to list of current's unvisited neighbors
				if (unvisited.contains(p))
					unvisitedNeighbors.add(new Position(p));
				else if (!endPositionIsSet && (p.getX()==maze.getX()-1 || p.getY()==maze.getY()-1 || p.getZ()==maze.getZ()-1)) {
					//Remove the wall between the chosen cell and the next cell
					maze.getMaze3d()[p.getY()][p.getX()][p.getZ()] = 0;
					//Set this cell as goal position
					maze.setGoalPosition(new Position(p.getX(), p.getY(), p.getZ()));
					endPositionIsSet = true;
				}
			}
			//If the current cell has any unvisited neighbors
			if (unvisitedNeighbors.size() > 0) {
				//Choose randomly one of the unvisited neighbors, remove it from unvisited and current's unvisited neighbors
				Position unvisitedNeighbor = new Position(unvisitedNeighbors.remove(rand.nextInt(unvisitedNeighbors.size())));
				unvisited.remove(unvisitedNeighbor);			
				//Push the current cell to the stack
				stack.push(current);
				
				Position nextNeighbor;				
				if (unvisitedNeighbor.getY() > current.getY()) {
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX()+1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()-1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()+1));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()-1));
					
					nextNeighbor = new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()+1, unvisitedNeighbor.getZ());
					
				}
				else if (unvisitedNeighbor.getY() < current.getY()) {
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX()+1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()-1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()+1));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()-1));
					
					nextNeighbor = new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()-1, unvisitedNeighbor.getZ());
					
				}
				else if (unvisitedNeighbor.getX() > current.getX()) {
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()+1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()-1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()+1));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()-1));
					
					nextNeighbor = new Position(unvisitedNeighbor.getX()+1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ());
					
				}
				else if (unvisitedNeighbor.getX() < current.getX()) {
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()+1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()-1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()+1));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()-1));

					nextNeighbor = new Position(unvisitedNeighbor.getX()-1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ());
					
				}
				else if (unvisitedNeighbor.getZ() > current.getZ()) {
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()+1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()-1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()+1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()-1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));

					nextNeighbor = new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()+1);
					
				}
				else { /* if (unvisitedNeighbor.getZ() < current.getZ()) */
					//Mark unvisited neighbor's 4 other directions cells as visited
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()+1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY()-1, unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()+1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));
					unvisited.remove(new Position(unvisitedNeighbor.getX()-1, unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()));

					nextNeighbor = new Position(unvisitedNeighbor.getX(), unvisitedNeighbor.getY(), unvisitedNeighbor.getZ()-1);
					
				}
				
				if (unvisited.contains(nextNeighbor)) {
					//Remove the wall between the chosen cell and the next cell
					maze.getMaze3d()[unvisitedNeighbor.getY()][unvisitedNeighbor.getX()][unvisitedNeighbor.getZ()] = 0; //
					maze.getMaze3d()[nextNeighbor.getY()][nextNeighbor.getX()][nextNeighbor.getZ()] = 0;
					//Mark as visited
					unvisited.remove(nextNeighbor);
					//Make the next cell the current cell
					current = nextNeighbor;
				}
				else if (!endPositionIsSet && (nextNeighbor.getX()==maze.getX()-1 || nextNeighbor.getY()==maze.getY()-1 || nextNeighbor.getZ()==maze.getZ()-1)) {
					//Remove the wall between the chosen cell and the next cell
					maze.getMaze3d()[unvisitedNeighbor.getY()][unvisitedNeighbor.getX()][unvisitedNeighbor.getZ()] = 0; //
					maze.getMaze3d()[nextNeighbor.getY()][nextNeighbor.getX()][nextNeighbor.getZ()] = 0;
					//Set this cell as goal position
					maze.setGoalPosition(new Position(nextNeighbor.getX(), nextNeighbor.getY(), nextNeighbor.getZ()));
					endPositionIsSet = true;
				}
			}			
			else if (!stack.isEmpty()) {
				//Pop a cell from the stack and make it the current cell
				current = stack.pop();
			}
			else {
				//Pick a random unvisited cell, make it the current cell and mark it as visited
				current = unvisited.remove(rand.nextInt(unvisited.size()));
				maze.getMaze3d()[current.getY()][current.getX()][current.getZ()] = 0;
			}
		}
		
		return maze;
	}
	
	/**
	 * Initialize maze and unvisited cells.
	 *
	 * @param maze the maze
	 * @param unvisited the unvisited cells list
	 */
	public void initializeMazeAndUnvisitedCells(Maze3d maze, ArrayList<Position> unvisited) {
		for (int i=0; i<maze.getY(); i++) {
			for (int j=0; j<maze.getX(); j++) {
				for (int k=0; k<maze.getZ(); k++) {
					maze.getMaze3d()[i][j][k] = 1;
					
					if(!(i == 0 || i == maze.getY()-1 || j == 0 || j == maze.getX()-1 || k == 0 || k == maze.getZ()-1)) {
						unvisited.add(new Position(j, i, k));						
					}						
				}
			}
		}
	}

	/**
	 * Gets the neighbors of a position.
	 *
	 * @param maze the maze
	 * @param p the position
	 * @return the neighbors
	 */
	public ArrayList<Position> getNeighbors(Maze3d maze, Position p) {
		ArrayList<Position> neighbors = new ArrayList<Position>();
		
		if (p.getY() < maze.getY()-1) {
			neighbors.add(new Position(p.getX(), p.getY()+1, p.getZ()));
		}
		
		if (p.getY() > 0) {
			neighbors.add(new Position(p.getX(), p.getY()-1, p.getZ()));
		}
		
		if (p.getX() < maze.getX()-1) {
			neighbors.add(new Position(p.getX()+1, p.getY(), p.getZ()));
		}
		
		if (p.getX() > 0) {
			neighbors.add(new Position(p.getX()-1, p.getY(), p.getZ()));
		}
		
		if (p.getZ() < maze.getZ()-1) {
			neighbors.add(new Position(p.getX(), p.getY(), p.getZ()+1));
		}
		
		if (p.getZ() > 0) {
			neighbors.add(new Position(p.getX(), p.getY(), p.getZ()-1));
		}		
		
		return neighbors;		
	}
}

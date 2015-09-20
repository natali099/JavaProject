package algorithms.demo;

import java.util.ArrayList;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
 * The Class SearchableMaze.
 */
public class SearchableMaze implements Searchable<Position> {
	
	/** The maze to be searched. */
	private Maze3d maze;
	
	/**
	 * Instantiates a new searchable maze.
	 *
	 * @param maze the maze
	 */
	public SearchableMaze(Maze3d maze) {
		this.setMaze(maze);
	}
	
	/**
	 * Gets the maze.
	 *
	 * @return the maze
	 */
	public Maze3d getMaze() {
		return maze;
	}
	
	/**
	 * Sets the maze.
	 *
	 * @param maze the new maze
	 */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	/**
	 * Gets maze's start state
	 * 
	 * @see algorithms.search.Searchable#getStartState()
	 */
	@Override
	public State<Position> getStartState() {
		State<Position> start = new State<Position>(maze.getStartPosition());
		start.setCameFrom(null);
		start.setCost(0);
		return start;
	}

	/**
	 * Gets maze's goal state
	 * 
	 * @see algorithms.search.Searchable#getGoalState()
	 */
	@Override
	public State<Position> getGoalState() {
		State<Position> goal = new State<Position>(maze.getGoalPosition());
		return goal;
	}

	/**
	 * Gets all possible states, converting them from String to State<Position>
	 * 
	 * @see algorithms.search.Searchable#getAllPossibleStates(algorithms.search.State)
	 */
	@Override
	public ArrayList<State<Position>> getAllPossibleStates(State<Position> s) {
		ArrayList<State<Position>> possibleMoves = new ArrayList<State<Position>>();
		String[] moves = maze.getPossibleMoves(s.getState());
		State<Position> state = null;
		
		for (String move : moves) {
			switch (move) {
			case "up":
				state = new State<Position>(new Position(s.getState().getX(), s.getState().getY()+1, s.getState().getZ()));
				break;
			case "down":
				state = new State<Position>(new Position(s.getState().getX(), s.getState().getY()-1, s.getState().getZ()));
				break;
			case "left":
				state = new State<Position>(new Position(s.getState().getX()-1, s.getState().getY(), s.getState().getZ()));
				break;
			case "right":
				state = new State<Position>(new Position(s.getState().getX()+1, s.getState().getY(), s.getState().getZ()));
				break;
			case "back":
				state = new State<Position>(new Position(s.getState().getX(), s.getState().getY(), s.getState().getZ()-1));
				break;
			case "forward":
				state = new State<Position>(new Position(s.getState().getX(), s.getState().getY(), s.getState().getZ()+1));
				break;
			}
			
			state.setCost(1);
			possibleMoves.add(state);
		}
		
		return possibleMoves;
	}

}

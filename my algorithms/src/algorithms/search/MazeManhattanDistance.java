package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * The Class MazeManhattanDistance.
 */
public class MazeManhattanDistance implements Heuristic<Position> {

	/**
	 * Calculates the distance using Manhattan distance
	 * @see algorithms.search.Heuristic#distance(algorithms.search.State, algorithms.search.State)
	 */
	@Override
	public double distance(State<Position> current, State<Position> goal) {
		double xDistance = Math.abs(goal.getState().getX()-current.getState().getX());
		double yDistance = Math.abs(goal.getState().getY()-current.getState().getY());
		double zDistance = Math.abs(goal.getState().getZ()-current.getState().getZ());
		
		return xDistance+yDistance+zDistance;
	}

}

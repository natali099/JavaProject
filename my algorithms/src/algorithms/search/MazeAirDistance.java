package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * The Class MazeAirDistance.
 */
public class MazeAirDistance implements Heuristic<Position> {

	/**
	 * Calculates the distance using Euclidean distance
	 * 
	 * @see algorithms.search.Heuristic#distance(algorithms.search.State, algorithms.search.State)
	 */
	@Override
	public double distance(State<Position> current, State<Position> goal) {
		double xDistance = goal.getState().getX()-current.getState().getX();
		double yDistance = goal.getState().getY()-current.getState().getY();
		double zDistance = goal.getState().getZ()-current.getState().getZ();
		
		return Math.sqrt((Math.pow(xDistance, 2))+(Math.pow(yDistance, 2))+(Math.pow(zDistance, 2)));
	}

}

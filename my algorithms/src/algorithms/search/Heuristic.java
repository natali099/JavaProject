package algorithms.search;

/**
 * The Interface Heuristic.
 *
 * @param <T> the generic type
 */
public interface Heuristic<T> {
	
	/**
	 * Calculates the distance between two states.
	 *
	 * @param current the current state
	 * @param goal the goal state
	 * @return the distance between current state and goal state
	 */
	double distance(State<T> current, State<T> goal);
}

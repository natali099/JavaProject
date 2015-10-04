package algorithms.search;

import java.util.ArrayList;

/**
 * The Interface Searchable.
 *
 * @param <T> the generic type
 */
public interface Searchable<T> {
	
	/**
	 * Gets the start state.
	 *
	 * @return the start state
	 */
	State<T> getStartState();
	
	/**
	 * Gets the goal state.
	 *
	 * @return the goal state
	 */
	State<T> getGoalState();
	
	/**
	 * Gets all possible states from current state.
	 *
	 * @param s the current state
	 * @return all possible states
	 */
	ArrayList<State<T>> getAllPossibleStates(State<T> s);
}

